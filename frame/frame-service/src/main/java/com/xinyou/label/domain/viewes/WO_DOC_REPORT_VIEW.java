package com.xinyou.label.domain.viewes;

import java.math.BigDecimal;

public class WO_DOC_REPORT_VIEW {
	
	public String getWo_id() {
		return wo_id;
	}
	public void setWo_id(String wo_id) {
		this.wo_id = wo_id;
	}
	public String getItm_id() {
		return itm_id;
	}
	public void setItm_id(String itm_id) {
		this.itm_id = itm_id;
	}
	public String getItm_name() {
		return itm_name;
	}
	public void setItm_name(String itm_name) {
		this.itm_name = itm_name;
	}
	public BigDecimal getWo_qty() {
		return wo_qty;
	}
	public void setWo_qty(BigDecimal wo_qty) {
		this.wo_qty = wo_qty;
	}
	public BigDecimal getFinish_qty() {
		return finish_qty;
	}
	public void setFinish_qty(BigDecimal finish_qty) {
		this.finish_qty = finish_qty;
	}
	public String getItm_unit() {
		return itm_unit;
	}
	public void setItm_unit(String itm_unit) {
		this.itm_unit = itm_unit;
	}
	
	private String wo_id;
	private String itm_id;
	private String itm_name;
	private BigDecimal wo_qty;
	private BigDecimal finish_qty;
	private String itm_unit;
}
