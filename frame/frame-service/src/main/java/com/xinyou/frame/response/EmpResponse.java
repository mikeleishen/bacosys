package com.xinyou.frame.response;

import java.util.List;

import com.xinyou.frame.domain.entities.CITY_MAIN;
import com.xinyou.frame.domain.entities.COUNTRY_MAIN;
import com.xinyou.frame.domain.entities.EMP_MAIN;
import com.xinyou.frame.domain.entities.NATION_MAIN;
import com.xinyou.frame.domain.entities.STATE_MAIN;
import com.xinyou.frame.domain.models.EMP_DM;

public class EmpResponse {
	private String status;
	private String info;
	private EMP_MAIN empData;
	private List<NATION_MAIN> nationListData;
	private List<STATE_MAIN> stateListData;
	private List<CITY_MAIN> cityListData;
	private List<COUNTRY_MAIN> countryListData;
	private List<EMP_MAIN> empListData;
	private EMP_DM empDMData;
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
	public EMP_MAIN getEmpData() {
		return empData;
	}
	public void setEmpData(EMP_MAIN empData) {
		this.empData = empData;
	}
	public List<NATION_MAIN> getNationListData() {
		return nationListData;
	}
	public void setNationListData(List<NATION_MAIN> nationListData) {
		this.nationListData = nationListData;
	}
	public List<STATE_MAIN> getStateListData() {
		return stateListData;
	}
	public void setStateListData(List<STATE_MAIN> stateListData) {
		this.stateListData = stateListData;
	}
	public List<CITY_MAIN> getCityListData() {
		return cityListData;
	}
	public void setCityListData(List<CITY_MAIN> cityListData) {
		this.cityListData = cityListData;
	}
	public List<COUNTRY_MAIN> getCountryListData() {
		return countryListData;
	}
	public void setCountryListData(List<COUNTRY_MAIN> countryListData) {
		this.countryListData = countryListData;
	}
	public List<EMP_MAIN> getEmpListData() {
		return empListData;
	}
	public void setEmpListData(List<EMP_MAIN> empListData) {
		this.empListData = empListData;
	}
	public EMP_DM getEmpDMData() {
		return empDMData;
	}
	public void setEmpDMData(EMP_DM empDMData) {
		this.empDMData = empDMData;
	}
}
