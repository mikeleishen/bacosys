package com.xinyou.label.domain.models;

import java.util.List;

import com.xinyou.label.domain.viewes.CTN_MAIN_VIEW;

public class PKG_DOC {
	private CTN_MAIN_VIEW pkg;
	private List<CTN_MAIN_VIEW> childrens;
	
	public CTN_MAIN_VIEW getPkg() {
		return pkg;
	}
	public void setPkg(CTN_MAIN_VIEW pkg) {
		this.pkg = pkg;
	}
	public List<CTN_MAIN_VIEW> getChildrens() {
		return childrens;
	}
	public void setChildrens(List<CTN_MAIN_VIEW> childrens) {
		this.childrens = childrens;
	}
}
