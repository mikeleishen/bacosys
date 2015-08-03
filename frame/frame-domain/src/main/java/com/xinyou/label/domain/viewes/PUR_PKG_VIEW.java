package com.xinyou.label.domain.viewes;

import java.math.BigDecimal;

public class PUR_PKG_VIEW {
	private String pur_id;
	private String pur_seqno;
	private String itm_id;
	private BigDecimal pkgqty ;
	private String ctn_baco;
	private String lot_id;
	private String stock_area;
	
	public String getStock_area() {
		return stock_area;
	}
	public void setStock_area(String stock_area) {
		this.stock_area = stock_area;
	}
	public String getLot_id() {
		return lot_id;
	}
	public void setLot_id(String lot_id) {
		this.lot_id = lot_id;
	}
	public String getPur_id() {
		return pur_id;
	}
	public void setPur_id(String pur_id) {
		this.pur_id = pur_id;
	}
	public String getPur_seqno() {
		return pur_seqno;
	}
	public void setPur_seqno(String pur_seqno) {
		this.pur_seqno = pur_seqno;
	}
	public String getItm_id() {
		return itm_id;
	}
	public void setItm_id(String itm_id) {
		this.itm_id = itm_id;
	}
	public BigDecimal getPkgqty() {
		return pkgqty;
	}
	public void setPkgqty(BigDecimal pkgqty) {
		this.pkgqty = pkgqty;
	}
	public String getCtn_baco() {
		return ctn_baco;
	}
	public void setCtn_baco(String ctn_baco) {
		this.ctn_baco = ctn_baco;
	}
}

