package cn.sportsdata.webapp.youth.common.utils;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

import cn.sportsdata.webapp.youth.common.constants.Constants;

public final class HAFileUtils {
	public final static String TEMP_FILE_TYPE="tmp";
	public final static String PRODUCT_FILE_TYPE="product";
	private final static String PIC_FOLDER_TYPE="pic";
	private final static String AUDIO_FOLDER_TYPE="audio";
	private final static String VIDEO_FOLDER_TYPE="video";
	private final static String TEXT_FOLDER_TYPE="text";
	private final static String OTHER_FOLDER_TYPE="other";

	public static String getFileExtension(String fileName){
		if (fileName == null || fileName.isEmpty()){
			return null;
		}

		int index = fileName.lastIndexOf(".");
		if (index < 0 || index == fileName.length()-1){
			return "";
		}

		return fileName.substring(index+1);
	}

	public static String getFolderType(String fileName){

		String fileExt = getFileExtension(fileName);

		if (fileExt == null){
			return OTHER_FOLDER_TYPE;
		}
		
		fileExt = fileExt.toLowerCase();

		if (Arrays.asList(Constants.VALID_ATTACHMENTS_PIC_TYPES).contains(fileExt)){
			return PIC_FOLDER_TYPE;
		}

		if (Arrays.asList(Constants.VALID_ATTACHMENTS_AUDIO_TYPES).contains(fileExt)){
			return AUDIO_FOLDER_TYPE;
		}

		if (Arrays.asList(Constants.VALID_ATTACHMENTS_VIDEO_TYPES).contains(fileExt)){
			return VIDEO_FOLDER_TYPE;
		}

		if (Arrays.asList(Constants.VALID_ATTACHMENTS_TEXT_TYPES).contains(fileExt)){
			return TEXT_FOLDER_TYPE;
		}

		return OTHER_FOLDER_TYPE;
	}

	public static String getStorageFilePath(String fileName, String loginID, String orgID, String fileType){
		if(fileName == null || fileName.isEmpty()){
			return null;
		}

		String uploadRootPath = ConfigProps.getInstance().getConfigValue("attachment.folder.temppath");

		if (PRODUCT_FILE_TYPE.equals(fileType)){
			uploadRootPath = ConfigProps.getInstance().getConfigValue("form.attachment.folder.rootpath");
		}

		String folderType = getFolderType(fileName);

		StringBuffer filePath = new StringBuffer().append(uploadRootPath).append(File.separator)
				.append(File.separator).append(orgID)
				.append(File.separator).append(loginID)
				.append(File.separator).append(folderType)
				.append(File.separator).append(fileName);

		return filePath.toString();
	}

	public static File createNewAttachmentFile(String fileName, String loginID, String orgID, String fileType) {

		String filePath = getStorageFilePath(fileName, loginID, orgID, fileType);
		if(filePath == null || filePath.isEmpty()){
			return null;
		}

		File targetFile = new File(filePath);

		if(targetFile.exists()) {
			FileUtils.deleteQuietly(targetFile);
		}
		
		if(!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}
		
		return targetFile;
	}

	public static File createNewAttachmentFile(String fileNameWithPath) {

		if (fileNameWithPath == null || fileNameWithPath.isEmpty()){
			return null;
		}

		File targetFile = new File(fileNameWithPath);

		if(targetFile.exists()) {
			FileUtils.deleteQuietly(targetFile);
		}

		if(!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}

		return targetFile;
	}

	public static File createNewTacticsFile(String fileName, String loginID, String orgID, String fileType) {
		return createNewAttachmentFile(fileName, loginID, orgID, fileType);
	}
}
