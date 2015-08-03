package com.xinyou.label.domain.viewes;

import java.math.BigDecimal;

public class ITM_INV_REPORT_VIEW {
	private String itm_main_id;
	private String itm_name;
	private String itm_unit;
	private BigDecimal itm_qty;
	
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

