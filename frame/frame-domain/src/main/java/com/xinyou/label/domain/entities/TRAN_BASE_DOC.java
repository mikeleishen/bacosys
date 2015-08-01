package com.xinyou.label.domain.entities;


public class TRAN_BASE_DOC extends EntityBase {
	private String tran_guid;
	private int base_doc_type;
	private String base_doc_id;
	public String getTran_guid() {
		return tran_guid;
	}
	public void setTran_guid(String tran_guid) {
		this.tran_guid = tran_guid;
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
}
