package com.xinyou.label.domain.viewes;

import java.math.BigDecimal;

public class SWS_RP_VIEW {
	private String guid;
	private BigDecimal finish_qty;
	private BigDecimal scrap_qty;
	private long bg_dt;
	private long rp_dt;
	private String sws_guid;
	private String rp_rac_id;
	private String rp_rac_name;
	private int rp_status;
	private String rp_ws;
	private String erp_doc_type;
	private String erp_doc_id;
	private String emp_ids;
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getErp_doc_type() {
		return erp_doc_type;
	}
	public void setErp_doc_type(String erp_doc_type) {
		this.erp_doc_type = erp_doc_type;
	}
	public String getErp_doc_id() {
		return erp_doc_id;
	}
	public void setErp_doc_id(String erp_doc_id) {
		this.erp_doc_id = erp_doc_id;
	}
	public String getEmp_ids() {
		return emp_ids;
	}
	public void setEmp_ids(String emp_ids) {
		this.emp_ids = emp_ids;
	}
	public String getRp_ws() {
		return rp_ws;
	}
	public void setRp_ws(String rp_ws) {
		this.rp_ws = rp_ws;
	}
	public BigDecimal getFinish_qty() {
		return finish_qty;
	}
	public void setFinish_qty(BigDecimal finish_qty) {
		this.finish_qty = finish_qty;
	}
	public BigDecimal getScrap_qty() {
		return scrap_qty;
	}
	public void setScrap_qty(BigDecimal scrap_qty) {
		this.scrap_qty = scrap_qty;
	}
	public long getBg_dt() {
		return bg_dt;
	}
	public void setBg_dt(long bg_dt) {
		this.bg_dt = bg_dt;
	}
	public long getRp_dt() {
		return rp_dt;
	}
	public void setRp_dt(long rp_dt) {
		this.rp_dt = rp_dt;
	}
	public String getSws_guid() {
		return sws_guid;
	}
	public void setSws_guid(String sws_guid) {
		this.sws_guid = sws_guid;
	}
	public String getRp_rac_id() {
		return rp_rac_id;
	}
	public void setRp_rac_id(String rp_rac_id) {
		this.rp_rac_id = rp_rac_id;
	}
	public String getRp_rac_name() {
		return rp_rac_name;
	}
	public void setRp_rac_name(String rp_rac_name) {
		this.rp_rac_name = rp_rac_name;
	}
	public int getRp_status() {
		return rp_status;
	}
	public void setRp_status(int rp_status) {
		this.rp_status = rp_status;
	}
}

