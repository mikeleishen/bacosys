package com.xinyou.frame.domain.entities;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CO_MAIN")
public class CO_MAIN {
	private String co_main_guid;
	private String co_main_id;
	private long created_dt;
	private String created_by;
	private long updated_dt;
	private String updated_by;
	private int is_deleted;
	private String client_guid;
	private String co_name;
	private String co_addr;
	private String co_desc;
	public String getCo_main_guid() {
		return co_main_guid;
	}
	public void setCo_main_guid(String co_main_guid) {
		this.co_main_guid = co_main_guid;
	}
	public String getCo_main_id() {
		return co_main_id;
	}
	public void setCo_main_id(String co_main_id) {
		this.co_main_id = co_main_id;
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
	public String getCo_name() {
		return co_name;
	}
	public void setCo_name(String co_name) {
		this.co_name = co_name;
	}
	public String getCo_desc() {
		return co_desc;
	}
	public void setCo_desc(String co_desc) {
		this.co_desc = co_desc;
	}
	public String getCo_addr() {
		return co_addr;
	}
	public void setCo_addr(String co_addr) {
		this.co_addr = co_addr;
	}
}
