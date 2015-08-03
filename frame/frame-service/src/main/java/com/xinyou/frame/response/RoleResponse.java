package com.xinyou.frame.response;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.xinyou.frame.domain.entities.BIZ_SYS;
import com.xinyou.frame.domain.entities.ROLE_MAIN;
import com.xinyou.frame.domain.models.SYS_DM;
import com.xinyou.frame.domain.models.FUN_NODE;
import com.xinyou.frame.domain.models.ROLE_DM;


@XmlRootElement(name = "Response")
public class RoleResponse {
	private String status;
	private String info;
	private ROLE_MAIN roleData;
	private List<ROLE_MAIN> roleListData;
	private ROLE_DM roleDMData;
	private List<BIZ_SYS> bizSysListData;
	private SYS_DM bizSysDMData;
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
	public ROLE_MAIN getRoleData() {
		return roleData;
	}
	public void setRoleData(ROLE_MAIN roleData) {
		this.roleData = roleData;
	}
	public List<ROLE_MAIN> getRoleListData() {
		return roleListData;
	}
	public void setRoleListData(List<ROLE_MAIN> roleListData) {
		this.roleListData = roleListData;
	}
	public ROLE_DM getRoleDMData() {
		return roleDMData;
	}
	public void setRoleDMData(ROLE_DM roleDMData) {
		this.roleDMData = roleDMData;
	}
	public List<BIZ_SYS> getBizSysListData() {
		return bizSysListData;
	}
	public void setBizSysListData(List<BIZ_SYS> bizSysListData) {
		this.bizSysListData = bizSysListData;
	}
	public SYS_DM getBizSysDMData() {
		return bizSysDMData;
	}
	public void setBizSysDMData(SYS_DM bizSysDMData) {
		this.bizSysDMData = bizSysDMData;
	}
	public List<FUN_NODE> getFunNodeListData() {
		return funNodeListData;
	}
	public void setFunNodeListData(List<FUN_NODE> funNodeListData) {
		this.funNodeListData = funNodeListData;
	}
}
