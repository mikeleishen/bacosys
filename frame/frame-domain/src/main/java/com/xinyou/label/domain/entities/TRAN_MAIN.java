package com.xinyou.label.domain.entities;

public class TRAN_MAIN extends EntityBase {
	
	public int getTran_type() {
		return tran_type;
	}
	public void setTran_type(int tran_type) {
		this.tran_type = tran_type;
	}
	public int getBase_doc_type() {
		return base_doc_type;
	}
	public void setBase_doc_type(int base_doc_type) {
		this.base_doc_type = base_doc_type;
	}
	public String getBase_doc_id() {
		return base_doc_id;
	}
	public void setBase_doc_id(String base_doc_id) {
		this.base_doc_id = base_doc_id;
	}
	public int getNeed_syn() {
		return need_syn;
	}
	public void setNeed_syn(int need_syn) {
		this.need_syn = need_syn;
	}
	public int getIs_syned() {
		return is_syned;
	}
	public void setIs_syned(int is_syned) {
		this.is_syned = is_syned;
	}
	public String getTran_reason_id() {
		return tran_reason_id;
	}
	public void setTran_reason_id(String tran_reason_id) {
		this.tran_reason_id = tran_reason_id;
	}
	public int getSyn_doc_type() {
		return syn_doc_type;
	}
	public void setSyn_doc_type(int syn_doc_type) {
		this.syn_doc_type = syn_doc_type;
	}
	public String getSyn_doc_id() {
		return syn_doc_id;
	}
	public void setSyn_doc_id(String syn_doc_id) {
		this.syn_doc_id = syn_doc_id;
	}
	public String getWh_id() {
		return wh_id;
	}
	public void setWh_id(String wh_id) {
		this.wh_id = wh_id;
	}
	public int getIn_out() {
		return in_out;
	}
	public void setIn_out(int in_out) {
		this.in_out = in_out;
	}

	private int tran_type;
	private String tran_reason_id;
	private int base_doc_type;
	private String base_doc_id;
	private int need_syn;
	private int is_syned;
	private int syn_doc_type;
	private String syn_doc_id;
	private String wh_id;
	private int in_out;	
}
