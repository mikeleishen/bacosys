package com.xinyou.label.domain.viewes;

import java.math.BigDecimal;

public class ITM_WO_REPORT_VIEW {
	private String itm_main_id;
	private String itm_name;
	private String itm_unit;
	private BigDecimal itm_qty;
	private long bdt;
	private long rdt;
	private String rac_name;
	private String rp_ws;
	private String emp_id_list;
	private String sws_id;
	private String wo_id;
	private String lot_id;

	public long getBdt() {
		return bdt;
	}
	public void setBdt(long bdt) {
		this.bdt = bdt;
	}
	public long getRdt() {
		return rdt;
	}
	public void setRdt(long rdt) {
		this.rdt = rdt;
	}
	public String getRac_name() {
		return rac_name;
	}
	public void setRac_name(String rac_name) {
		this.rac_name = rac_name;
	}
	public String getRp_ws() {
		return rp_ws;
	}
	public void setRp_ws(String rp_ws) {
		this.rp_ws = rp_ws;
	}
	public String getEmp_id_list() {
		return emp_id_list;
	}
	public void setEmp_id_list(String emp_id_list) {
		this.emp_id_list = emp_id_list;
	}
	public String getSws_id() {
		return sws_id;
	}
	public void setSws_id(String sws_id) {
		this.sws_id = sws_id;
	}
	public String getWo_id() {
		return wo_id;
	}
	public void setWo_id(String wo_id) {
		this.wo_id = wo_id;
	}
	public String getLot_id() {
		return lot_id;
	}
	public void setLot_id(String lot_id) {
		this.lot_id = lot_id;
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
}

