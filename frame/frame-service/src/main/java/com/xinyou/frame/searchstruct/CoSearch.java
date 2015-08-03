package com.xinyou.frame.searchstruct;


public class CoSearch {

	public String getCo_guid() {
		return co_guid;
	}
	public void setCo_guid(String co_guid) {
		this.co_guid = co_guid;
	}
	public String getCo_id() {
		return co_id;
	}
	public void setCo_id(String co_id) {
		this.co_id = co_id;
	}
	public String getCo_name() {
		return co_name;
	}
	public void setCo_name(String co_name) {
		this.co_name = co_name;
	}
	public int getPage_no() {
		return page_no;
	}
	public void setPage_no(int page_no) {
		this.page_no = page_no;
	}
	public int getPage_size() {
		return page_size;
	}
	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}
	private String co_guid;
	private String co_id;
	private String co_name;
	private int page_no;
	private int page_size;
	
}
