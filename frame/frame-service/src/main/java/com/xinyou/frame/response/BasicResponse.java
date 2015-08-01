package com.xinyou.frame.response;

import java.util.List;

import com.xinyou.frame.domain.models.EntityListDM;

public class BasicResponse <T,T1> {
	
	private String status = "0";
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
	public long getSvrdt() {
		return svrdt;
	}
	public void setSvrdt(long svrdt) {
		this.svrdt = svrdt;
	}
	public T getDataEntity() {
		return dataEntity;
	}
	public void setDataEntity(T dataEntity) {
		this.dataEntity = dataEntity;
	}
	public List<T> getDataList() {
		return dataList;
	}
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	public List<T1> getDataList2() {
		return dataList2;
	}
	public void setDataList2(List<T1> dataList2) {
		this.dataList2 = dataList2;
	}
	public EntityListDM<T, T1> getDataDM() {
		return dataDM;
	}
	public void setDataDM(EntityListDM<T, T1> dataDM) {
		this.dataDM = dataDM;
	}
	private String info = "";
	private long   svrdt = 0;
	private T dataEntity;
	private List<T> dataList;
	private List<T1> dataList2;
	private EntityListDM<T,T1> dataDM;
	
	
	

}
