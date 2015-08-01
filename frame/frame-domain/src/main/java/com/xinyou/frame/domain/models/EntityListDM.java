package com.xinyou.frame.domain.models;

import java.util.List;

/** 适合使用在明细关系结构中*/
public class EntityListDM <T,T1> {
	private T dataEntity;
	private List<T1> dataList;
	private int count;
	
	public T getDataEntity() {
		return dataEntity;
	}
	public void setDataEntity(T dataEntity) {
		this.dataEntity = dataEntity;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<T1> getDataList() {
		return dataList;
	}
	public void setDataList(List<T1> dataList) {
		this.dataList = dataList;
	}
	
  
	
	

}
