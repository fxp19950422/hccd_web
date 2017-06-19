package cn.sportsdata.webapp.youth.web.controller.exchange;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hslf.usermodel.HSLFPictureData;
import org.apache.poi.hslf.usermodel.HSLFPictureShape;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTable;
import org.apache.poi.hslf.usermodel.HSLFTableCell;
import org.apache.poi.hslf.usermodel.HSLFTextBox;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.apache.poi.sl.draw.DrawTableShape;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.sl.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.sportsdata.webapp.youth.common.utils.DateUtil;
import cn.sportsdata.webapp.youth.common.utils.SecurityUtils;
import cn.sportsdata.webapp.youth.common.vo.AssetTypeVO;
import cn.sportsdata.webapp.youth.common.vo.AssetVO;
import cn.sportsdata.webapp.youth.common.vo.DepartmentVO;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.common.vo.patient.DoctorVO;
import cn.sportsdata.webapp.youth.common.vo.patient.ExchangeVO;
import cn.sportsdata.webapp.youth.common.vo.patient.OpertaionRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientInHospital;
import cn.sportsdata.webapp.youth.common.vo.patient.ResidentRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.ShiftMeetingVO;
import cn.sportsdata.webapp.youth.service.asset.AssetService;
import cn.sportsdata.webapp.youth.service.exchange.ExchangeService;
import cn.sportsdata.webapp.youth.service.patient.PatientService;
import cn.sportsdata.webapp.youth.web.controller.BaseController;

@Controller
@RequestMapping("/exchange")
public class ExchangeController extends BaseController {
	private static Logger logger = Logger.getLogger(ExchangeController.class);

	@Autowired
	ExchangeService exchangeService;

	@Autowired
	PatientService patientService;

	@Autowired
	AssetService assetservice;

	@RequestMapping(value = "/exchange_list", method = RequestMethod.GET)
	public String toTestManagePage(HttpServletRequest request, Model model,
			@RequestParam(required = false, defaultValue = "0") int radio) {

		DepartmentVO department = this.getCurrentDepartment(request);
		List<DoctorVO> doctors = exchangeService.getDoctors(department.getDepartmentCode(), radio == 1);

		model.addAttribute("doctors", doctors);
		model.addAttribute("radio", radio);

		String dateStr = DateUtil.date2String(new Date(), "yyyy-MM-dd");

		model.addAttribute("systemDate", dateStr);

//		List<ShiftMeetingVO> recordList = patientService.getTodayExchangeRecordList();

		return "exchange/exchange_list";
	}

	// @RequestMapping(value = "/exchange_detail",method = RequestMethod.POST)
	// public String toExchangeDetail(HttpServletRequest request, Model model,
	// @RequestBody JSONObject obj) {
	//
	// JSONArray array = obj.getJSONArray("uids");
	// List<String> uids = new ArrayList<String>();
	//
	// for (int i = 0; i < array.size();i++){
	// uids.add(array.getJSONObject(i).getString("uid"));
	// }
	//
	// List<ResidentRecord> medicalRecords =
	// exchangeService.getMedicalRecordByPatientIds(uids);
	// if (medicalRecords.size() <= 0){
	// for (int i =0; i < 10; i++){
	// ResidentRecord vo = new ResidentRecord();
	// vo.setName("王爷"+i);
	// medicalRecords.add(vo);
	// }
	// }
	//
	// model.addAttribute("medicalrecords", medicalRecords);
	// return "exchange/exchange_detail";
	// }

	public static int differentDaysByMillisecond(Date date1, Date date2) {
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
		return days;
	}

	@RequestMapping(value = "/exchange_detail", method = RequestMethod.GET)
	public String toExchangeDetail(HttpServletRequest request, Model model, String obj, String anotherOperation,
			String startDate) throws Exception {

		// String document = new String(Base64.decode(obj.getBytes()));
		//
		// JSONObject jsonObj = JSONObject.fromObject(document);
		// JSONArray array = jsonObj.getJSONArray("uids");
		// List<String> uids = new ArrayList<String>();
		//
		// for (int i = 0; i < array.size();i++){
		// uids.add(array.getJSONObject(i).getString("uid"));
		// }
		//
		// List<ResidentRecord> medicalRecords =
		// exchangeService.getMedicalRecordByPatientIds(uids);
		// System.out.println("the size of the records is " +
		// medicalRecords.size());
		// if (medicalRecords.size() <= 0){
		// for (int i =0; i < 10; i++){
		// ResidentRecord vo = new ResidentRecord();
		// vo.setName("王爷"+i);
		// medicalRecords.add(vo);
		// }
		// }

		Date dateStart = DateUtil.getDateFromStr(startDate);
		Date now = new Date();
		int difference = differentDaysByMillisecond(dateStart, now);
		List<ShiftMeetingVO> recordList = patientService.getTodayExchangeRecordList(difference + 1);

		List<String> operationList = new ArrayList<String>();
		List<String> residentList = new ArrayList<String>();
		List<String> patientInHospitalList = new ArrayList<String>();
		

		LoginVO login = getCurrentUser(request);

		String role = this.getCurrentRole(request);

		String doctorId = login.getHospitalUserInfo().getUserIdinHospital();

		if (!StringUtils.isEmpty(role) && "director".equals(role)) {
			doctorId = null;
		}

		
		for (ShiftMeetingVO record : recordList) {
			if ("operation".equals(record.getRecordType())) {
				operationList.add(record.getRecordId());
			} else if ("resident".equals(record.getRecordType())) {
				residentList.add(record.getRecordId());
			} else if ("patientInhospital".equals(record.getRecordType())) {
				patientInHospitalList.add(record.getRecordId());
			}
		}
		
		//test
//		operationList.clear();
//		residentList.clear();
//		patientInHospitalList.clear();
//		operationList.add("65ecda4a-528e-11e7-8f6f-00163e0ccee1");
//		patientInHospitalList.add("44E5C449F3014B62BAE75B7AC8CE0A39");

		List<PatientInHospital> operationRecordList = new ArrayList<PatientInHospital>();
		if (operationList.size() > 0) {
			operationRecordList = exchangeService.getExchangeOperationRecordList(operationList, doctorId);
		}

		List<PatientInHospital> patientInHospitalRecordList = new ArrayList<PatientInHospital>();
		if (patientInHospitalList.size() > 0) {
			patientInHospitalRecordList = exchangeService.getExchangePatientInHospitalRecord(patientInHospitalList,
					doctorId);
		}

		List<ResidentRecord> residentRecordList = new ArrayList<ResidentRecord>();
		if (residentList.size() > 0) {
			residentRecordList = exchangeService.getExchangeResidentRecord(residentList, doctorId);
		}
		
//		List<PatientInHospital> list = new ArrayList<PatientInHospital>();
//		List<PatientInHospital> tmpList = new ArrayList<PatientInHospital>();
		LinkedHashMap<String, ExchangeVO> map = new LinkedHashMap<String, ExchangeVO>();
		for (int i = 0; i < operationRecordList.size(); i++){
			PatientInHospital exchangeRecord = operationRecordList.get(i);
			if (map.containsKey(exchangeRecord.getDoctorId())){
				ExchangeVO exchangeVO = (ExchangeVO)map.get(exchangeRecord.getDoctorId());
				exchangeVO.getExchangeList().add(exchangeRecord);
			} else {
				ExchangeVO exchangeVO = new ExchangeVO();
				exchangeVO.getExchangeList().add(exchangeRecord);
				map.put(exchangeRecord.getDoctorId(), exchangeVO);
			}
		}
		
		for (int i = 0; i < residentRecordList.size(); i++){
			ResidentRecord exchangeRecord = residentRecordList.get(i);
			if (map.containsKey(exchangeRecord.getDoctorId())){
				ExchangeVO exchangeVO = (ExchangeVO)map.get(exchangeRecord.getDoctorId());
				exchangeVO.getResidentList().add(exchangeRecord);
			} else {
				ExchangeVO exchangeVO = new ExchangeVO();
				exchangeVO.getResidentList().add(exchangeRecord);
				map.put(exchangeRecord.getDoctorId(), exchangeVO);
			}
		}
		
		List<ExchangeVO> exchangeVOList = new ArrayList<ExchangeVO>(map.values());

		model.addAttribute("medicalrecords", exchangeVOList);

		model.addAttribute("patient_in_hospital_records", patientInHospitalRecordList);

//		model.addAttribute("residentrecords", residentRecordList);
		
		List<List<String>> page = new ArrayList<List<String>>();

		if (!StringUtils.isEmpty(anotherOperation.trim())) {
			String[] arrays = anotherOperation.split("<br>");

			List<String> lstArr = new ArrayList<String>();
			for (int i = 0; i < arrays.length; i++) {
				if (!StringUtils.isEmpty(arrays[i])) {
					lstArr.add(arrays[i]);
				}
			}

			List<String> tmp = new ArrayList<String>();
			for (int i = 0; i < lstArr.size(); i++) {
				if (i % 4 == 0) {
					if (i != 0) {
						page.add(tmp);
						tmp = new ArrayList<String>();
					}
					tmp.add(lstArr.get(i));
				} else {
					tmp.add(lstArr.get(i));
				}
			}
			page.add(tmp);
		}
		model.addAttribute("anotherOperation", page);

		return "exchange/exchange_detail";
	}

	@RequestMapping(value = "/download_exchange_record", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download_exchange_record(HttpServletRequest request, HttpServletResponse response, String startDate)
			throws Exception {
		
		Date dateStart = DateUtil.getDateFromStr(startDate);
		Date now = new Date();
		int difference = differentDaysByMillisecond(dateStart, now);
		List<ShiftMeetingVO> recordList = patientService.getTodayExchangeRecordList(difference + 1);

		List<String> operationList = new ArrayList<String>();
		List<String> residentList = new ArrayList<String>();
		List<String> patientInHospitalList = new ArrayList<String>();
		for (ShiftMeetingVO record : recordList) {
			if ("operation".equals(record.getRecordType())) {
				operationList.add(record.getRecordId());
			} else if ("resident".equals(record.getRecordType())) {
				residentList.add(record.getRecordId());
			} else if ("patientInhospital".equals(record.getRecordType())) {
				patientInHospitalList.add(record.getRecordId());
			}
		}

		LoginVO login = getCurrentUser(request);

		String role = this.getCurrentRole(request);

		String doctorId = login.getHospitalUserInfo().getUserIdinHospital();

		if (!StringUtils.isEmpty(role) && "director".equals(role)) {
			doctorId = null;
		}

		List<PatientInHospital> operationRecordList = null;
		if (operationList.size() > 0) {
			operationRecordList = exchangeService.getExchangeOperationRecordList(operationList, doctorId);
		}

		List<PatientInHospital> patientInHospitalRecordList = null;
		if (patientInHospitalList.size() > 0) {
			patientInHospitalRecordList = exchangeService.getExchangePatientInHospitalRecord(patientInHospitalList,
					doctorId);
		}

		List<ResidentRecord> residentRecordList = null;
		if (residentList.size() > 0) {
			residentRecordList = exchangeService.getExchangeResidentRecord(residentList, doctorId);
		}

		ByteArrayOutputStream ostream = null;
		HSLFSlideShow pptFile = new HSLFSlideShow();
		try {
			// FileInputStream in = new FileInputStream(templateFile);
			if (operationRecordList != null) {
				savePPT(pptFile, operationRecordList);
			}
			if (patientInHospitalRecordList != null) {
				saveDiscussPPT(pptFile, patientInHospitalRecordList);
			}

			if (residentRecordList != null) {
				saveResdientPPT(pptFile, residentRecordList);
			}

			// 输出 word 内容文件流，提供下载
			ostream = new ByteArrayOutputStream();

			// OutputStream servletOS = response.getOutputStream();
			String name = DateUtil.date2String(new Date(), "yyyy-MM-dd") + ".ppt";
			pptFile.write(ostream);
			String dfileName = new String(name.getBytes("gbk"), "iso8859-1");

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", dfileName);
			//
			return new ResponseEntity<byte[]>(ostream.toByteArray(), headers, HttpStatus.CREATED);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pptFile.close();
		}

		return null;
	}

	private void savePPT(HSLFSlideShow pptFile, List<PatientInHospital> patientInHospitalRecordList) throws Exception {
		if (patientInHospitalRecordList == null || patientInHospitalRecordList.size() == 0) {
			return;
		}
		HSLFSlide section1 = pptFile.createSlide();
		Rectangle arg0 = new Rectangle(50, 150, 600, 150);
		HSLFTextBox tt = section1.createTextBox();
		tt.setAnchor(arg0);

		HSLFTextRun tr = tt.setText("手术交班");
		tr.setFontSize((double) 120.0);

		for (PatientInHospital record : patientInHospitalRecordList) {
			operationPPT(pptFile, record);
		}
	}

	private void saveDiscussPPT(HSLFSlideShow pptFile, List<PatientInHospital> operationRecordList) throws Exception {
		if (operationRecordList == null || operationRecordList.size() == 0) {
			return;
		}

		HSLFSlide section1 = pptFile.createSlide();
		Rectangle arg0 = new Rectangle(50, 150, 600, 150);
		HSLFTextBox tt = section1.createTextBox();
		tt.setAnchor(arg0);

		HSLFTextRun tr = tt.setText("病例讨论");
		tr.setFontSize((double) 120.0);

		for (PatientInHospital record : operationRecordList) {
			patientBaseInfo(pptFile, record);

			for (OpertaionRecord operation : record.getOperationRecords()) {
				operationInfo(pptFile, operation);
				for (AssetTypeVO assetType : operation.getAssetTypes()) {
					for (AssetVO asset : assetType.getAssets()) {
						operationPic(pptFile, asset, assetType.getAssetTypeName());
					}
				}
			}

			discussInfo(pptFile, record);
		}
	}

	private void discussInfo(HSLFSlideShow ppt, PatientInHospital record) throws Exception {
		HSLFSlide slide = ppt.createSlide();
		HSLFTextBox title = slide.addTitle();
		title.setText("体格检查");
		Rectangle arg0 = new Rectangle(50, 150, 500, 500);
		HSLFTextBox tt = slide.createTextBox();
		tt.setAnchor(arg0);

		String bodyExam = record.getBodyExam();
		if (bodyExam == null) {
			bodyExam = "请在web系统中填写查体";
		}
		HSLFTextRun tr = tt.setText(bodyExam);
		tr.setFontSize((double) 24d);

		HSLFSlide slide2 = ppt.createSlide();
		HSLFTextBox title2 = slide2.addTitle();
		title2.setText("讨论内容");
		HSLFTextBox tt2 = slide2.createTextBox();
		tt2.setAnchor(arg0);

		String discuss = record.getRecordDiscussion();
		if (discuss == null) {
			discuss = "请在web系统中填写讨论内容";
		}
		HSLFTextRun tr2 = tt2.setText(discuss);
		tr2.setFontSize((double) 24d);
	}

	private void operationPPT(HSLFSlideShow ppt, PatientInHospital record) throws Exception {
		patientBaseInfo(ppt, record);

		for (OpertaionRecord operation : record.getOperationRecords()) {
			operationInfo(ppt, operation);
			for (AssetTypeVO assetType : operation.getAssetTypes()) {
				for (AssetVO asset : assetType.getAssets()) {
					operationPic(ppt, asset, assetType.getAssetTypeName());
				}
			}
		}
	}
	
	static private String OSS_URL = "http://hospital-image.oss-cn-shanghai.aliyuncs.com/";
	private InputStream loadPicFromOSS(String fileName) throws IOException {
		URL url = new URL(OSS_URL + fileName);    
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
		        //设置超时间为3秒  
		conn.setConnectTimeout(3*1000);  
		//防止屏蔽程序抓取而返回403错误  
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
		  
		//得到输入流  
		InputStream inputStream = conn.getInputStream();    

		return inputStream;
	}

	private void operationPic(HSLFSlideShow ppt, AssetVO asset, String typeName) throws Exception {
		HSLFSlide slide = ppt.createSlide();
		HSLFTextBox title = slide.addTitle();
		title.setText(typeName);

		BufferedImage sourceImg = null;
		HSLFPictureData pd = null;
		if (asset.getStorage_name().equalsIgnoreCase("oss")) {
			InputStream inputStream = loadPicFromOSS(asset.getId());
			sourceImg = ImageIO.read(inputStream);
			pd = ppt.addPicture(inputStream, PictureData.PictureType.JPEG);
		} else {
			AssetVO assetv = assetservice.getAssetByID(asset.getId());
			String encryptedFileName = assetv.getStorage_name();
			String filePath = SecurityUtils.decryptByAES(encryptedFileName);
			File picture = new File(filePath);

			if (!picture.exists()) {
				logger.error("Asset ID: " + assetv.getId() + " of related file path is not found");
				return;
			}
			sourceImg = ImageIO.read(new FileInputStream(picture));
			pd = ppt.addPicture(picture, PictureData.PictureType.JPEG);
		}
		
		HSLFPictureShape pictNew = new HSLFPictureShape(pd);

		// 设置图片在幻灯片中的位置
		int w = sourceImg.getWidth();
		double h = sourceImg.getHeight() / (double) w * 500d;
		pictNew.setAnchor(new java.awt.Rectangle(100, 120, 500, (int) h));

		slide.addShape(pictNew);
	}

	private void operationInfo(HSLFSlideShow ppt, OpertaionRecord record) throws Exception {
		HSLFSlide slide = ppt.createSlide();
		HSLFTextBox title = slide.addTitle();
		title.setText("手术情况");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[][] txt1 = { { "手术时间", sdf.format(record.getOperatingDate()) }, { "手术名称", record.getOperationDesc() },
				{ "术者", record.getOperator() }, { "麻醉方法", record.getAnaesthesiaMethod() } };

		// 构造一个6行2列的表
		HSLFTable table1 = slide.createTable(4, 2);
		for (int i = 0; i < txt1.length; i++) {
			for (int j = 0; j < txt1[i].length; j++) {
				// 获取表的单元格
				HSLFTableCell cell = table1.getCell(i, j);
				// 单元格的文本域
				HSLFTextRun rt = cell.getTextParagraphs().get(0).getTextRuns().get(0);
				// 设置样式
				rt.setFontFamily("Microsoft YaHei");
				rt.setFontSize(24d);
				if (j == 0) {
					cell.getFill().setForegroundColor(new Color(157, 198, 0));
				} else {
					rt.setBold(true);
				}
				// 设置格子垂直居中
				cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
				// 设置格子水平居中
				cell.setHorizontalCentered(true);
				// 设置格子文本
				if (txt1[i][j] == null) {
					cell.setText("web网站中未填写此项");
				} else {
					cell.setText(txt1[i][j]);
				}
			}
		}

		DrawTableShape dts1 = new DrawTableShape(table1);
		dts1.setAllBorders(1.0, Color.black);

		table1.setColumnWidth(0, 120);
		table1.setColumnWidth(1, 480);

		int pgWidth = ppt.getPageSize().width;
		// 移动位置
		table1.moveTo((pgWidth - table1.getAnchor().getWidth()) / 2., 200.);
	}

	private void patientBaseInfo(HSLFSlideShow ppt, PatientInHospital record) throws Exception {
		HSLFSlide slide = ppt.createSlide();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String[] txtTitle = { "姓名", "年龄", "入院时间", "收治医生", "住院号" };
		String[] txtContent = { record.getRealName(), record.getAge() + "岁", sdf.format(record.getAdmissionDateTime()),
				record.getDoctorInCharge(), record.getPatientId() };
		String[][] txt1 = { txtTitle, txtContent };

		// 构造一个6行2列的表
		HSLFTable table1 = slide.createTable(2, 5);
		for (int i = 0; i < txt1.length; i++) {
			for (int j = 0; j < txt1[i].length; j++) {
				// 获取表的单元格
				HSLFTableCell cell = table1.getCell(i, j);
				// 单元格的文本域
				HSLFTextRun rt = cell.getTextParagraphs().get(0).getTextRuns().get(0);
				// 设置样式
				rt.setFontFamily("Microsoft YaHei");
				rt.setFontSize(20d);
				if (i == 0) {
					cell.getFill().setForegroundColor(new Color(157, 198, 0));
				} else {
					rt.setBold(true);
				}
				// 设置格子垂直居中
				cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
				// 设置格子水平居中
				cell.setHorizontalCentered(true);
				// 设置格子文本
				if (txt1[i][j] == null) {
					cell.setText("web未填写");
				} else {
					cell.setText(txt1[i][j]);
				}
			}
		}

		DrawTableShape dts1 = new DrawTableShape(table1);
		dts1.setAllBorders(1.0, Color.black);

		for (int j = 0; j < txtTitle.length; j++) {
			table1.setColumnWidth(j, 140);
		}
		for (int j = 0; j < txt1.length; j++) {
			table1.setRowHeight(j, 40);
		}
		int pgWidth = ppt.getPageSize().width;
		// 移动位置
		table1.moveTo((pgWidth - table1.getAnchor().getWidth()) / 2., 20.);

		String[][] txt2 = { { "主诉", record.getOpPrimary() }, { "诊断", record.getDiagnosis() } };
		// 构造一个2行2列的表
		HSLFTable table2 = slide.createTable(2, 2);
		for (int i = 0; i < txt2.length; i++) {
			for (int j = 0; j < txt2[i].length; j++) {
				// 获取表的单元格
				HSLFTableCell cell = table2.getCell(i, j);
				// 单元格的文本域
				HSLFTextRun rt = cell.getTextParagraphs().get(0).getTextRuns().get(0);
				// 设置样式
				rt.setFontFamily("Arial");
				rt.setFontSize(30d);
				if (j == 0) {
					cell.getFill().setForegroundColor(new Color(227, 227, 227));
				} else {
					rt.setBold(true);
				}
				// 设置格子垂直居中
				cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
				// 设置格子水平居中
				cell.setHorizontalCentered(true);
				// 设置格子文本
				if (txt2[i][j] == null) {
					cell.setText("web网站中未填写此项内容");
				} else {
					cell.setText(txt2[i][j]);
				}
			}
		}

		DrawTableShape dts2 = new DrawTableShape(table2);
		dts2.setAllBorders(1.0, Color.black);

		table2.setColumnWidth(0, 140d);
		table2.setColumnWidth(1, table1.getAnchor().getWidth() - 140d);
		// 移动位置
		table2.moveTo((pgWidth - table2.getAnchor().getWidth()) / 2., table1.getAnchor().getY() + 120d);
	}

	private void saveResdientPPT(HSLFSlideShow pptFile, List<ResidentRecord> residentRecordList) throws Exception {
		if (residentRecordList == null || residentRecordList.size() == 0) {
			return;
		}
		HSLFSlide section1 = pptFile.createSlide();
		Rectangle arg0 = new Rectangle(50, 150, 600, 150);
		HSLFTextBox tt = section1.createTextBox();
		tt.setAnchor(arg0);

		HSLFTextRun tr = tt.setText("复查阅片");
		tr.setFontSize((double) 120.0);

		for (ResidentRecord record : residentRecordList) {
			residentPPT(pptFile, record);
		}
	}

	private void residentPPT(HSLFSlideShow ppt, ResidentRecord record) throws Exception {
		PatientInHospital patientInfo = new PatientInHospital();
		patientInfo.setAge(record.getAge() > 0?record.getAge().toString():"0");
		patientInfo.setRealName(record.getRealName());
		patientInfo.setAdmissionDateTime(record.getAdmissionDate());
		patientInfo.setDoctorInCharge(record.getDoctorInCharge());
		patientInfo.setPatientId(record.getPatientId());
		patientInfo.setOpPrimary(record.getInState());
		patientInfo.setDiagnosis(record.getDiagnosis());
		patientBaseInfo(ppt, patientInfo);

		for (AssetTypeVO assetType : record.getResidentAssetTypes()) {
			for (AssetVO asset : assetType.getAssets()) {
				operationPic(ppt, asset, assetType.getAssetTypeName());
			}
		}
	}
}
