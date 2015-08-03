package com.xinyou.label.domain.viewes;
import java.math.BigDecimal;


/** 领料单 单体
 * */
public class RBA_ITM_VIEW {
	public String getRba_itm_guid() {
		return rba_itm_guid;
	}
	public void setRba_itm_guid(String rba_itm_guid) {
		this.rba_itm_guid = rba_itm_guid;
	}
	public String getRba_doc_id() {
		return rba_doc_id;
	}
	public void setRba_doc_id(String rba_doc_id) {
		this.rba_doc_id = rba_doc_id;
	}
	public String getRba_itm_seqno() {
		return rba_itm_seqno;
	}
	public void setRba_itm_seqno(String rba_itm_seqno) {
		this.rba_itm_seqno = rba_itm_seqno;
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
	public String getInv_id() {
		return inv_id;
	}
	public void setInv_id(String inv_id) {
		this.inv_id = inv_id;
	}

	private String rba_itm_guid;
	private String rba_doc_id;
	private String rba_itm_seqno;
	private String itm_main_id;
	private String itm_name;
	private String itm_unit;
	private BigDecimal itm_qty;
	private String inv_id;
}
