package com.ymess.pojos;

import java.util.List;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;
import org.springframework.security.core.GrantedAuthority;

@Table(value="users")
public class UserPOJO {

	@PrimaryKey(value="username")
	private String username;
	
	@Column(value="password")
	private String password;
	
	@Column(value="enabled")
	private Boolean enabled;
	
	@Column(value="authority")
	private String authority;

	
	public UserPOJO(String username, String password, Boolean enabled,List<GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.authority = authorities.get(0).toString();
	}

	public UserPOJO() {
		
	}
	
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	
}
