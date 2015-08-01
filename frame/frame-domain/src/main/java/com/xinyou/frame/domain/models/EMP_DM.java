package com.xinyou.frame.domain.models;

import java.util.List;

import com.xinyou.frame.domain.entities.ADDR_MAIN;
import com.xinyou.frame.domain.entities.EMP_MAIN;

public class EMP_DM {
	private EMP_MAIN empData;
	private List<ADDR_MAIN> addrListData;

	public EMP_MAIN getEmpData() {
		return empData;
	}

	public void setEmpData(EMP_MAIN empData) {
		this.empData = empData;
	}

	public List<ADDR_MAIN> getAddrListData() {
		return addrListData;
	}

	public void setAddrListData(List<ADDR_MAIN> addrListData) {
		this.addrListData = addrListData;
	}
}

