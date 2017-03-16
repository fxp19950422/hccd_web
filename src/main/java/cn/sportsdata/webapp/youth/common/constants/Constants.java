package cn.sportsdata.webapp.youth.common.constants;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	public static final String DEVELOPMENT_MODE = "development";
	public static final String PRODUCTION_MODE = "production";
	
	public static final String STRING_FALSE = "false";
	public static final String STRING_TRUE = "true";
	
	public static final String CHIEF_COACH = "chiefCoach";
	public static final String ASSISTANT_COACH = "assistantCoach";
	public static final String FITNESS_COACH = "fitnessCoach";
	public static final String GOALKEEPER_COACH = "goalkeeperCoach";
	public static final String RESEARCH_COACH = "researchCoach";
	public static final String TACTICS_COACH = "tacticsCoach";
	
	public static final String FORWARD_POSITION = "forward";
	public static final String CENTER_POSITION = "center";
	public static final String DEFENDER_POSITION = "defender";
	public static final String GOALKEEPER_POSITION = "goalkeeper";
	
	public static Map<String, String> POSITION_MAPPING = new HashMap<String, String>();
	public static Map<String, String> RATING_MAPPING = new HashMap<String, String>();
	public static Map<String, String> PHYSIQUE_MAPPING = new HashMap<String, String>();
	public static Map<String, String> PREFERRED_FOOT_MAPPING = new HashMap<String, String>();
	public static Map<String, String> PROFESSIONAL_TYPE_MAPPING = new HashMap<String, String>();
	public static Map<String, String> COACH_TYPE_MAPPING = new HashMap<String, String>();
	
	public static Map<String, String> SINGLETRAINING_TYPE_MAPPING = new HashMap<String, String>();
	public static Map<String, String> SINGLETRAINING_TARGET_MAPPING = new HashMap<String, String>();
	
	public static Map<String, String> MATCH_TYPE_MAPPING = new HashMap<String, String>();
	public static Map<String, String> MATCH_FIELD_TYPE_MAPPING = new HashMap<String, String>();
	
	public static Map<String, String> MATCH_GOAL_TYPE_MAPPING = new HashMap<String, String>();
	public static Map<String, String> MATCH_GOAL_MODE_MAPPING = new HashMap<String, String>();
	public static Map<String, String> MATCH_FOUL_TYPE_MAPPING = new HashMap<String, String>();
	
	static {
		POSITION_MAPPING.put("forward", "前锋");
		POSITION_MAPPING.put("center", "中场");
		POSITION_MAPPING.put("defender", "后卫");
		POSITION_MAPPING.put("goalkeeper", "门将");
		
		RATING_MAPPING.put("excellent", "优秀");
		RATING_MAPPING.put("good", "良好");
		RATING_MAPPING.put("average", "中等");
		RATING_MAPPING.put("bad", "不及格");
		
		PHYSIQUE_MAPPING.put("strong", "强壮");
		PHYSIQUE_MAPPING.put("middle", "适中");
		PHYSIQUE_MAPPING.put("weak", "瘦弱");
		
		PREFERRED_FOOT_MAPPING.put("left", "左脚");
		PREFERRED_FOOT_MAPPING.put("right", "右脚");
		
		PROFESSIONAL_TYPE_MAPPING.put("youth", "青训");
		PROFESSIONAL_TYPE_MAPPING.put("transfer", "转会");
		PROFESSIONAL_TYPE_MAPPING.put("rent", "租借");
		
		COACH_TYPE_MAPPING.put(CHIEF_COACH, "主教练");
		COACH_TYPE_MAPPING.put(ASSISTANT_COACH, "助理教练");
		COACH_TYPE_MAPPING.put(GOALKEEPER_COACH, "守门员教练");
		COACH_TYPE_MAPPING.put(FITNESS_COACH, "体能教练");
		COACH_TYPE_MAPPING.put(RESEARCH_COACH, "科研教练");
		COACH_TYPE_MAPPING.put(TACTICS_COACH, "战术教练");

		
		SINGLETRAINING_TYPE_MAPPING.put("normal", "普通");
		SINGLETRAINING_TYPE_MAPPING.put("guide", "指导");
		SINGLETRAINING_TYPE_MAPPING.put("antagonize", "对抗");
		
		SINGLETRAINING_TARGET_MAPPING.put("all", "所有队员");
		SINGLETRAINING_TARGET_MAPPING.put("offensive_players", "进攻队员");
		SINGLETRAINING_TARGET_MAPPING.put("defensive_player", "防守队员");
		SINGLETRAINING_TARGET_MAPPING.put("goalkeeper", "守门员");
		
		MATCH_TYPE_MAPPING.put("formal", "正式比赛");
		MATCH_TYPE_MAPPING.put("friendly", "友谊赛");
		
		MATCH_GOAL_TYPE_MAPPING.put("normal", "运动战");
		MATCH_GOAL_TYPE_MAPPING.put("penalty", "点球");
		MATCH_GOAL_TYPE_MAPPING.put("corner", "角球");
		MATCH_GOAL_TYPE_MAPPING.put("freekick", "任意球");
		
		MATCH_GOAL_MODE_MAPPING.put("left", "左脚");
		MATCH_GOAL_MODE_MAPPING.put("right", "右脚");
		MATCH_GOAL_MODE_MAPPING.put("header", "头球");
		
		MATCH_FOUL_TYPE_MAPPING.put("yellow", "黄");
		MATCH_FOUL_TYPE_MAPPING.put("red", "红");
		
		MATCH_FIELD_TYPE_MAPPING.put("home", "主场");
		MATCH_FIELD_TYPE_MAPPING.put("guest", "客场");
		MATCH_FIELD_TYPE_MAPPING.put("neutrality", "中立");
	}
	public static final String USER_SESSION_KEY = "USER_SESSION";
	public static final String UTRAINING_CALENDAR_COLOR_RED_RGB = "#f2663a";
	public static final String UTRAINING_CALENDAR_COLOR_YELLOW_RGB = "#fecd08";
	public static final String UTRAINING_CALENDAR_COLOR_BLUE_RGB = "#1999ce";
	public static final String UTRAINING_CALENDAR_COLOR_GREEN_RGB = "#4eba65";
	public static final String UTRAINING_CALENDAR_COLOR_RED = "red";
	public static final String UTRAINING_CALENDAR_COLOR_YELLOW = "yellow";
	public static final String UTRAINING_CALENDAR_COLOR_BLUE = "blue";
	public static final String UTRAINING_CALENDAR_COLOR_GREEN = "green";

	public static final String MATCH_RESULT_WON = "won";
	public static final String MATCH_RESULT_LOST = "lost";
	public static final String MATCH_RESULT_DRAWN = "drawn";
	
	public static final String MATCH_FOUL_YELLOW_CARD = "yellow";
	public static final String MATCH_FOUL_RED_CARD = "red";
	
	public static final String TACTICS_CATEGORY = "soccer";
	
	public static final String[] VALID_ATTACHMENTS_PIC_TYPES = new String[] {"jpg", "jpeg", "gif", "png", "bmp"};

	public static final String[] VALID_ATTACHMENTS_VIDEO_TYPES = new String[] {"mp4", "mpeg" ,"avi" ,"mkv", "3gp", "mov"};
	public static final String[] VALID_ATTACHMENTS_AUDIO_TYPES = new String[] {"mp3", "wmv", "wma", "au", "mp1", "mp2"};
	public static final String[] VALID_ATTACHMENTS_TEXT_TYPES = new String[] {"txt", "doc","docx","ppt","pptx","pdf","log","xls","xlsx","pps","htm","html","xml","rtp"};
	public static final String MATCH_ASSET_PHOTO_TYPE = "photo";
	public static final String MATCH_ASSET_ATTACH_TYPE = "attachment";
	
	public static final String[] MATCH_DATA_ITEM_KEY_OF_NON_NUMBER = { "player_label" };
	
	public static final String TACTICS_COPY = "-副本";

	public static final String singleTraining_Tactics_Type = "7";
	public static final long starters_tactics_type = 6;
	public static final long place_kick_tactics_type = 9;
	public static final long default_starters_formation_type_id = 1;
	public static final String default_place_kick_type_id = "1";

	public static final String ACTION_AUTH_EMAIL_RESET_PWD = "email_reset_pwd";
	public static final String ACTION_AUTH_EMAIL_ACTIVATE = "email_activate";
	
	public static final String DEFAULT_DOWNLOAD_FILE_NAME = "附件";
	
	public static Map<String, Integer> MENU_ORDER = new HashMap<String, Integer>();
	static {
		//top menu
		MENU_ORDER.put("dashboard", 1);
		MENU_ORDER.put("regular_manage", 2);
		MENU_ORDER.put("match_manage", 3);
		MENU_ORDER.put("people_manage", 4);
		MENU_ORDER.put("tactics_manage", 5);
		MENU_ORDER.put("auxiliary_manage", 6);
		
		//regular_manage menu
		MENU_ORDER.put("utraining/show_calendar", 21);
		MENU_ORDER.put("singletraining/showSingleTrainingList", 22);
		
		//match_manage menu
		MENU_ORDER.put("match/showMatchList", 31);
		MENU_ORDER.put("match/showMatchResultList", 32);
		
		//people_manage menu
		MENU_ORDER.put("user/showCoachList", 41);
		MENU_ORDER.put("user/showPlayerList", 42);
		MENU_ORDER.put("account_manage", 43);
		MENU_ORDER.put("test_manage", 44);
		MENU_ORDER.put("healthdata/gohealthdata", 45);
		
		//tactics_manage menu
		MENU_ORDER.put("starters_setting", 51);
		MENU_ORDER.put("place_kick", 52);
		MENU_ORDER.put("system_tool/showTacticsList", 53);
		
		//auxiliary_manage menu
		MENU_ORDER.put("equipment/showEquipmentList", 61);
	}
	
	public static final String[] MATCH_DATA_ITEM_KEYS = {"run_distance", "target_shoot", "aside_shoot", "break_through", "off_side", "short_pass", "long_pass", "forward_pass", "cross_pass", "fail_pass", "clearance_kick", "foul", "success_steal", "fail_steal", "intercept", "save"};
}
