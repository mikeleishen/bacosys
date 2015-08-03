package com.xinyou.label.domain.viewes;

import java.math.BigDecimal;

public class SCRAP_REPORT_VIEW {
	private long scrap_dt;
	private String scrap_type;
	private String wo_id;
	private String itm_id;
	private String lot_id;
	private String rac_seqno;
	private String rac_id;
	private String rac_name;
	private BigDecimal scrap_qty;
	private String scrap_part;
	private String scrap_reason_name;
	private String scrap_content_name;
	private String happen_rac_name;
	private String guid;
	private String sws_id;
	private String erp_doc_id;
	
	public String getSws_id() {
		return sws_id;
	}
	public void setSws_id(String sws_id) {
		this.sws_id = sws_id;
	}
	public String getErp_doc_id() {
		return erp_doc_id;
	}
	public void setErp_doc_id(String erp_doc_id) {
		this.erp_doc_id = erp_doc_id;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getScrap_reason_name() {
		return scrap_reason_name;
	}
	public void setScrap_reason_name(String scrap_reason_name) {
		this.scrap_reason_name = scrap_reason_name;
	}
	public String getScrap_content_name() {
		return scrap_content_name;
	}
	public void setScrap_content_name(String scrap_content_name) {
		this.scrap_content_name = scrap_content_name;
	}
	public String getHappen_rac_name() {
		return happen_rac_name;
	}
	public void setHappen_rac_name(String happen_rac_name) {
		this.happen_rac_name = happen_rac_name;
	}
	
	public long getScrap_dt() {
		return scrap_dt;
	}
	public void setScrap_dt(long scrap_dt) {
		this.scrap_dt = scrap_dt;
	}
	public String getScrap_type() {
		return scrap_type;
	}
	public void setScrap_type(String scrap_type) {
		this.scrap_type = scrap_type;
	}
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
	public String getLot_id() {
		return lot_id;
	}
	public void setLot_id(String lot_id) {
		this.lot_id = lot_id;
	}
	public String getRac_seqno() {
		return rac_seqno;
	}
	public void setRac_seqno(String rac_seqno) {
		this.rac_seqno = rac_seqno;
	}
	public String getRac_id() {
		return rac_id;
	}
	public void setRac_id(String rac_id) {
		this.rac_id = rac_id;
	}
	public String getRac_name() {
		return rac_name;
	}
	public void setRac_name(String rac_name) {
		this.rac_name = rac_name;
	}
	public BigDecimal getScrap_qty() {
		return scrap_qty;
	}
	public void setScrap_qty(BigDecimal scrap_qty) {
		this.scrap_qty = scrap_qty;
	}
	public String getScrap_part() {
		return scrap_part;
	}
	public void setScrap_part(String scrap_part) {
		this.scrap_part = scrap_part;
	}
}

