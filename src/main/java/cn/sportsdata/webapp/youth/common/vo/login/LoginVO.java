package cn.sportsdata.webapp.youth.common.vo.login;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.BaseVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cn.sportsdata.webapp.youth.common.vo.Menu;
import cn.sportsdata.webapp.youth.common.vo.account.AccountVO;

public class LoginVO extends BaseVO implements UserDetails,Serializable{
	private static final long serialVersionUID = 7922477308103836118L;
	private String id;
    private String userName;
    private String password;
    private String email;
    private String name;
    private long loginCount;
    private long orgID;
    private int privilegeTypeID;
    private String birthday;
    private int type;
    private String avatar;
    private String avatar_id;
    
    public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	
	
	public String getAvatar_id() {
		return avatar_id;
	}

	public void setAvatar_id(String avatar_id) {
		this.avatar_id = avatar_id;
	}

	public LoginVO(){
    	
    }
    
    public LoginVO(AccountVO account){
    	this.id = account.getId();
    	this.userName = account.getUsername();
    	this.password = account.getPassword();
    	this.email = account.getEmail();
    	this.name = account.getName();
    	this.loginCount = account.getLoginCount();
    	this.birthday = account.getBirthday();
    	this.avatar = account.getAvatar();
    	this.avatar_id = account.getAvatarId();
    }

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getPrivilegeTypeID() {
		return privilegeTypeID;
	}

	public void setPrivilegeTypeID(int privilegeTypeID) {
		this.privilegeTypeID = privilegeTypeID;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	protected List<Menu> menus = new ArrayList<Menu>();



	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the loginCount
	 */
	public long getLoginCount() {
		return loginCount;
	}

	/**
	 * @param loginCount the loginCount to set
	 */
	public void setLoginCount(long loginCount) {
		this.loginCount = loginCount;
	}

	/**
	 * @return the orgID
	 */
	public long getOrgID() {
		return orgID;
	}

	/**
	 * @param orgID the orgID to set
	 */
	public void setOrgID(long orgID) {
		this.orgID = orgID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userName;
	}

}
