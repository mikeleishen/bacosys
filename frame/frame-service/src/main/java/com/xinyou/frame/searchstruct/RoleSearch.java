package com.xinyou.frame.searchstruct;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SysRoleSearch")
public class RoleSearch {
	private String role_guid;
	private String role_id;
	private String role_name;
	private String role_sys_guid;
	private String sys_id;
	private String sys_name;
	private int page_no;
	private int page_size;
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
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
	public String getRole_guid() {
		return role_guid;
	}
	public void setRole_guid(String role_guid) {
		this.role_guid = role_guid;
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
	public String getRole_sys_guid() {
		return role_sys_guid;
	}
	public void setRole_sys_guid(String role_sys_guid) {
		this.role_sys_guid = role_sys_guid;
	}
}
