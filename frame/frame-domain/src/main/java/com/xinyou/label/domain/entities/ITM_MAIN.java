package com.xinyou.label.domain.entities;

import java.math.BigDecimal;

/** 原材料 实体类
 * */
public class ITM_MAIN {
	private String itm_main_guid;
	private String itm_main_id;
	private String itm_name;
	private String itm_spec;
	private String itm_unit;
	private String itm_dft_inv;
	private BigDecimal itm_safe_qty;
	private String itm_sn;
	private String def_loc_id;
	
	public String getDef_loc_id() {
		return def_loc_id;
	}
	public void setDef_loc_id(String def_loc_id) {
		this.def_loc_id = def_loc_id;
	}
	public String getItm_main_guid() {
		return itm_main_guid;
	}
	public void setItm_main_guid(String itm_main_guid) {
		this.itm_main_guid = itm_main_guid;
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
	public String getItm_spec() {
		return itm_spec;
	}
	public void setItm_spec(String itm_spec) {
		this.itm_spec = itm_spec;
	}
	public String getItm_unit() {
		return itm_unit;
	}
	public void setItm_unit(String itm_unit) {
		this.itm_unit = itm_unit;
	}
	public String getItm_dft_inv() {
		return itm_dft_inv;
	}
	public void setItm_dft_inv(String itm_dft_inv) {
		this.itm_dft_inv = itm_dft_inv;
	}
	public BigDecimal getItm_safe_qty() {
		return itm_safe_qty;
	}
	public void setItm_safe_qty(BigDecimal itm_safe_qty) {
		this.itm_safe_qty = itm_safe_qty;
	}
	public String getItm_sn() {
		return itm_sn;
	}
	public void setItm_sn(String itm_sn) {
		this.itm_sn = itm_sn;
	}
}
