package com.xinyou.frame.domain.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ROLE_SYS")
public class ROLE_SYS {
	private String role_sys_guid;
	private String role_sys_id;
	private long created_dt;
	private String created_by;
	private long updated_dt;
	private String updated_by;
	private int is_deleted;
	private String client_guid;
	private String role_guid;
	private String sys_guid;
	private int is_default_sys;
	public String getRole_sys_guid() {
		return role_sys_guid;
	}
	public void setRole_sys_guid(String role_sys_guid) {
		this.role_sys_guid = role_sys_guid;
	}
	public String getRole_sys_id() {
		return role_sys_id;
	}
	public void setRole_sys_id(String role_sys_id) {
		this.role_sys_id = role_sys_id;
	}
	public long getCreated_dt() {
		return created_dt;
	}
	public void setCreated_dt(long created_dt) {
		this.created_dt = created_dt;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public long getUpdated_dt() {
		return updated_dt;
	}
	public void setUpdated_dt(long updated_dt) {
		this.updated_dt = updated_dt;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	public int getIs_deleted() {
		return is_deleted;
	}
	public void setIs_deleted(int is_deleted) {
		this.is_deleted = is_deleted;
	}
	public String getClient_guid() {
		return client_guid;
	}
	public void setClient_guid(String client_guid) {
		this.client_guid = client_guid;
	}
	public String getRole_guid() {
		return role_guid;
	}
	public void setRole_guid(String role_guid) {
		this.role_guid = role_guid;
	}
	public String getSys_guid() {
		return sys_guid;
	}
	public void setSys_guid(String sys_guid) {
		this.sys_guid = sys_guid;
	}
	public int getIs_default_sys() {
		return is_default_sys;
	}
	public void setIs_default_sys(int is_default_sys) {
		this.is_default_sys = is_default_sys;
	}

}
