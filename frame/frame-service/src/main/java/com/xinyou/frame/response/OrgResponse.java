package com.xinyou.frame.response;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.xinyou.frame.domain.entities.ORG_MAIN;
import com.xinyou.frame.domain.models.ORG_NODE;

@XmlRootElement(name="Response")
public class OrgResponse {
	private String status;
	private String info;
	private ORG_MAIN orgData;
	private List<ORG_MAIN> orgListData;
	private List<ORG_NODE> orgNodeListData;
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
	public ORG_MAIN getOrgData() {
		return orgData;
	}
	public void setOrgData(ORG_MAIN orgData) {
		this.orgData = orgData;
	}
	public List<ORG_MAIN> getOrgListData() {
		return orgListData;
	}
	public void setOrgListData(List<ORG_MAIN> orgListData) {
		this.orgListData = orgListData;
	}
	public List<ORG_NODE> getOrgNodeListData() {
		return orgNodeListData;
	}
	public void setOrgNodeListData(List<ORG_NODE> orgNodeListData) {
		this.orgNodeListData = orgNodeListData;
	}
}
