package cn.sportsdata.webapp.youth.common.bo;

import java.io.Serializable;

public class UserBasicBO implements Serializable {
	private static final long serialVersionUID = -4812518922484871239L;
	
	private String id;
	private String name;
	private String tel;
	private String birthday;
	private String email;
	private String homeAddress;
	private String avatar;
	private String avatarId;
	private String passport;
	private String nationality;
	private String idCard;
	private String birthPlace;
	private String roleName;
	
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
	
	public String getBirthday() {
		return birthday;
	}
	
	public void setBirthday(String birthday) {
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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
