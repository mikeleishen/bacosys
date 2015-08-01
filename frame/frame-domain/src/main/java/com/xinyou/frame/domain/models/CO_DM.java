package com.xinyou.frame.domain.models;


import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import com.xinyou.frame.domain.entities.CO_MAIN;

@XmlRootElement(name = "CO_DM")
public class CO_DM {
	private int count;
	private List<CO_MAIN> coListData;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<CO_MAIN> getCoListData() {
		return coListData;
	}
	public void setCoListData(List<CO_MAIN> coListData) {
		this.coListData = coListData;
	}
}

