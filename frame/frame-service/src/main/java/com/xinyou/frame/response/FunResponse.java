package com.xinyou.frame.response;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.xinyou.frame.domain.entities.FUN_MAIN;
import com.xinyou.frame.domain.models.FUN_NODE;


@XmlRootElement(name = "Response")
public class FunResponse {
	private String status;
	private String info;
	private FUN_MAIN funData;
	private List<FUN_MAIN> funListData;
	private List<FUN_NODE> funNodeListData;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public FUN_MAIN getFunData() {
		return funData;
	}
	public void setFunData(FUN_MAIN funData) {
		this.funData = funData;
	}
	public List<FUN_MAIN> getFunListData() {
		return funListData;
	}
	public void setFunListData(List<FUN_MAIN> funListData) {
		this.funListData = funListData;
	}
	public List<FUN_NODE> getFunNodeListData() {
		return funNodeListData;
	}
	public void setFunNodeListData(List<FUN_NODE> funNodeListData) {
		this.funNodeListData = funNodeListData;
	}
}
