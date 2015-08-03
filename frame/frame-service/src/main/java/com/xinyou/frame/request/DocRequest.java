package com.xinyou.frame.request;


import com.xinyou.frame.domain.entities.DOC_MAIN;
import com.xinyou.frame.domain.entities.PRIVILEGE_MAIN;

public class DocRequest {
	private String token;
	private String doc_guid;
	private String doc_id;
	private String doc_name;
	private String privilege_guid;
	private String privilege_id;
	private String privilege_name;
	private String role_guid;
	private String param_type;
	private int page_no;
	private int page_size;
	
	private DOC_MAIN doc;
	private PRIVILEGE_MAIN privilege;

	public DOC_MAIN getDoc() {
		return doc;
	}

	public void setDoc(DOC_MAIN doc) {
		this.doc = doc;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDoc_guid() {
		return doc_guid;
	}

	public void setDoc_guid(String doc_guid) {
		this.doc_guid = doc_guid;
	}

	public String getDoc_id() {
		return doc_id;
	}

	public void setDoc_id(String doc_id) {
		this.doc_id = doc_id;
	}

	public String getDoc_name() {
		return doc_name;
	}

	public void setDoc_name(String doc_name) {
		this.doc_name = doc_name;
	}

	public String getPrivilege_guid() {
		return privilege_guid;
	}

	public void setPrivilege_guid(String privilege_guid) {
		this.privilege_guid = privilege_guid;
	}

	public String getPrivilege_id() {
		return privilege_id;
	}

	public void setPrivilege_id(String privilege_id) {
		this.privilege_id = privilege_id;
	}

	public String getPrivilege_name() {
		return privilege_name;
	}

	public void setPrivilege_name(String privilege_name) {
		this.privilege_name = privilege_name;
	}

	public String getRole_guid() {
		return role_guid;
	}

	public void setRole_guid(String role_guid) {
		this.role_guid = role_guid;
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

	public String getParam_type() {
		return param_type;
	}

	public void setParam_type(String param_type) {
		this.param_type = param_type;
	}

	public PRIVILEGE_MAIN getPrivilege() {
		return privilege;
	}

	public void setPrivilege(PRIVILEGE_MAIN privilege) {
		this.privilege = privilege;
	}
}

