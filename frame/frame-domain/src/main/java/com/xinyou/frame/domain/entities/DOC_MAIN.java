package com.xinyou.frame.domain.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DOC_MAIN")
public class DOC_MAIN {
	private String doc_main_guid;
	private String doc_main_id;
	private long created_dt;
	private String created_by;
	private long updated_dt;
	private String updated_by;
	private int is_deleted;
	private String client_guid;
	private String doc_name;
	private String doc_status_id;
	private String doc_pre_tag;
	private String doc_mid_tag_id;
	private int doc_seq_no;
	private int doc_seq_no_len;
	private String doc_desc;
	private String doc_seqno_id;
	public String getDoc_main_guid() {
		return doc_main_guid;
	}
	public void setDoc_main_guid(String doc_main_guid) {
		this.doc_main_guid = doc_main_guid;
	}
	public String getDoc_main_id() {
		return doc_main_id;
	}
	public void setDoc_main_id(String doc_main_id) {
		this.doc_main_id = doc_main_id;
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
	public String getDoc_name() {
		return doc_name;
	}
	public void setDoc_name(String doc_name) {
		this.doc_name = doc_name;
	}
	public String getDoc_desc() {
		return doc_desc;
	}
	public void setDoc_desc(String doc_desc) {
		this.doc_desc = doc_desc;
	}
	public String getDoc_pre_tag() {
		return doc_pre_tag;
	}
	public void setDoc_pre_tag(String doc_pre_tag) {
		this.doc_pre_tag = doc_pre_tag;
	}
	public String getDoc_mid_tag_id() {
		return doc_mid_tag_id;
	}
	public void setDoc_mid_tag_id(String doc_mid_tag_id) {
		this.doc_mid_tag_id = doc_mid_tag_id;
	}
	public String getDoc_status_id() {
		return doc_status_id;
	}
	public void setDoc_status_id(String doc_status_id) {
		this.doc_status_id = doc_status_id;
	}
	public int getDoc_seq_no() {
		return doc_seq_no;
	}
	public void setDoc_seq_no(int doc_seq_no) {
		this.doc_seq_no = doc_seq_no;
	}
	public int getDoc_seq_no_len() {
		return doc_seq_no_len;
	}
	public void setDoc_seq_no_len(int doc_seq_no_len) {
		this.doc_seq_no_len = doc_seq_no_len;
	}
	public String getDoc_seqno_id() {
		return doc_seqno_id;
	}
	public void setDoc_seqno_id(String doc_seqno_id) {
		this.doc_seqno_id = doc_seqno_id;
	}
}

