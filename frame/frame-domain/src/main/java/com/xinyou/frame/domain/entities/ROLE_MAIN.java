package com.xinyou.frame.domain.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ROLE_MAIN")
public class ROLE_MAIN {
	private String role_main_guid;
	private String role_main_id;
	private long created_dt;
	private String created_by;
	private long updated_dt;
	private String updated_by;
	private int is_deleted;
	private String client_guid;
	private String role_name;
	private String role_desc;
	public String getRole_main_guid() {
		return role_main_guid;
	}
	public void setRole_main_guid(String role_main_guid) {
		this.role_main_guid = role_main_guid;
	}
	public String getRole_main_id() {
		return role_main_id;
	}
	public void setRole_main_id(String role_main_id) {
		this.role_main_id = role_main_id;
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
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getRole_desc() {
		return role_desc;
	}
	public void setRole_desc(String role_desc) {
		this.role_desc = role_desc;
	}
}