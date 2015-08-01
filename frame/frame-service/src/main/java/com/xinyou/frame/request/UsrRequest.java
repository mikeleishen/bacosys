package com.xinyou.frame.request;

import com.xinyou.frame.domain.entities.USR_MAIN;

public class UsrRequest {
	
	
	
     public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsr_guid() {
		return usr_guid;
	}

	public void setUsr_guid(String usr_guid) {
		this.usr_guid = usr_guid;
	}

	public String getUsr_name() {
		return usr_name;
	}

	public void setUsr_name(String usr_name) {
		this.usr_name = usr_name;
	}

	public String getUsr_pswd() {
		return usr_pswd;
	}

	public void setUsr_pswd(String usr_pswd) {
		this.usr_pswd = usr_pswd;
	}

	public String getSys_guid() {
		return sys_guid;
	}

	public void setSys_guid(String sys_guid) {
		this.sys_guid = sys_guid;
	}

	public String getRole_guid() {
		return role_guid;
	}

	public void setRole_guid(String role_guid) {
		this.role_guid = role_guid;
	}

	public int getPage_no() {
		return page_no;
	}

	public void setPage_no(int page_no) {
		this.page_no = page_no;
	}

	public int getPage_size() {
		return page_size;
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}

	public USR_MAIN getUsr_main() {
		return usr_main;
	}

	public void setUsr_main(USR_MAIN usr_main) {
		this.usr_main = usr_main;
	}

	private String token;
     private String usr_guid;
     private String usr_name;
     private String usr_pswd;
     private String sys_guid;
     private String role_guid;
     private int page_no;
     private int page_size;
     
     private USR_MAIN usr_main;
     
     
}
