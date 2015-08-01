package com.xinyou.label.domain.entities;

import java.math.BigDecimal;

public class TRAN_ITM extends EntityBase {
	public String getItm_id() {
		return itm_id;
	}
	public void setItm_id(String itm_id) {
		this.itm_id = itm_id;
	}

	public String getTran_guid() {
		return tran_guid;
	}
	public void setTran_guid(String tran_guid) {
		this.tran_guid = tran_guid;
	}
	public BigDecimal getItm_qty() {
		return itm_qty;
	}
	public void setItm_qty(BigDecimal itm_qty) {
		this.itm_qty = itm_qty;
	}

	private String itm_id;
	private BigDecimal itm_qty;
	private String tran_guid;
}
