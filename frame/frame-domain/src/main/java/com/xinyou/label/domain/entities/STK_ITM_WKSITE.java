package com.xinyou.label.domain.entities;

import java.math.BigDecimal;

public class STK_ITM_WKSITE extends EntityBase {
    private String stk_itm_wksite_guid;
    private String stk_itm_wksite_id ;
    private String stk_main_guid ;
    private String stk_main_id ;
    private String stk_emp_id ;
    private String sws_guid;
    private String sws_id;
    private String wo_doc_id ;
    private String itm_id ;
    private String stk_rac_id ;
    private String stk_rac_name ;
    private BigDecimal stk_value;
    
	public String getStk_itm_wksite_guid() {
		return stk_itm_wksite_guid;
	}
	public void setStk_itm_wksite_guid(String stk_itm_wksite_guid) {
		this.stk_itm_wksite_guid = stk_itm_wksite_guid;
	}
	public String getStk_itm_wksite_id() {
		return stk_itm_wksite_id;
	}
	public void setStk_itm_wksite_id(String stk_itm_wksite_id) {
		this.stk_itm_wksite_id = stk_itm_wksite_id;
	}
	public String getStk_main_guid() {
		return stk_main_guid;
	}
	public void setStk_main_guid(String stk_main_guid) {
		this.stk_main_guid = stk_main_guid;
	}
	public String getStk_emp_id() {
		return stk_emp_id;
	}
	public void setStk_emp_id(String stk_emp_id) {
		this.stk_emp_id = stk_emp_id;
	}
	public String getSws_guid() {
		return sws_guid;
	}
	public void setSws_guid(String sws_guid) {
		this.sws_guid = sws_guid;
	}
	public String getSws_id() {
		return sws_id;
	}
	public void setSws_id(String sws_id) {
		this.sws_id = sws_id;
	}
	public String getWo_doc_id() {
		return wo_doc_id;
	}
	public void setWo_doc_id(String wo_doc_id) {
		this.wo_doc_id = wo_doc_id;
	}
	public String getItm_id() {
		return itm_id;
	}
	public void setItm_id(String itm_id) {
		this.itm_id = itm_id;
	}
	public String getStk_rac_id() {
		return stk_rac_id;
	}
	public void setStk_rac_id(String stk_rac_id) {
		this.stk_rac_id = stk_rac_id;
	}
	public String getStk_rac_name() {
		return stk_rac_name;
	}
	public void setStk_rac_name(String stk_rac_name) {
		this.stk_rac_name = stk_rac_name;
	}
	public BigDecimal getStk_value() {
		return stk_value;
	}
	public void setStk_value(BigDecimal stk_value) {
		this.stk_value = stk_value;
	}
    public String getStk_main_id() {
		return stk_main_id;
	}
	public void setStk_main_id(String stk_main_id) {
		this.stk_main_id = stk_main_id;
	}
    

}
