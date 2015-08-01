package com.xinyou.frame.domain.entities;

public class COUNTRY_MAIN {
	private String country_main_guid;
	private String country_main_id;
	private long created_dt;
	private String created_by;
	private long updated_dt;
	private String updated_by;
	private int is_deleted;
	private String client_guid;
	private String country_name;
	private String city_guid;
	public String getCountry_main_guid() {
		return country_main_guid;
	}
	public void setCountry_main_guid(String country_main_guid) {
		this.country_main_guid = country_main_guid;
	}
	public String getCountry_main_id() {
		return country_main_id;
	}
	public void setCountry_main_id(String country_main_id) {
		this.country_main_id = country_main_id;
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
	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}
	public String getCity_guid() {
		return city_guid;
	}
	public void setCity_guid(String city_guid) {
		this.city_guid = city_guid;
	}
}
