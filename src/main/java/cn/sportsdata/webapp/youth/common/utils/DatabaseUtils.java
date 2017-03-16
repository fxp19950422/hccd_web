package cn.sportsdata.webapp.youth.common.utils;

import java.util.UUID;

public class DatabaseUtils {
	static long increament = 0;
	private static long and_leftshit(long val, long _and, long _leftshift) {
		return (val&_and)<<_leftshift;
	}
	public static String Short_UUID(String org_id, String user_id){
		return UUID.randomUUID().toString();

//		short_uuid构成的规则如下
//      1   +  user_id    +  标识符   +    APP的启动时间值     +   自增值
//     1bit      25bit       5bits            28bits             4bits
//		try{
//			Calendar calendar = Calendar.getInstance();
//			long account_id = user_id;	
//			long curr_time= calendar.getTimeInMillis();
//			// to do get device id
//			long did = 1;
//			
//			long uuid = and_leftshit(increament,0x0f,0) |  and_leftshit(curr_time,0x0fffffff,4) |
//					and_leftshit(did,0x1f,32) | and_leftshit(account_id,0x1ffffff,37) | and_leftshit(1,1,63);
//
//			increament++;	
//			return uuid;
//		}
//		catch (Exception e) {
//			
//		}
//
//		return 0;
	}
	
}
