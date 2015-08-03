package com.xinyou.frame.response;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.xinyou.frame.domain.entities.DOC_MAIN;
import com.xinyou.frame.domain.entities.PARAM_MAIN;
import com.xinyou.frame.domain.entities.PRIVILEGE_MAIN;
import com.xinyou.frame.domain.models.DOC_DM;
import com.xinyou.frame.domain.models.PRIVILEGE_DM;

@XmlRootElement(name = "Response")
public class DocResponse {
	private String status;
	private String info;
	private DOC_MAIN docData;
	private PRIVILEGE_MAIN docPrivilegeData;
	private List<DOC_MAIN> docListData;
	private DOC_DM docDMData;
	private PRIVILEGE_MAIN privilegeData;
	private PRIVILEGE_DM privilegeDMData;
	private List<PARAM_MAIN> paramListData;
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
	public DOC_MAIN getDocData() {
		return docData;
	}
	public void setDocData(DOC_MAIN docData) {
		this.docData = docData;
	}
	public PRIVILEGE_MAIN getDocPrivilegeData() {
		return docPrivilegeData;
	}
	public void setDocPrivilegeData(PRIVILEGE_MAIN docPrivilegeData) {
		this.docPrivilegeData = docPrivilegeData;
	}
	public List<DOC_MAIN> getDocListData() {
		return docListData;
	}
	public void setDocListData(List<DOC_MAIN> docListData) {
		this.docListData = docListData;
	}
	public DOC_DM getDocDMData() {
		return docDMData;
	}
	public void setDocDMData(DOC_DM docDMData) {
		this.docDMData = docDMData;
	}
	public PRIVILEGE_MAIN getPrivilegeData() {
		return privilegeData;
	}
	public void setPrivilegeData(PRIVILEGE_MAIN privilegeData) {
		this.privilegeData = privilegeData;
	}
	public PRIVILEGE_DM getPrivilegeDMData() {
		return privilegeDMData;
	}
	public void setPrivilegeDMData(PRIVILEGE_DM privilegeDMData) {
		this.privilegeDMData = privilegeDMData;
	}
	public List<PARAM_MAIN> getParamListData() {
		return paramListData;
	}
	public void setParamListData(List<PARAM_MAIN> paramListData) {
		this.paramListData = paramListData;
	}
}
