package com.xinyou.frame.request;

import com.xinyou.frame.domain.entities.BIZ_SYS;

public class SysRequest {
	
	  public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSys_guid() {
		return sys_guid;
	}

	public void setSys_guid(String sys_guid) {
		this.sys_guid = sys_guid;
	}

	public String getSys_id() {
		return sys_id;
	}

	public void setSys_id(String sys_id) {
		this.sys_id = sys_id;
	}

	public String getSys_name() {
		return sys_name;
	}

	public void setSys_name(String sys_name) {
		this.sys_name = sys_name;
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

	public BIZ_SYS getBiz_sys() {
		return biz_sys;
	}

	public void setBiz_sys(BIZ_SYS biz_sys) {
		this.biz_sys = biz_sys;
	}

	private String token;
	  private String sys_guid;
	  private String sys_id;
	  private String sys_name;
	  private int    page_no;
	  private int    page_size;
	 
	  private BIZ_SYS biz_sys;

}
