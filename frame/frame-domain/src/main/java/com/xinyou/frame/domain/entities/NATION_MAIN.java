package com.xinyou.frame.domain.entities;

public class NATION_MAIN {
	private String nation_main_guid;
	private String nation_main_id;
	private long created_dt;
	private String created_by;
	private String updated_dt;
	private long updated_by;
	private int is_deleted;
	private String client_guid;
	private String nation_name;
	public String getNation_main_guid() {
		return nation_main_guid;
	}
	public void setNation_main_guid(String nation_main_guid) {
		this.nation_main_guid = nation_main_guid;
	}
	public String getNation_main_id() {
		return nation_main_id;
	}
	public void setNation_main_id(String nation_main_id) {
		this.nation_main_id = nation_main_id;
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
	public String getUpdated_dt() {
		return updated_dt;
	}
	public void setUpdated_dt(String updated_dt) {
		this.updated_dt = updated_dt;
	}
	public long getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(long updated_by) {
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
	public String getNation_name() {
		return nation_name;
	}
	public void setNation_name(String nation_name) {
		this.nation_name = nation_name;
	}
}

