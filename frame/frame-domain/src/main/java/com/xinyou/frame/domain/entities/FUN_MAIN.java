package com.xinyou.frame.domain.entities;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FUN_MAIN")
public class FUN_MAIN {
	private String fun_main_guid;
	private String fun_main_id;
	private long created_dt;
	private String created_by;
	private long updated_dt;
	private String updated_by;
	private int is_deleted;
	private String client_guid;
	private String fun_name;
	private String fun_seqno;
	private String fun_param;
	private String fun_ass;
	private String fun_class;
	private String fun_method;
	private String fun_url;
	private int node_expand;
	private String node_img;
	private String fun_desc;
	private String biz_sys_guid;
	public String getFun_main_guid() {
		return fun_main_guid;
	}
	public void setFun_main_guid(String fun_main_guid) {
		this.fun_main_guid = fun_main_guid;
	}
	public String getFun_main_id() {
		return fun_main_id;
	}
	public void setFun_main_id(String fun_main_id) {
		this.fun_main_id = fun_main_id;
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
	public String getFun_name() {
		return fun_name;
	}
	public void setFun_name(String fun_name) {
		this.fun_name = fun_name;
	}
	public String getFun_seqno() {
		return fun_seqno;
	}
	public void setFun_seqno(String fun_seqno) {
		this.fun_seqno = fun_seqno;
	}
	public String getFun_param() {
		return fun_param;
	}
	public void setFun_param(String fun_param) {
		this.fun_param = fun_param;
	}
	public String getFun_url() {
		return fun_url;
	}
	public void setFun_url(String fun_url) {
		this.fun_url = fun_url;
	}
	public String getFun_desc() {
		return fun_desc;
	}
	public void setFun_desc(String fun_desc) {
		this.fun_desc = fun_desc;
	}
	public String getBiz_sys_guid() {
		return biz_sys_guid;
	}
	public void setBiz_sys_guid(String biz_sys_guid) {
		this.biz_sys_guid = biz_sys_guid;
	}
	public String getNode_img() {
		return node_img;
	}
	public void setNode_img(String node_img) {
		this.node_img = node_img;
	}
	public String getFun_ass() {
		return fun_ass;
	}
	public void setFun_ass(String fun_ass) {
		this.fun_ass = fun_ass;
	}
	public String getFun_class() {
		return fun_class;
	}
	public void setFun_class(String fun_class) {
		this.fun_class = fun_class;
	}
	public String getFun_method() {
		return fun_method;
	}
	public void setFun_method(String fun_method) {
		this.fun_method = fun_method;
	}
	public int getNode_expand() {
		return node_expand;
	}
	public void setNode_expand(int node_expand) {
		this.node_expand = node_expand;
	}
	
}