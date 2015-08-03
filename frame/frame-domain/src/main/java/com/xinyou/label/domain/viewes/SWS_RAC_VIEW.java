package com.xinyou.label.domain.viewes;

import java.math.BigDecimal;

public class SWS_RAC_VIEW {
	private String rac_seqno;
	private String rac_id;
	private String rac_name;
	private BigDecimal rac_pkg_qty;
	private BigDecimal finish_qty;
	private BigDecimal scrap_qty;
	private BigDecimal tar_qty;
	private int status;
	private String main_worker;
	private long rp_date;
	private long bg_date;
	private String sws_rp_guid;
	private String rac_tech_id;
	private int rac_emp_num;
	private BigDecimal rp_qty;
	private String rp_ws;
	private String rp_ws_no;
	private int tar_emp_num;
	private String rac_desc;
	
	public String getRac_desc() {
		return rac_desc;
	}
	public void setRac_desc(String rac_desc) {
		this.rac_desc = rac_desc;
	}
	public int getTar_emp_num() {
		return tar_emp_num;
	}
	public void setTar_emp_num(int tar_emp_num) {
		this.tar_emp_num = tar_emp_num;
	}
	public String getRp_ws() {
		return rp_ws;
	}
	public void setRp_ws(String rp_ws) {
		this.rp_ws = rp_ws;
	}
	public String getRp_ws_no() {
		return rp_ws_no;
	}
	public void setRp_ws_no(String rp_ws_no) {
		this.rp_ws_no = rp_ws_no;
	}
	public BigDecimal getRp_qty() {
		return rp_qty;
	}
	public void setRp_qty(BigDecimal rp_qty) {
		this.rp_qty = rp_qty;
	}
	public int getRac_emp_num() {
		return rac_emp_num;
	}
	public void setRac_emp_num(int rac_emp_num) {
		this.rac_emp_num = rac_emp_num;
	}
	public BigDecimal getTar_qty() {
		return tar_qty;
	}
	public void setTar_qty(BigDecimal tar_qty) {
		this.tar_qty = tar_qty;
	}
	public String getRac_tech_id() {
		return rac_tech_id;
	}
	public void setRac_tech_id(String rac_tech_id) {
		this.rac_tech_id = rac_tech_id;
	}
	public String getSws_rp_guid() {
		return sws_rp_guid;
	}
	public void setSws_rp_guid(String sws_rp_guid) {
		this.sws_rp_guid = sws_rp_guid;
	}
	public long getBg_date() {
		return bg_date;
	}
	public void setBg_date(long bg_date) {
		this.bg_date = bg_date;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMain_worker() {
		return main_worker;
	}
	public void setMain_worker(String main_worker) {
		this.main_worker = main_worker;
	}
	public long getRp_date() {
		return rp_date;
	}
	public void setRp_date(long rp_date) {
		this.rp_date = rp_date;
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
	public BigDecimal getRac_pkg_qty() {
		return rac_pkg_qty;
	}
	public void setRac_pkg_qty(BigDecimal rac_pkg_qty) {
		this.rac_pkg_qty = rac_pkg_qty;
	}
}

