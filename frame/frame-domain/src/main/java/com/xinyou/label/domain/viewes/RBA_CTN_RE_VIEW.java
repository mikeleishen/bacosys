package com.xinyou.label.domain.viewes;

import java.math.BigDecimal;

public class RBA_CTN_RE_VIEW{
	private String rba_ctn_re_guid;
	private String rba_ctn_re_id;
	private String rba_doc_id;
	private String rba_itm_seqno;
	private String ctn_baco;
	private String lot_id;
	
	private String itm_id;
	private String wo_doc_id;
	private long created_dt ;
	private String itm_name;
	private BigDecimal itm_qty ;
	private String m_qc_doc;
	 
	 public String getItm_id() {
		return itm_id;
	}
	public void setItm_id(String itm_id) {
		this.itm_id = itm_id;
	}
	 public String getWo_doc_id() {
		return wo_doc_id;
	}
	public void setWo_doc_id(String wo_doc_id) {
		this.wo_doc_id = wo_doc_id;
	}
	public long getCreated_dt() {
		return created_dt;
	}
	public void setCreated_dt(long created_dt) {
		this.created_dt = created_dt;
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
	public String getM_qc_doc() {
		return m_qc_doc;
	}
	public void setM_qc_doc(String m_qc_doc) {
		this.m_qc_doc = m_qc_doc;
	}
	public String getSp_name() {
		return sp_name;
	}
	public void setSp_name(String sp_name) {
		this.sp_name = sp_name;
	}
	private String sp_name ;
	
	public String getRba_ctn_re_guid() {
		return rba_ctn_re_guid;
	}
	public void setRba_ctn_re_guid(String rba_ctn_re_guid) {
		this.rba_ctn_re_guid = rba_ctn_re_guid;
	}
	public String getRba_ctn_re_id() {
		return rba_ctn_re_id;
	}
	public void setRba_ctn_re_id(String rba_ctn_re_id) {
		this.rba_ctn_re_id = rba_ctn_re_id;
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
	public String getCtn_baco() {
		return ctn_baco;
	}
	public void setCtn_baco(String ctn_baco) {
		this.ctn_baco = ctn_baco;
	}
	public String getLot_id() {
		return lot_id;
	}
	public void setLot_id(String lot_id) {
		this.lot_id = lot_id;
	}
	
}

