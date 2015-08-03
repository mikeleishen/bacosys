package com.xinyou.label.domain.models;

import java.util.List;

import com.xinyou.label.domain.viewes.SUB_WO_SUB_VIEW;
import com.xinyou.label.domain.viewes.SWS_RAC_VIEW;

public class SWS_PRINT_DOC {
	private SUB_WO_SUB_VIEW swsdoc;
	private List<SWS_RAC_VIEW> raclist;
	
	public SUB_WO_SUB_VIEW getSwsdoc() {
		return swsdoc;
	}
	public void setSwsdoc(SUB_WO_SUB_VIEW swsdoc) {
		this.swsdoc = swsdoc;
	}
	public List<SWS_RAC_VIEW> getRaclist() {
		return raclist;
	}
	public void setRaclist(List<SWS_RAC_VIEW> raclist) {
		this.raclist = raclist;
	}
}