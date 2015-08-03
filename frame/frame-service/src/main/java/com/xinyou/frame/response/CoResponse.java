package com.xinyou.frame.response;

import java.util.List;

import com.xinyou.frame.domain.entities.CO_MAIN;
import com.xinyou.frame.domain.models.CO_DM;

public class CoResponse {

	private String status;
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
	public CO_MAIN getCoData() {
		return coData;
	}
	public void setCoData(CO_MAIN coData) {
		this.coData = coData;
	}
	public List<CO_MAIN> getCoListData() {
		return coListData;
	}
	public void setCoListData(List<CO_MAIN> coListData) {
		this.coListData = coListData;
	}
	public CO_DM getCoDMData() {
		return coDMData;
	}
	public void setCoDMData(CO_DM coDMData) {
		this.coDMData = coDMData;
	}
	private String info;
	private CO_MAIN coData;
	private List<CO_MAIN> coListData;
	private CO_DM coDMData;
	
	
}
