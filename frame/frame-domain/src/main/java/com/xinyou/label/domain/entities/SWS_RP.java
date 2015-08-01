package com.xinyou.label.domain.entities;

import java.math.BigDecimal;

/**
 *  报工单
 * */
public class SWS_RP extends EntityBase {
	private BigDecimal finish_qty;
	private BigDecimal scrap_qty;
	private long bg_dt;
	private long rp_dt;
	private String sws_guid;
	private String rp_rac_id;
	private String rp_rac_name;
	private int rp_status;
	private String rp_ws;
	private String rp_ws_no;
	private BigDecimal rp_tar_value;
	private String itm_id;
	private String sws_id;
	private String b_pda_id;
	private String e_pda_id;
	
	public String getB_pda_id() {
		return b_pda_id;
	}
	public void setB_pda_id(String b_pda_id) {
		this.b_pda_id = b_pda_id;
	}
	public String getE_pda_id() {
		return e_pda_id;
	}
	public void setE_pda_id(String e_pda_id) {
		this.e_pda_id = e_pda_id;
	}
	public BigDecimal getRp_tar_value() {
		return rp_tar_value;
	}
	public void setRp_tar_value(BigDecimal rp_tar_value) {
		this.rp_tar_value = rp_tar_value;
	}
	public String getSws_id() {
		return sws_id;
	}
	public void setSws_id(String sws_id) {
		this.sws_id = sws_id;
	}
	public String getRp_ws_no() {
		return rp_ws_no;
	}
	public void setRp_ws_no(String rp_ws_no) {
		this.rp_ws_no = rp_ws_no;
	}
	public String getItm_id() {
		return itm_id;
	}
	public void setItm_id(String itm_id) {
		this.itm_id = itm_id;
	}
	public String getRp_ws() {
		return rp_ws;
	}
	public void setRp_ws(String rp_ws) {
		this.rp_ws = rp_ws;
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
	public String getSws_guid() {
		return sws_guid;
	}
	public void setSws_guid(String sws_guid) {
		this.sws_guid = sws_guid;
	}
	public String getRp_rac_id() {
		return rp_rac_id;
	}
	public void setRp_rac_id(String rp_rac_id) {
		this.rp_rac_id = rp_rac_id;
	}
	public String getRp_rac_name() {
		return rp_rac_name;
	}
	public void setRp_rac_name(String rp_rac_name) {
		this.rp_rac_name = rp_rac_name;
	}
	public int getRp_status() {
		return rp_status;
	}
	public void setRp_status(int rp_status) {
		this.rp_status = rp_status;
	}
}
