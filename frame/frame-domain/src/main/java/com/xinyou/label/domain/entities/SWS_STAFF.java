package com.xinyou.label.domain.entities;

/**
 *  报工单相关人员
 * */
public class SWS_STAFF extends EntityBase {
	private String sws_guid;
	private String emp_guid;
	private String emp_id;
	private String emp_name;
	
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getSws_guid() {
		return sws_guid;
	}
	public void setSws_guid(String sws_guid) {
		this.sws_guid = sws_guid;
	}
	public String getEmp_guid() {
		return emp_guid;
	}
	public void setEmp_guid(String emp_guid) {
		this.emp_guid = emp_guid;
	}
}
