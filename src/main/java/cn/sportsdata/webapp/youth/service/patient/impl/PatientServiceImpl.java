package cn.sportsdata.webapp.youth.service.patient.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sportsdata.webapp.youth.common.bo.hospital.PatientMedicalRecordBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientOperationRecordBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientRecordBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientResidentRecordBO;
import cn.sportsdata.webapp.youth.common.vo.patient.DoctorVO;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetTypeVO;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetVO;
import cn.sportsdata.webapp.youth.dao.asset.AssetDAO;
import cn.sportsdata.webapp.youth.dao.hospital.PatientDAO;
import cn.sportsdata.webapp.youth.service.patient.PatientService;

@Service
public class PatientServiceImpl implements PatientService {
	private static Logger logger = Logger.getLogger(PatientServiceImpl.class);
	@Autowired
	private PatientDAO patientDAO;
	
	@Autowired
	private AssetDAO assetDAO;
	
	@Override
	public List<PatientRecordBO> getMedicalRecordList(String hospitalId, String doctorCode) {
		List<PatientRecordBO> patientRecordList = null;
		try {
			patientRecordList = patientDAO.getMedicalRecordList(hospitalId, doctorCode);
			if(patientRecordList == null) {
				patientRecordList = new ArrayList<PatientRecordBO>();
			}
		}
		catch (Exception e) {
			patientRecordList = new ArrayList<PatientRecordBO>();
		}
		return patientRecordList;
	}

	@Override
	public PatientMedicalRecordBO getMedicalRecord(String recordId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PatientOperationRecordBO getOperationRecord(String recordId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PatientResidentRecordBO getResidentRecord(String recordId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RecordAssetVO> getRecordAssetList(String recordId) {
		List<RecordAssetVO> assetTypeList = null;
		try {
			assetTypeList = patientDAO.getRecordAssetList(recordId);
			if(assetTypeList == null) {
				assetTypeList = new ArrayList<RecordAssetVO>();
			}
		}
		catch (Exception e) {
			assetTypeList = new ArrayList<RecordAssetVO>();
		}
		return assetTypeList;
	}

	@Override
	public List<RecordAssetTypeVO> getRecordAssetTypeList(String recordType) {
		List<RecordAssetTypeVO> assetTypeList = null;
		try {
			assetTypeList = patientDAO.getRecordAssetTypeList(recordType);
			if(assetTypeList == null) {
				assetTypeList = new ArrayList<RecordAssetTypeVO>();
			}
		}
		catch (Exception e) {
			assetTypeList = new ArrayList<RecordAssetTypeVO>();
		}
		return assetTypeList;
	}

	@Override
	public DoctorVO getDoctorInfoByUsername(String username) {
		return patientDAO.getDoctorInfoByUsername(username);
	}
}
