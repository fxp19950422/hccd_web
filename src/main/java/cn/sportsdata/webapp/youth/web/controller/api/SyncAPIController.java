package cn.sportsdata.webapp.youth.web.controller.api;

import cn.sportsdata.webapp.youth.common.exceptions.SoccerProException;
import cn.sportsdata.webapp.youth.common.utils.HAFileUtils;
import cn.sportsdata.webapp.youth.common.utils.SecurityUtils;
import cn.sportsdata.webapp.youth.common.vo.AssetVO;
import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.Response;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.service.account.AccountService;
import cn.sportsdata.webapp.youth.service.asset.AssetService;
import cn.sportsdata.webapp.youth.syncservice.BaseSyncService;
import cn.sportsdata.webapp.youth.syncservice.DBTimeService;
import org.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by binzhu on 5/4/16.
 */
@Controller
@RequestMapping("/api/v1/")
public class SyncAPIController {

    @Autowired
    private BaseSyncService baseSyncService;

    @Autowired
    private DBTimeService dbTimeService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AssetService assetService;

    private static Logger logger = Logger.getLogger(SyncAPIController.class);

    private String keyForData = "data";

    @RequestMapping(value = "/sync", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Object getNewData(@RequestBody String postParam){
        LoginVO loginVO = (LoginVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String accountID = loginVO.getId();
        List<String> orgIdList = getAccountOrgs(accountID);

        JSONObject obj = new JSONObject(postParam);
        JSONObject jsonObject = obj.getJSONObject(keyForData);

        Object allDataMap = baseSyncService.getNewData(jsonObject, accountID, orgIdList);

        return Response.toSussess(allDataMap);
    }

    @RequestMapping(value = "/update", produces = "application/json;charset=UTF-8", method = RequestMethod.PUT)
    @ResponseBody
    public Object updateData(@RequestBody String postParam){
        LoginVO loginVO = (LoginVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String accountID = loginVO.getId();
        List<String> orgIdList = getAccountOrgs(accountID);
        String currentDBTime = dbTimeService.getDBTime();

        JSONObject obj = new JSONObject(postParam);
        JSONObject jsonObject = obj.getJSONObject(keyForData);

        if (dataCanBeModified(jsonObject, accountID, orgIdList) == Boolean.FALSE){
            return new ResponseEntity<Response>(
                    Response.toFailure(1, "You don't have enough privilege to modify some of the data!"), HttpStatus.UNAUTHORIZED);
        }

        Object allDataMap = baseSyncService.updateData(jsonObject, currentDBTime, accountID, orgIdList);

        return Response.toSussess(allDataMap);
    }

    @RequestMapping(value = "/new", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Object insertData(@RequestBody String postParam) {
        LoginVO loginVO = (LoginVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String accountID = loginVO.getId();
        List<String> orgIdList = getAccountOrgs(accountID);

        String currentDBTime = dbTimeService.getDBTime();

        JSONObject obj = new JSONObject(postParam);
        JSONObject jsonObject = obj.getJSONObject(keyForData);

        Object allDataMap = baseSyncService.insertData(jsonObject, currentDBTime, accountID, orgIdList);

        return Response.toSussess(allDataMap);
    }

    @RequestMapping(value = "/replace", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Object replaceData(@RequestBody String postParam) {
        LoginVO loginVO = (LoginVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String accountID = loginVO.getId();
        List<String> orgIdList = getAccountOrgs(accountID);

        String currentDBTime = dbTimeService.getDBTime();

        JSONObject obj = new JSONObject(postParam);
        JSONObject jsonObject = obj.getJSONObject(keyForData);

        Object allDataMap = baseSyncService.replaceData(jsonObject, currentDBTime, accountID, orgIdList);

        return Response.toSussess(allDataMap);
    }

    @RequestMapping(value = "/delete", produces = "application/json;charset=UTF-8", method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteData(@RequestBody String postParam) {
        LoginVO loginVO = (LoginVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String accountID = loginVO.getId();
        List<String> orgIdList = getAccountOrgs(accountID);

        String currentDBTime = dbTimeService.getDBTime();

        JSONObject obj = new JSONObject(postParam);
        JSONObject jsonObject = obj.getJSONObject(keyForData);

        // The codes below are commented on 2016.5.31 to correct the case that when client commits a un-existed
        // record to delete. In this case we cannot distinct whether the record is not existed or the committer has no
        // privilege to delete it. So we only delete the records that the committer can delete and return OK.

//        if (dataCanBeModified(jsonObject, accountID, orgIdList) == Boolean.FALSE){
//            return new ResponseEntity<Response>(Response.toFailure(2, "You don't have enough privilege to delete some of the data!"), HttpStatus.UNAUTHORIZED);
//        }

        Object allDataMap = baseSyncService.deleteData(jsonObject, currentDBTime, accountID, orgIdList);

        return Response.toSussess(allDataMap);
    }

    private List<String> getAccountOrgs(String accountID) {
        List<String> orgIdList = new ArrayList<String>();
        
        List<OrgVO> voList = accountService.getOrgsByAccount(accountID);
        for (int i=0; i<voList.size(); i++){
            orgIdList.add( voList.get(i).getId());
        }
        
        return orgIdList;
    }

    private Boolean dataCanBeModified(JSONObject allData, String accountID, List<String> orgIdList){

        Object unsupportedData = baseSyncService.filterData(allData, accountID, orgIdList);

        if (unsupportedData != null){
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    @RequestMapping(value = "/sync/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object fileUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {

        LoginVO loginVO = (LoginVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String accountID = loginVO.getId();
        List orgIdList = getAccountOrgs(accountID);

        String assetInfo = request.getParameter(keyForData);

        if (assetInfo == null){
            return new ResponseEntity<Response>(Response.toFailure(3, "Cannot find keyword 'data' in given parameters!"), HttpStatus.NO_CONTENT);
        }

        JSONObject jsonAsset = new JSONObject(assetInfo);
        String assetID = jsonAsset.getString("id");
        String orgID = jsonAsset.getString("org_id");
        String privacy = jsonAsset.optString("privacy", null);
        String fileExtension = jsonAsset.optString("file_extension", null);

        if (privacy == null){
            privacy = "protected";
        }

        String status = (String) jsonAsset.optString("status", null);

        if (status == null){
            status = "active";
        }

        if (!canUploadAsset(assetID, accountID, orgIdList)){
            return new ResponseEntity<Response>(
                    Response.toFailure(2, "You don't have privilege to upload this file!"), HttpStatus.UNAUTHORIZED);
        }

        // convert to MultipartHttpRequest
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        // get the file according to the pre-defined name "file"
        MultipartFile originalFile = multipartRequest.getFile("file");

        // get original file name
        String originalFilename = originalFile.getOriginalFilename();

        //get file extension
        if(fileExtension == null) {
            fileExtension = HAFileUtils.getFileExtension(originalFilename);
        }

        // get the storage file name
        String storageFileName = assetID;
        if(fileExtension != null && !fileExtension.isEmpty()){
            storageFileName = storageFileName + "." +fileExtension;
        }
        storageFileName = HAFileUtils.getStorageFilePath(storageFileName, accountID, orgID, HAFileUtils.PRODUCT_FILE_TYPE);

        if(storageFileName == null){
            return new ResponseEntity<Response>(
                    Response.toFailure(4, "Failed to create file!"), HttpStatus.NOT_FOUND);
        }

        File storageFile = HAFileUtils.createNewAttachmentFile(storageFileName);
        FileCopyUtils.copy(originalFile.getBytes(), storageFile);

        // add a record to asset
        AssetVO vo = new AssetVO();
        vo.setId(assetID);
        vo.setCreator_id(accountID);
        vo.setOrg_id(orgID);
        vo.setDisplay_name(originalFilename);
        vo.setFile_extension(fileExtension);
        vo.setSize(originalFile.getSize());
        vo.setPrivacy(privacy);
        vo.setStatus(status);
        vo.setStorage_name(SecurityUtils.encryptByAES(storageFile.getAbsolutePath()));

        assetService.replaceAsset(vo);

        return Response.toSussess("OK");
    }

    @ResponseBody
    @RequestMapping(value = "/sync/download")
    public Object downloadFormAsset(HttpServletRequest request, HttpServletResponse response, @RequestParam(required=true) String id) throws SoccerProException {
        if(StringUtils.isBlank(id)) {
            logger.error("File name is blank");
            return new ResponseEntity<Response>(
                    Response.toFailure(4, "Asset id is needed!"), HttpStatus.BAD_REQUEST);
        }

        LoginVO loginVO = (LoginVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String accountID = loginVO.getId();
        List orgIdList = getAccountOrgs(accountID);

        File targetFile = null;

        AssetVO vo = assetService.getAssetByID(id);

        if(vo == null || vo.getStorage_name() ==null || vo.getStorage_name().isEmpty()){
            return new ResponseEntity<Response>(
                    Response.toFailure(4, "No such file!"), HttpStatus.NOT_FOUND);
        }

        if (!canDownloadAsset(vo, accountID, orgIdList)){
            return new ResponseEntity<Response>(
                    Response.toFailure(4, "You don't have privilege to download this file!"), HttpStatus.UNAUTHORIZED);
        }

        try{
            String encryptedFileName = vo.getStorage_name();
            String storageFileName = SecurityUtils.decryptByAES(encryptedFileName);
            targetFile = new File(storageFileName);

        } catch(Exception e) {
            targetFile = null;
        }

        if(targetFile==null || !targetFile.exists()) {
            logger.error(id + " is not existed");
            return new ResponseEntity<Response>(
                    Response.toFailure(4, "No such file!"), HttpStatus.NOT_FOUND);
        }

        long size = targetFile.length();
        response.addHeader("Content-Length",""+size);
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName="+vo.getDisplay_name());

        InputStream is = null;
        OutputStream os = null;

        try {
            is = new FileInputStream(targetFile);
            os = response.getOutputStream();

            IOUtils.copy(is, os);
        } catch(Exception e) {
            logger.error("Error occurs while downloading form attachment: " + targetFile.getAbsolutePath(), e);
            return new ResponseEntity<Response>(
                    Response.toFailure(4, "Error occurs while downloading file!"), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
        }

        return Response.toSussess("OK");
    }

    boolean canUploadAsset(String assetID, String accountID, List<String> orgList){
        AssetVO vo = assetService.getAssetByID(assetID);

        if(vo == null){
            return true;
        }

        if(vo.getCreator_id().equals(accountID)){
            return true;
        }

        if(vo.getPrivacy().equals("public") && orgList.contains(vo.getOrg_id())){
            return true;
        }

        return false;
    }

    boolean canDownloadAsset(AssetVO vo, String accountID, List<String> orgList){

        if (vo.getCreator_id().equals(accountID)){
            return true;
        }

        if(orgList.contains(vo.getOrg_id()) && !vo.getPrivacy().equals("private")){
            return true;
        }

        return false;
    }
}
