package cn.sportsdata.webapp.youth.common.utils;

import com.aliyun.oss.OSSClient;

public class OSSUtil {
	private static String endpoint = "https://oss-cn-shanghai.aliyuncs.com";
	public static String BUCKET_NAME = "hospital-image";
	// accessKey请登录https://ak-console.aliyun.com/#/查看
	private static String accessKeyId = "LTAIwSWiHAJqKLUl";
	private static String accessKeySecret = "NNX1hVtHG5KtkgIS4Ev7EKlsTk7Ma3";
	// 创建OSSClient实例
	
	public static OSSClient getOSSClient(){
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		return ossClient;
	}
	
	// 使用访问OSS
	
	// 关闭client
	public static void shutdownOSSClient(OSSClient client){
		client.shutdown();
	}
	
}
