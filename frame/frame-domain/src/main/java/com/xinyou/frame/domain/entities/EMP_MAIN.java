package com.xinyou.frame.domain.entities;

public class EMP_MAIN {
	private String emp_main_guid;
	private String emp_main_id;
	private long created_dt;
    private String created_by;
    private long updated_dt;
    private String updated_by;
    private String client_guid;
    private int is_deleted;
    private int pms_level;
    private String data_ver; 
	private String emp_name;
	private int emp_type;
	private String emp_lp;
	private String emp_sp;
	private int emp_status;
	private String emp_memo;
	private String emp_sys_id;
	private String emp_baco;
	
	public String getEmp_sys_id() {
		return emp_sys_id;
	}
	public void setEmp_sys_id(String emp_sys_id) {
		this.emp_sys_id = emp_sys_id;
	}
	public String getEmp_baco() {
		return emp_baco;
	}
	public void setEmp_baco(String emp_baco) {
		this.emp_baco = emp_baco;
	}
	public String getEmp_main_guid() {
		return emp_main_guid;
	}
	public void setEmp_main_guid(String emp_main_guid) {
		this.emp_main_guid = emp_main_guid;
	}
	public String getEmp_main_id() {
		return emp_main_id;
	}
	public void setEmp_main_id(String emp_main_id) {
		this.emp_main_id = emp_main_id;
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
	public String getClient_guid() {
		return client_guid;
	}
	public void setClient_guid(String client_guid) {
		this.client_guid = client_guid;
	}
	public int getIs_deleted() {
		return is_deleted;
	}
	public void setIs_deleted(int is_deleted) {
		this.is_deleted = is_deleted;
	}
	public int getPms_level() {
		return pms_level;
	}
	public void setPms_level(int pms_level) {
		this.pms_level = pms_level;
	}
	public String getData_ver() {
		return data_ver;
	}
	public void setData_ver(String data_ver) {
		this.data_ver = data_ver;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public int getEmp_type() {
		return emp_type;
	}
	public void setEmp_type(int emp_type) {
		this.emp_type = emp_type;
	}
	public String getEmp_lp() {
		return emp_lp;
	}
	public void setEmp_lp(String emp_lp) {
		this.emp_lp = emp_lp;
	}
	public String getEmp_sp() {
		return emp_sp;
	}
	public void setEmp_sp(String emp_sp) {
		this.emp_sp = emp_sp;
	}
	public int getEmp_status() {
		return emp_status;
	}
	public void setEmp_status(int emp_status) {
		this.emp_status = emp_status;
	}
	public String getEmp_memo() {
		return emp_memo;
	}
	public void setEmp_memo(String emp_memo) {
		this.emp_memo = emp_memo;
	}
}
