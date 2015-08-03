package com.xinyou.label.domain.models;

import java.util.List;

import com.xinyou.label.domain.viewes.SUB_WO_MAIN_VIEW;

public class WO_PRINT_DOC {
	private SUB_WO_MAIN_VIEW headdoc;
	private List<SWS_PRINT_DOC> subdocs;
	
	public SUB_WO_MAIN_VIEW getHeaddoc() {
		return headdoc;
	}
	public void setHeaddoc(SUB_WO_MAIN_VIEW headdoc) {
		this.headdoc = headdoc;
	}
	public List<SWS_PRINT_DOC> getSubdocs() {
		return subdocs;
	}
	public void setSubdocs(List<SWS_PRINT_DOC> subdocs) {
		this.subdocs = subdocs;
	}
}

