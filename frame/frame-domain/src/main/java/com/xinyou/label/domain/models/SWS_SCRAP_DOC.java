package com.xinyou.label.domain.models;

import java.math.BigDecimal;
import java.util.List;

import com.xinyou.label.domain.viewes.RAC_SCRAP_VIEW;
import com.xinyou.label.domain.viewes.RAC_VIEW;

/** 报废单
 * */
public class SWS_SCRAP_DOC {
	private String sws_guid;
	private String rp_guid;
	private String sws_id;
	private String itm_id;
	private BigDecimal sws_qty;
	private long bg_dt;
	private String rac_id;
	private String rac_name;
	private String ws_id;
	private String ws_no;
	private BigDecimal tar_qty;
	private BigDecimal rp_qty;
	private BigDecimal scraped_qty;
	private String wo_id;

	private List<RAC_SCRAP_VIEW> scrap_list;
	private List<RAC_VIEW> rac_list;
	private int scrap_on_rac;

	public String getWo_id() {
		return wo_id;
	}
	public void setWo_id(String wo_id) {
		this.wo_id = wo_id;
	}
	public int getScrap_on_rac() {
		return scrap_on_rac;
	}
	public void setScrap_on_rac(int scrap_on_rac) {
		this.scrap_on_rac = scrap_on_rac;
	}
	public List<RAC_VIEW> getRac_list() {
		return rac_list;
	}
	public void setRac_list(List<RAC_VIEW> rac_list) {
		this.rac_list = rac_list;
	}
	public String getRp_guid() {
		return rp_guid;
	}
	public void setRp_guid(String rp_guid) {
		this.rp_guid = rp_guid;
	}
	public BigDecimal getSws_qty() {
		return sws_qty;
	}
	public void setSws_qty(BigDecimal sws_qty) {
		this.sws_qty = sws_qty;
	}
	public long getBg_dt() {
		return bg_dt;
	}
	public void setBg_dt(long bg_dt) {
		this.bg_dt = bg_dt;
	}
	public String getWs_id() {
		return ws_id;
	}
	public void setWs_id(String ws_id) {
		this.ws_id = ws_id;
	}
	public String getWs_no() {
		return ws_no;
	}
	public void setWs_no(String ws_no) {
		this.ws_no = ws_no;
	}
	public BigDecimal getTar_qty() {
		return tar_qty;
	}
	public void setTar_qty(BigDecimal tar_qty) {
		this.tar_qty = tar_qty;
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
	public String getItm_id() {
		return itm_id;
	}
	public void setItm_id(String itm_id) {
		this.itm_id = itm_id;
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
	public BigDecimal getRp_qty() {
		return rp_qty;
	}
	public void setRp_qty(BigDecimal rp_qty) {
		this.rp_qty = rp_qty;
	}
	public BigDecimal getScraped_qty() {
		return scraped_qty;
	}
	public void setScraped_qty(BigDecimal scraped_qty) {
		this.scraped_qty = scraped_qty;
	}
	public List<RAC_SCRAP_VIEW> getScrap_list() {
		return scrap_list;
	}
	public void setScrap_list(List<RAC_SCRAP_VIEW> scrap_list) {
		this.scrap_list = scrap_list;
	}
}
