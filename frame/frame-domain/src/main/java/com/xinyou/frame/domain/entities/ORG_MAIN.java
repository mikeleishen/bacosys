package com.xinyou.frame.domain.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ORG_MAIN")
public class ORG_MAIN {
	private String org_main_guid;
	private String org_main_id;
	private long created_dt;
	private String created_by;
	private long updated_dt;
	private String updated_by;
	private int is_deleted;
	private String client_guid;
	private String org_name;
	private String org_seqno;
	private String node_img;
	private String org_desc;
	private String co_guid;
	public String getOrg_main_guid() {
		return org_main_guid;
	}
	public void setOrg_main_guid(String org_main_guid) {
		this.org_main_guid = org_main_guid;
	}
	public String getOrg_main_id() {
		return org_main_id;
	}
	public void setOrg_main_id(String org_main_id) {
		this.org_main_id = org_main_id;
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
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getOrg_seqno() {
		return org_seqno;
	}
	public void setOrg_seqno(String org_seqno) {
		this.org_seqno = org_seqno;
	}
	public String getOrg_desc() {
		return org_desc;
	}
	public void setOrg_desc(String org_desc) {
		this.org_desc = org_desc;
	}
	public String getCo_guid() {
		return co_guid;
	}
	public void setCo_guid(String co_guid) {
		this.co_guid = co_guid;
	}
	public String getNode_img() {
		return node_img;
	}
	public void setNode_img(String node_img) {
		this.node_img = node_img;
	}
}

