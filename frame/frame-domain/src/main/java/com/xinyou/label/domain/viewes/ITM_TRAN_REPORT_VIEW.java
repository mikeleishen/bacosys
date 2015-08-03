package com.xinyou.label.domain.viewes;

import java.math.BigDecimal;

public class ITM_TRAN_REPORT_VIEW {
	private String itm_main_id;
	private String itm_name;
	private String itm_unit;
	private BigDecimal itm_qty;
	private long tran_dt;
	private String operator;
	private int tran_type;
	private String tran_reason;
	private String ctn_baco;
	private String tran_guid;
	
	public String getTran_guid() {
		return tran_guid;
	}
	public void setTran_guid(String tran_guid) {
		this.tran_guid = tran_guid;
	}
	public String getCtn_baco() {
		return ctn_baco;
	}
	public void setCtn_baco(String ctn_baco) {
		this.ctn_baco = ctn_baco;
	}
	public String getItm_main_id() {
		return itm_main_id;
	}
	public void setItm_main_id(String itm_main_id) {
		this.itm_main_id = itm_main_id;
	}
	public String getItm_name() {
		return itm_name;
	}
	public void setItm_name(String itm_name) {
		this.itm_name = itm_name;
	}
	public String getItm_unit() {
		return itm_unit;
	}
	public void setItm_unit(String itm_unit) {
		this.itm_unit = itm_unit;
	}
	public BigDecimal getItm_qty() {
		return itm_qty;
	}
	public void setItm_qty(BigDecimal itm_qty) {
		this.itm_qty = itm_qty;
	}
	public long getTran_dt() {
		return tran_dt;
	}
	public void setTran_dt(long tran_dt) {
		this.tran_dt = tran_dt;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public int getTran_type() {
		return tran_type;
	}
	public void setTran_type(int tran_type) {
		this.tran_type = tran_type;
	}
	public String getTran_reason() {
		return tran_reason;
	}
	public void setTran_reason(String tran_reason) {
		this.tran_reason = tran_reason;
	}
}

