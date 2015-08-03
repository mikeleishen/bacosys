package com.xinyou.label.domain.viewes;

import java.math.BigDecimal;

public class STK_ITM_VIEW {
	private long stk_dt;
	private String emp_id;
	private String emp_name;
	private String ctn_baco;
	private int ctn_type;
	private String itm_id;
	private BigDecimal itm_qty;
	private BigDecimal stk_itm_qty;
	private int reason;
	private String stkloc;
	private String ctnloc;
	
	public String getStkloc() {
		return stkloc;
	}
	public void setStkloc(String stkloc) {
		this.stkloc = stkloc;
	}
	public String getCtnloc() {
		return ctnloc;
	}
	public void setCtnloc(String ctnloc) {
		this.ctnloc = ctnloc;
	}
	public BigDecimal getStk_itm_qty() {
		return stk_itm_qty;
	}
	public void setStk_itm_qty(BigDecimal stk_itm_qty) {
		this.stk_itm_qty = stk_itm_qty;
	}
	public int getReason() {
		return reason;
	}
	public void setReason(int reason) {
		this.reason = reason;
	}
	private String loc_baco;
	
	public String getLoc_baco() {
		return loc_baco;
	}
	public void setLoc_baco(String loc_baco) {
		this.loc_baco = loc_baco;
	}
	public BigDecimal getItm_qty() {
		return itm_qty;
	}
	public void setItm_qty(BigDecimal itm_qty) {
		this.itm_qty = itm_qty;
	}
	public long getStk_dt() {
		return stk_dt;
	}
	public void setStk_dt(long stk_dt) {
		this.stk_dt = stk_dt;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public String getCtn_baco() {
		return ctn_baco;
	}
	public void setCtn_baco(String ctn_baco) {
		this.ctn_baco = ctn_baco;
	}
	public int getCtn_type() {
		return ctn_type;
	}
	public void setCtn_type(int ctn_type) {
		this.ctn_type = ctn_type;
	}
	public String getItm_id() {
		return itm_id;
	}
	public void setItm_id(String itm_id) {
		this.itm_id = itm_id;
	}
}

