package com.xinyou.frame.domain.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ROLE_FUN")
public class ROLE_FUN {
	private String role_fun_guid;
	private String role_fun_id;
	private long created_dt;
	private String created_by;
	private long updated_dt;
	private String updated_by;
	private int is_deleted;
	private String client_guid;
	private String role_guid;
	private String fun_guid;
	private String sys_guid;
	public String getRole_fun_guid() {
		return role_fun_guid;
	}
	public void setRole_fun_guid(String role_fun_guid) {
		this.role_fun_guid = role_fun_guid;
	}
	public String getRole_fun_id() {
		return role_fun_id;
	}
	public void setRole_fun_id(String role_fun_id) {
		this.role_fun_id = role_fun_id;
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
	public String getFun_guid() {
		return fun_guid;
	}
	public void setFun_guid(String fun_guid) {
		this.fun_guid = fun_guid;
	}
	public String getSys_guid() {
		return sys_guid;
	}
	public void setSys_guid(String sys_guid) {
		this.sys_guid = sys_guid;
	}
}
