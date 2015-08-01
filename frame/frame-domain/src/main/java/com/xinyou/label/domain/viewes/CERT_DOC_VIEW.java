package com.xinyou.label.domain.viewes;

import java.math.BigDecimal;

import com.xinyou.label.domain.entities.EntityBase;

public class CERT_DOC_VIEW extends EntityBase{
	private String itm_id;
	private String itm_name;
	private BigDecimal itm_qty;
	private String cert_year;
	private int cert_status;
	
	private BigDecimal pack_qty;
	private BigDecimal total_qty;
	
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
	public String getCert_year() {
		return cert_year;
	}
	public void setCert_year(String cert_year) {
		this.cert_year = cert_year;
	}
	public int getCert_status() {
		return cert_status;
	}
	public void setCert_status(int cert_status) {
		this.cert_status = cert_status;
	}
	public BigDecimal getPack_qty() {
		return pack_qty;
	}
	public void setPack_qty(BigDecimal pack_qty) {
		this.pack_qty = pack_qty;
	}
	public BigDecimal getTotal_qty() {
		return total_qty;
	}
	public void setTotal_qty(BigDecimal total_qty) {
		this.total_qty = total_qty;
	}
	
	
}
