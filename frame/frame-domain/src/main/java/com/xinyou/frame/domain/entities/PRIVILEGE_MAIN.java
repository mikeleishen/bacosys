package com.xinyou.frame.domain.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PRIVILEGE_MAIN")
public class PRIVILEGE_MAIN {
	private String privilege_main_guid;
	private String privilege_main_id;
	private long created_dt;
	private String created_by;
	private long updated_dt;
	private String updated_by;
	private int is_deleted;
	private String client_guid;
	private String privilege_name;
	private String privilege_desc;
	private String doc_guid;
	private String doc_id;
	private String doc_name;
	private String role_guid;
	private String role_privilege_guid;
	public String getPrivilege_main_guid() {
		return privilege_main_guid;
	}
	public void setPrivilege_main_guid(String privilege_main_guid) {
		this.privilege_main_guid = privilege_main_guid;
	}
	public String getPrivilege_main_id() {
		return privilege_main_id;
	}
	public void setPrivilege_main_id(String privilege_main_id) {
		this.privilege_main_id = privilege_main_id;
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
	public String getPrivilege_name() {
		return privilege_name;
	}
	public void setPrivilege_name(String privilege_name) {
		this.privilege_name = privilege_name;
	}
	public String getPrivilege_desc() {
		return privilege_desc;
	}
	public void setPrivilege_desc(String privilege_desc) {
		this.privilege_desc = privilege_desc;
	}
	public String getDoc_guid() {
		return doc_guid;
	}
	public void setDoc_guid(String doc_guid) {
		this.doc_guid = doc_guid;
	}
	public String getDoc_name() {
		return doc_name;
	}
	public void setDoc_name(String doc_name) {
		this.doc_name = doc_name;
	}
	public String getDoc_id() {
		return doc_id;
	}
	public void setDoc_id(String doc_id) {
		this.doc_id = doc_id;
	}
	public String getRole_guid() {
		return role_guid;
	}
	public void setRole_guid(String role_guid) {
		this.role_guid = role_guid;
	}
	public String getRole_privilege_guid() {
		return role_privilege_guid;
	}
	public void setRole_privilege_guid(String role_privilege_guid) {
		this.role_privilege_guid = role_privilege_guid;
	}
}
