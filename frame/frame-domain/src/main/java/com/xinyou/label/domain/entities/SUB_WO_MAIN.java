package com.xinyou.label.domain.entities;

import java.math.BigDecimal;


/**
 *  大流程票（大转移单）
 * */
public class SUB_WO_MAIN extends EntityBase {
	private BigDecimal sub_qty;
	private String wo_id;
	private String lot_id;
	private String cut_seqno;
	private String cut_id;
	private String cut_name;
	private String m_model;
	private String m_quality;
	private String m_qc_doc;
	private int sws_qty;
	private BigDecimal cut_qty;
	private String itm_id;
	private String next_text;
	private String swm_memo;
	private String sws_memo;
	private String stock_area;
	private String ptn_name;
	private String work_demand;
	private String sp_info;
	private String pre_sws_id;
	
	public String getPre_sws_id() {
		return pre_sws_id;
	}
	public void setPre_sws_id(String pre_sws_id) {
		this.pre_sws_id = pre_sws_id;
	}
	public String getSp_info() {
		return sp_info;
	}
	public void setSp_info(String sp_info) {
		this.sp_info = sp_info;
	}
	public String getWork_demand() {
		return work_demand;
	}
	public void setWork_demand(String work_demand) {
		this.work_demand = work_demand;
	}
	public String getSws_memo() {
		return sws_memo;
	}
	public void setSws_memo(String sws_memo) {
		this.sws_memo = sws_memo;
	}
	public String getPtn_name() {
		return ptn_name;
	}
	public void setPtn_name(String ptn_name) {
		this.ptn_name = ptn_name;
	}
	public String getNext_text() {
		return next_text;
	}
	public void setNext_text(String next_text) {
		this.next_text = next_text;
	}
	public String getSwm_memo() {
		return swm_memo;
	}
	public void setSwm_memo(String swm_memo) {
		this.swm_memo = swm_memo;
	}
	public String getStock_area() {
		return stock_area;
	}
	public void setStock_area(String stock_area) {
		this.stock_area = stock_area;
	}
	public String getItm_id() {
		return itm_id;
	}
	public void setItm_id(String itm_id) {
		this.itm_id = itm_id;
	}
	public String getCut_seqno() {
		return cut_seqno;
	}
	public void setCut_seqno(String cut_seqno) {
		this.cut_seqno = cut_seqno;
	}
	public BigDecimal getSub_qty() {
		return sub_qty;
	}
	public void setSub_qty(BigDecimal sub_qty) {
		this.sub_qty = sub_qty;
	}
	public int getSws_qty() {
		return sws_qty;
	}
	public void setSws_qty(int sws_qty) {
		this.sws_qty = sws_qty;
	}
	public BigDecimal getCut_qty() {
		return cut_qty;
	}
	public void setCut_qty(BigDecimal cut_qty) {
		this.cut_qty = cut_qty;
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
	public String getCut_id() {
		return cut_id;
	}
	public void setCut_id(String cut_id) {
		this.cut_id = cut_id;
	}
	public String getCut_name() {
		return cut_name;
	}
	public void setCut_name(String cut_name) {
		this.cut_name = cut_name;
	}
	public String getM_model() {
		return m_model;
	}
	public void setM_model(String m_model) {
		this.m_model = m_model;
	}
	public String getM_quality() {
		return m_quality;
	}
	public void setM_quality(String m_quality) {
		this.m_quality = m_quality;
	}
	public String getM_qc_doc() {
		return m_qc_doc;
	}
	public void setM_qc_doc(String m_qc_doc) {
		this.m_qc_doc = m_qc_doc;
	}
}
