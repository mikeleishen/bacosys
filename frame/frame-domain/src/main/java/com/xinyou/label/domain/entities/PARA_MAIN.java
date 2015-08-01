package com.xinyou.label.domain.entities;

public class PARA_MAIN extends EntityBase{
	private String para_type_id;
	private String para_value;
	private String para_memo;
	
	public String getPara_memo() {
		return para_memo;
	}
	public void setPara_memo(String para_memo) {
		this.para_memo = para_memo;
	}
	public String getPara_type_id() {
		return para_type_id;
	}
	public void setPara_type_id(String para_type_id) {
		this.para_type_id = para_type_id;
	}
	public String getPara_value() {
		return para_value;
	}
	public void setPara_value(String para_value) {
		this.para_value = para_value;
	}
}
