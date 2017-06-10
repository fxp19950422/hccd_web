package cn.sportsdata.webapp.youth.common.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sportsdata.webapp.youth.common.constants.Constants;

public class UserVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = -5992223638569304676L;
	
	private String id;
	private String name;
	private String phone;
	private String mobile;
	private String address;
	private String gender;
	
	private String role;

	private String tel;
	private Timestamp birthday;
	private String email;
	private String homeAddress;
	private String avatar;
	private String avatarId;
	private String passport;
	private String nationality;
	private String idCard;
	private String birthPlace;
	private String doctorCode;
	
	

	private String userName;
	private String displayName;
	
	private String password;
	
	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private List<UserExtVO> userExtInfoList;
	
	
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getId() {
		return id;
	}
	
	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTel() {
		return tel;
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public Timestamp getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getHomeAddress() {
		return homeAddress;
	}
	
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	
	public String getAvatar() {
		return avatar;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public String getPassport() {
		return passport;
	}
	
	public void setPassport(String passport) {
		this.passport = passport;
	}
	
	public String getNationality() {
		return nationality;
	}
	
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public String getIdCard() {
		return idCard;
	}
	
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	public String getBirthPlace() {
		return birthPlace;
	}
	
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public List<UserExtVO> getUserExtInfoList() {
		return userExtInfoList;
	}

	public void setUserExtInfoList(List<UserExtVO> userExtInfoList) {
		this.userExtInfoList = userExtInfoList;
	}

	//--------------------below properties are used for players--------------------//
	private Map<String, String> userExtInfoMap;
	public Map<String, String> getUserExtInfoMap() {
		if(userExtInfoMap == null) {
			userExtInfoMap = new HashMap<String, String>();
			
			if(userExtInfoList != null) {
				for(UserExtVO userExt : userExtInfoList) {
					userExtInfoMap.put(userExt.getItemName(), userExt.getItemValue());
				}
			}
		}
		
		return userExtInfoMap;
	}
	
	private String translatedPosition;
	public String getTranslatedPosition() {
		String value = getUserExtInfoMap().get("professional_primary_position");
		translatedPosition = Constants.POSITION_MAPPING.get(value);
		
		return translatedPosition;
	}
	
	private String translatedSecondaryPosition;
	public String getTranslatedSecondaryPosition() {
		String value = getUserExtInfoMap().get("professional_secondary_position");
		translatedSecondaryPosition = Constants.POSITION_MAPPING.get(value);
		
		return translatedSecondaryPosition;
	}
	
	private String translatedPreferredFoot;
	public String getTranslatedPreferredFoot() {
		String value = getUserExtInfoMap().get("professional_preferred_foot");
		translatedPreferredFoot = Constants.PREFERRED_FOOT_MAPPING.get(value);
		
		return translatedPreferredFoot;
	}
	
	private String translatedPhysique;
	public String getTranslatedPhysique() {
		String value = getUserExtInfoMap().get("professional_physique");
		translatedPhysique = Constants.PHYSIQUE_MAPPING.get(value);
		
		return translatedPhysique;
	}
	
	private String translatedProfessionType;
	public String getTranslatedProfessionType() {
		String value = getUserExtInfoMap().get("professional_type");
		translatedProfessionType = Constants.PROFESSIONAL_TYPE_MAPPING.get(value);
		
		return translatedProfessionType;
	}
	
	private String translatedOverallRating;
	public String getTranslatedOverallRating() {
		String value = getUserExtInfoMap().get("technical_overall_rating");
		translatedOverallRating = Constants.RATING_MAPPING.get(value);
		
		return translatedOverallRating;
	}
	
	private String translatedTechniqueRating;
	public String getTranslatedTechniqueRating() {
		String value = getUserExtInfoMap().get("technical_technique_rating");
		translatedTechniqueRating = Constants.RATING_MAPPING.get(value);
		
		return translatedTechniqueRating;
	}
	
	private String translatedTeamworkRating;
	public String getTranslatedTeamworkRating() {
		String value = getUserExtInfoMap().get("technical_teamwork_rating");
		translatedTeamworkRating = Constants.RATING_MAPPING.get(value);
		
		return translatedTeamworkRating;
	}
	
	private String translatedMentalityRating;
	public String getTranslatedMentalityRating() {
		String value = getUserExtInfoMap().get("technical_mentality_rating");
		translatedMentalityRating = Constants.RATING_MAPPING.get(value);
		
		return translatedMentalityRating;
	}
	
	private String translatedFitnessRating;
	public String getTranslatedFitnessRating() {
		String value = getUserExtInfoMap().get("technical_fitness_rating");
		translatedFitnessRating = Constants.RATING_MAPPING.get(value);
		
		return translatedFitnessRating;
	}
	
	private String translatedCoachType;
	public String getTranslatedCoachType() {
		String value = getUserExtInfoMap().get("coach_type");
		translatedCoachType = Constants.COACH_TYPE_MAPPING.get(value);
		
		return translatedCoachType;
	}
	
	private boolean checked;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
