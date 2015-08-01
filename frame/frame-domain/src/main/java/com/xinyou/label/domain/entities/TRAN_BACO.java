package com.xinyou.label.domain.entities;

import java.math.BigDecimal;

public class TRAN_BACO extends EntityBase {
	
	public String getCtn_baco() {
		return ctn_baco;
	}
	public void setCtn_baco(String ctn_baco) {
		this.ctn_baco = ctn_baco;
	}
	public String getTran_guid() {
		return tran_guid;
	}
	public void setTran_guid(String tran_guid) {
		this.tran_guid = tran_guid;
	}
	public BigDecimal getTran_qty() {
		return tran_qty;
	}
	public void setTran_qty(BigDecimal tran_qty) {
		this.tran_qty = tran_qty;
	}
	public String getItm_id() {
		return itm_id;
	}
	public void setItm_id(String itm_id) {
		this.itm_id = itm_id;
	}
	public String getParent_baco() {
		return parent_baco;
	}
	public void setParent_baco(String parent_baco) {
		this.parent_baco = parent_baco;
	}
	public String getLot_id() {
		return lot_id;
	}
	public void setLot_id(String lot_id) {
		this.lot_id = lot_id;
	}
	public String getWh_id() {
		return wh_id;
	}
	public void setWh_id(String wh_id) {
		this.wh_id = wh_id;
	}
	public String getF_wh_id() {
		return f_wh_id;
	}
	public void setF_wh_id(String f_wh_id) {
		this.f_wh_id = f_wh_id;
	}
	public int getBase_type() {
		return base_type;
	}
	public void setBase_type(int base_type) {
		this.base_type = base_type;
	}
	public String getBase_id() {
		return base_id;
	}
	public void setBase_id(String base_id) {
		this.base_id = base_id;
	}
	public String getBase_seqno() {
		return base_seqno;
	}
	public void setBase_seqno(String base_seqno) {
		this.base_seqno = base_seqno;
	}
	
	private String ctn_baco;
	private String tran_guid;
	private BigDecimal tran_qty;
	private String itm_id;
	private String parent_baco;
	private String lot_id;	
	private String wh_id;
	
	private String f_wh_id;
	private int base_type;
	private String base_id;
	private String base_seqno;
}
