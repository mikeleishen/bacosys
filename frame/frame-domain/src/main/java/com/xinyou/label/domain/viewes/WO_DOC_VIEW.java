package com.xinyou.label.domain.viewes;

import java.math.BigDecimal;

public class WO_DOC_VIEW {
	
	public String getWo_doc_guid() {
		return wo_doc_guid;
	}
	public void setWo_doc_guid(String wo_doc_guid) {
		this.wo_doc_guid = wo_doc_guid;
	}
	public String getWo_doc_id() {
		return wo_doc_id;
	}
	public void setWo_doc_id(String wo_doc_id) {
		this.wo_doc_id = wo_doc_id;
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
	public BigDecimal getItm_qty() {
		return itm_qty;
	}
	public void setItm_qty(BigDecimal itm_qty) {
		this.itm_qty = itm_qty;
	}
	public String getItm_unit() {
		return itm_unit;
	}
	public void setItm_unit(String itm_unit) {
		this.itm_unit = itm_unit;
	}
	public BigDecimal getItm_finish_qty() {
		return itm_finish_qty;
	}
	public void setItm_finish_qty(BigDecimal itm_finish_qty) {
		this.itm_finish_qty = itm_finish_qty;
	}
	public BigDecimal getItm_scrap_qty() {
		return itm_scrap_qty;
	}
	public void setItm_scrap_qty(BigDecimal itm_scrap_qty) {
		this.itm_scrap_qty = itm_scrap_qty;
	}
	public int getWo_status() {
		return wo_status;
	}
	public void setWo_status(int wo_status) {
		this.wo_status = wo_status;
	}
	public BigDecimal getItm_bind_qty() {
		return itm_bind_qty;
	}
	public void setItm_bind_qty(BigDecimal itm_bind_qty) {
		this.itm_bind_qty = itm_bind_qty;
	}
	public String getNext_text() {
		return next_text;
	}
	public void setNext_text(String next_text) {
		this.next_text = next_text;
	}
	public String getSwm_memo() {
		return swm_memo;
	}
	public void setSwm_memo(String swm_memo) {
		this.swm_memo = swm_memo;
	}
	public String getStock_area() {
		return stock_area;
	}
	public void setStock_area(String stock_area) {
		this.stock_area = stock_area;
	}
	public String getPtn_name() {
		return ptn_name;
	}
	public void setPtn_name(String ptn_name) {
		this.ptn_name = ptn_name;
	}
	public String getDef_loc_id() {
		return def_loc_id;
	}
	public void setDef_loc_id(String def_loc_id) {
		this.def_loc_id = def_loc_id;
	}
	public String getSws_memo() {
		return sws_memo;
	}
	public void setSws_memo(String sws_memo) {
		this.sws_memo = sws_memo;
	}
	public String getM_quality() {
		return m_quality;
	}
	public void setM_quality(String m_quality) {
		this.m_quality = m_quality;
	}
	public String getM_model() {
		return m_model;
	}
	public void setM_model(String m_model) {
		this.m_model = m_model;
	}
	public String getLot_id() {
		return lot_id;
	}
	public void setLot_id(String lot_id) {
		this.lot_id = lot_id;
	}
	
	private String wo_doc_guid;
	private String wo_doc_id;
	private String itm_id;
	private String itm_name;
	private String itm_unit;
	private BigDecimal itm_qty;
	private BigDecimal itm_finish_qty;
	private BigDecimal itm_scrap_qty;
	private int wo_status;
	private BigDecimal itm_bind_qty;
	private String next_text ="";
	private String swm_memo ="";
	private String stock_area ="";
	private String ptn_name="";
	private String def_loc_id="";
	private String sws_memo ="";
	private String m_quality ="";
	private String m_model ="";
	private String lot_id = "";
}
