package com.xinyou.frame.domain.models;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.xinyou.frame.domain.entities.DOC_MAIN;

@XmlRootElement(name = "DOC_DM")
public class DOC_DM {
	private int count;
	private List<DOC_MAIN> docListData;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<DOC_MAIN> getDocListData() {
		return docListData;
	}
	public void setDocListData(List<DOC_MAIN> docListData) {
		this.docListData = docListData;
	}
}
