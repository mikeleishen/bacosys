package com.xinyou.frame.response;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.xinyou.frame.domain.entities.USR_MAIN;
import com.xinyou.frame.domain.models.EntityListDM;
import com.xinyou.frame.domain.models.FUN_NODE;
import com.xinyou.frame.domain.models.USR_DM;
import com.xinyou.frame.domain.models.USR_LOGIN_DM;

@XmlRootElement(name="Response")
public class UsrResponse {
	
	private String token;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
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
	public List<FUN_NODE> getFunNodeListData() {
		return funNodeListData;
	}
	public void setFunNodeListData(List<FUN_NODE> funNodeListData) {
		this.funNodeListData = funNodeListData;
	}
	public USR_MAIN getUsrData() {
		return usrData;
	}
	public void setUsrData(USR_MAIN usrData) {
		this.usrData = usrData;
	}
	public USR_DM getUsrDMData() {
		return usrDMData;
	}
	public void setUsrDMData(USR_DM usrDMData) {
		this.usrDMData = usrDMData;
	}
	public USR_LOGIN_DM getUsrLoginDMData() {
		return usrLoginDMData;
	}
	public void setUsrLoginDMData(USR_LOGIN_DM usrLoginDMData) {
		this.usrLoginDMData = usrLoginDMData;
	}
	public Object getDataEntity() {
		return dataEntity;
	}
	public void setDataEntity(Object dataEntity) {
		this.dataEntity = dataEntity;
	}
	public EntityListDM getDataDM() {
		return dataDM;
	}
	public void setDataDM(EntityListDM dataDM) {
		this.dataDM = dataDM;
	}
	private String status;
	private String info;
	private List<FUN_NODE> funNodeListData;
	private USR_MAIN usrData;
	private USR_DM   usrDMData;
	private USR_LOGIN_DM usrLoginDMData;
	private Object dataEntity;
	private EntityListDM dataDM;
	
	
	
	
	
	

}
