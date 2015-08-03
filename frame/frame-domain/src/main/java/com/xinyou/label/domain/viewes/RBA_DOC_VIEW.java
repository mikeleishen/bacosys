package com.xinyou.label.domain.viewes;

import java.util.List;

/**  领料单 单头
 * */
public class RBA_DOC_VIEW {
	private String rba_doc_guid;
	private String rba_doc_id;
	private String wo_doc_id;
	private int label_status;
	private int rba_status;
	private String rba_doc_remark;
	private String rba_doc_dt;
	private String itm_id;
	private String erp_emp_id;
	private List<RBA_ITM_VIEW> itm_list;
	
	public String getErp_emp_id() {
		return erp_emp_id;
	}
	public void setErp_emp_id(String erp_emp_id) {
		this.erp_emp_id = erp_emp_id;
	}
	public String getItm_id() {
		return itm_id;
	}
	public void setItm_id(String itm_id) {
		this.itm_id = itm_id;
	}
	public List<RBA_ITM_VIEW> getItm_list() {
		return itm_list;
	}
	public void setItm_list(List<RBA_ITM_VIEW> itm_list) {
		this.itm_list = itm_list;
	}
	public String getRba_doc_guid() {
		return rba_doc_guid;
	}
	public void setRba_doc_guid(String rba_doc_guid) {
		this.rba_doc_guid = rba_doc_guid;
	}
	public String getRba_doc_id() {
		return rba_doc_id;
	}
	public void setRba_doc_id(String rba_doc_id) {
		this.rba_doc_id = rba_doc_id;
	}
	public String getWo_doc_id() {
		return wo_doc_id;
	}
	public void setWo_doc_id(String wo_doc_id) {
		this.wo_doc_id = wo_doc_id;
	}
	public int getLabel_status() {
		return label_status;
	}
	public void setLabel_status(int label_status) {
		this.label_status = label_status;
	}
	public int getRba_status() {
		return rba_status;
	}
	public void setRba_status(int rba_status) {
		this.rba_status = rba_status;
	}
	public String getRba_doc_remark() {
		return rba_doc_remark;
	}
	public void setRba_doc_remark(String rba_doc_remark) {
		this.rba_doc_remark = rba_doc_remark;
	}
	public String getRba_doc_dt() {
		return rba_doc_dt;
	}
	public void setRba_doc_dt(String rba_doc_dt) {
		this.rba_doc_dt = rba_doc_dt;
	}
}

