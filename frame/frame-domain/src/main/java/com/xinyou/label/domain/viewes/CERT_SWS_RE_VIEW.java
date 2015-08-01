package com.xinyou.label.domain.viewes;

import java.math.BigDecimal;

import com.xinyou.label.domain.entities.EntityBase;

public class CERT_SWS_RE_VIEW  extends EntityBase{
	
	private String cert_doc_guid;
	private String cert_doc_id;
	private String sws_guid;
	private String sws_id;
	private BigDecimal sws_qty;
	
	private String itm_id;
	private String itm_name;
	
	
	
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
	public String getCert_doc_guid() {
		return cert_doc_guid;
	}
	public void setCert_doc_guid(String cert_doc_guid) {
		this.cert_doc_guid = cert_doc_guid;
	}
	public String getCert_doc_id() {
		return cert_doc_id;
	}
	public void setCert_doc_id(String cert_doc_id) {
		this.cert_doc_id = cert_doc_id;
	}
	public String getSws_guid() {
		return sws_guid;
	}
	public void setSws_guid(String sws_guid) {
		this.sws_guid = sws_guid;
	}
	public String getSws_id() {
		return sws_id;
	}
	public void setSws_id(String sws_id) {
		this.sws_id = sws_id;
	}
	public BigDecimal getSws_qty() {
		return sws_qty;
	}
	public void setSws_qty(BigDecimal sws_qty) {
		this.sws_qty = sws_qty;
	}
	
	

}

