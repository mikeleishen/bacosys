package com.xinyou.label.domain.viewes;

import java.math.BigDecimal;

public class PUR_ITM_VIEW {
		public String getPur_itm_guid() {
		return pur_itm_guid;
	}
	public void setPur_itm_guid(String pur_itm_guid) {
		this.pur_itm_guid = pur_itm_guid;
	}
	public String getPur_doc_id() {
		return pur_doc_id;
	}
	public void setPur_doc_id(String pur_doc_id) {
		this.pur_doc_id = pur_doc_id;
	}
	public String getPur_itm_seqno() {
		return pur_itm_seqno;
	}
	public void setPur_itm_seqno(String pur_itm_seqno) {
		this.pur_itm_seqno = pur_itm_seqno;
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
	public BigDecimal getItm_delivery_qty() {
		return itm_delivery_qty;
	}
	public void setItm_delivery_qty(BigDecimal itm_delivery_qty) {
		this.itm_delivery_qty = itm_delivery_qty;
	}
	private String pur_itm_guid;
	private String pur_doc_id;
	private String pur_itm_seqno;
	private String itm_main_id;
	private String itm_name;
	private String itm_unit;
	private BigDecimal itm_qty;
	private BigDecimal itm_delivery_qty;
}

