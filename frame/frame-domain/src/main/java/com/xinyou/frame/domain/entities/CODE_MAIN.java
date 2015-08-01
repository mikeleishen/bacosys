package com.xinyou.frame.domain.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CODE_MAIN")
public class CODE_MAIN {
	private String code_main_id;
	private String code_value;
	
	public String getCode_main_id() {
		return code_main_id;
	}
	public void setCode_main_id(String code_main_id) {
		this.code_main_id = code_main_id;
	}
	public String getCode_value() {
		return code_value;
	}
	public void setCode_value(String code_value) {
		this.code_value = code_value;
	}
}
