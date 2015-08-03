package com.xinyou.label.domain.models;

import java.math.BigDecimal;
import java.util.List;

public class ERP_RDA_DOC {
	public String getWo_id() {
		return wo_id;
	}

	public void setWo_id(String wo_id) {
		this.wo_id = wo_id;
	}

	public BigDecimal getFinish_qty() {
		return finish_qty;
	}

	public void setFinish_qty(BigDecimal finish_qty) {
		this.finish_qty = finish_qty;
	}

	public BigDecimal getScrap_qty() {
		return scrap_qty;
	}

	public void setScrap_qty(BigDecimal scrap_qty) {
		this.scrap_qty = scrap_qty;
	}

	public long getBg_dt() {
		return bg_dt;
	}

	public void setBg_dt(long bg_dt) {
		this.bg_dt = bg_dt;
	}

	public long getRp_dt() {
		return rp_dt;
	}

	public void setRp_dt(long rp_dt) {
		this.rp_dt = rp_dt;
	}

	public String getRac_id() {
		return rac_id;
	}

	public void setRac_id(String rac_id) {
		this.rac_id = rac_id;
	}

	public String getWs_id() {
		return ws_id;
	}

	public void setWs_id(String ws_id) {
		this.ws_id = ws_id;
	}

	public List<String> getEmpIds() {
		return empIds;
	}

	public void setEmpIds(List<String> empIds) {
		this.empIds = empIds;
	}
	
	public String getNext_rac_id() {
		return next_rac_id;
	}

	public void setNext_rac_id(String next_rac_id) {
		this.next_rac_id = next_rac_id;
	}

	public String getWh_id() {
		return wh_id;
	}

	public void setWh_id(String wh_id) {
		this.wh_id = wh_id;
	}
	public String getItm_id() {
		return itm_id;
	}

	public void setItm_id(String itm_id) {
		this.itm_id = itm_id;
	}
	public String getRac_seqno() {
		return rac_seqno;
	}

	public void setRac_seqno(String rac_seqno) {
		this.rac_seqno = rac_seqno;
	}

	public String getNext_rac_seqno() {
		return next_rac_seqno;
	}

	public void setNext_rac_seqno(String next_rac_seqno) {
		this.next_rac_seqno = next_rac_seqno;
	}
	public String getLot_id() {
		return lot_id;
	}

	public void setLot_id(String lot_id) {
		this.lot_id = lot_id;
	}
	public String getSws_id() {
		return sws_id;
	}

	public void setSws_id(String sws_id) {
		this.sws_id = sws_id;
	}
	public String getRp_wo_no() {
		return rp_wo_no;
	}

	public void setRp_wo_no(String rp_wo_no) {
		this.rp_wo_no = rp_wo_no;
	}

	private String wo_id;
	private String itm_id;
	private String lot_id;
	private BigDecimal finish_qty;
	private BigDecimal scrap_qty;
	private long bg_dt;
	private long rp_dt;
	private String rac_seqno;
	private String rac_id;
	private String next_rac_seqno;
	private String next_rac_id;
	private String wh_id;
	private String ws_id;
	private List<String> empIds;
	private String sws_id;
	private String rp_wo_no;
}

