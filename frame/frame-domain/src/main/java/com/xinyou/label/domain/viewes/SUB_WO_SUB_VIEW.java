package com.xinyou.label.domain.viewes;

import java.math.BigDecimal;

public class SUB_WO_SUB_VIEW {
	private String guid;
	private String id;
	private String parent_guid;
	private String ctn_guid;
	private int sub_seqno;
	private BigDecimal finish_qty;
	private BigDecimal scrap_qty;
	private BigDecimal sws_qty;
	private String itm_id;
	private int sws_status;
	private int rp_status;
	private String lot_id;
	private String wo_id;
	private String stock_area;
	private String rp_ws;
	private String rp_ws_no;
	private String paper_color;
	private String cut_seqno;
	private BigDecimal cutWt;
	private String sp_id;
	private String m_qc_doc;
	private String pre_sws_id;
	
	public String getPre_sws_id() {
		return pre_sws_id;
	}
	public void setPre_sws_id(String pre_sws_id) {
		this.pre_sws_id = pre_sws_id;
	}
	public String getM_qc_doc() {
		return m_qc_doc;
	}
	public void setM_qc_doc(String m_qc_doc) {
		this.m_qc_doc = m_qc_doc;
	}
	public String getSp_id() {
		return sp_id;
	}
	public void setSp_id(String sp_id) {
		this.sp_id = sp_id;
	}
	public BigDecimal getCutWt() {
		return cutWt;
	}
	public void setCutWt(BigDecimal cutWt) {
		this.cutWt = cutWt;
	}
	public String getCut_seqno() {
		return cut_seqno;
	}
	public void setCut_seqno(String cut_seqno) {
		this.cut_seqno = cut_seqno;
	}
	public String getRp_ws_no() {
		return rp_ws_no;
	}
	public void setRp_ws_no(String rp_ws_no) {
		this.rp_ws_no = rp_ws_no;
	}
	public String getPaper_color() {
		return paper_color;
	}
	public void setPaper_color(String paper_color) {
		this.paper_color = paper_color;
	}
	public String getRp_ws() {
		return rp_ws;
	}
	public void setRp_ws(String rp_ws) {
		this.rp_ws = rp_ws;
	}
	public String getStock_area() {
		return stock_area;
	}
	public void setStock_area(String stock_area) {
		this.stock_area = stock_area;
	}
	public String getWo_id() {
		return wo_id;
	}
	public void setWo_id(String wo_id) {
		this.wo_id = wo_id;
	}
	public String getLot_id() {
		return lot_id;
	}
	public void setLot_id(String lot_id) {
		this.lot_id = lot_id;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParent_guid() {
		return parent_guid;
	}
	public void setParent_guid(String parent_guid) {
		this.parent_guid = parent_guid;
	}
	public String getCtn_guid() {
		return ctn_guid;
	}
	public void setCtn_guid(String ctn_guid) {
		this.ctn_guid = ctn_guid;
	}
	public int getSub_seqno() {
		return sub_seqno;
	}
	public void setSub_seqno(int sub_seqno) {
		this.sub_seqno = sub_seqno;
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
	public BigDecimal getSws_qty() {
		return sws_qty;
	}
	public void setSws_qty(BigDecimal sws_qty) {
		this.sws_qty = sws_qty;
	}
	public String getItm_id() {
		return itm_id;
	}
	public void setItm_id(String itm_id) {
		this.itm_id = itm_id;
	}
	public int getSws_status() {
		return sws_status;
	}
	public void setSws_status(int sws_status) {
		this.sws_status = sws_status;
	}
	public int getRp_status() {
		return rp_status;
	}
	public void setRp_status(int rp_status) {
		this.rp_status = rp_status;
	}
	
}
