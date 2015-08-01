package com.xinyou.frame.domain.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "BIZ_SYS")
public class BIZ_SYS {
	private String biz_sys_guid;
	private String biz_sys_id;
	private long created_dt;
	private String created_by;
	private long updated_dt;
	private String updated_by;
	private int is_deleted;
	private String client_guid;
	private String sys_name;
	private String sys_url;
	private String sys_desc;
	private String role_sys_guid;
	private int is_default_sys;
	private int sys_pc;
	private int sys_mobile;
	public String getBiz_sys_guid() {
		return biz_sys_guid;
	}
	public void setBiz_sys_guid(String biz_sys_guid) {
		this.biz_sys_guid = biz_sys_guid;
	}
	public String getBiz_sys_id() {
		return biz_sys_id;
	}
	public void setBiz_sys_id(String biz_sys_id) {
		this.biz_sys_id = biz_sys_id;
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
	public String getSys_name() {
		return sys_name;
	}
	public void setSys_name(String sys_name) {
		this.sys_name = sys_name;
	}
	public String getSys_url() {
		return sys_url;
	}
	public void setSys_url(String sys_url) {
		this.sys_url = sys_url;
	}
	public String getSys_desc() {
		return sys_desc;
	}
	public void setSys_desc(String sys_desc) {
		this.sys_desc = sys_desc;
	}
	public String getRole_sys_guid() {
		return role_sys_guid;
	}
	public void setRole_sys_guid(String role_sys_guid) {
		this.role_sys_guid = role_sys_guid;
	}
	public int getIs_default_sys() {
		return is_default_sys;
	}
	public void setIs_default_sys(int is_default_sys) {
		this.is_default_sys = is_default_sys;
	}
	public int getSys_pc() {
		return sys_pc;
	}
	public void setSys_pc(int sys_pc) {
		this.sys_pc = sys_pc;
	}
	public int getSys_mobile() {
		return sys_mobile;
	}
	public void setSys_mobile(int sys_mobile) {
		this.sys_mobile = sys_mobile;
	}
}
