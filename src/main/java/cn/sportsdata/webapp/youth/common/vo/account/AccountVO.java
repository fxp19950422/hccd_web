package cn.sportsdata.webapp.youth.common.vo.account;

import java.io.Serializable;
import java.util.Collection;

import cn.sportsdata.webapp.youth.common.vo.BaseVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AccountVO extends BaseVO implements Serializable, UserDetails {
	private static final long serialVersionUID = 7922477308103836118L;

	private String id;
	private String username;

	private boolean curLogin = false;
	/**
	 * @return the curLogin
	 */
	public boolean isCurLogin() {
		return curLogin;
	}

	/**
	 * @param curLogin the curLogin to set
	 */
	public void setCurLogin(boolean curLogin) {
		this.curLogin = curLogin;
	}

	private String password;
	private String repassword;
	private long loginCount;
	private String email;
	private String avatar;
	private String avatarId;
	private String mobile;
	
	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	private String birthday;
	private String name;
	
	private String encryptedPassword;

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(long loginCount) {
		this.loginCount = loginCount;
	}
	
	/**
	 * @return the encryptedPassword
	 */
	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	/**
	 * @param encryptedPassword the encryptedPassword to set
	 */
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
