package com.xinyou.label.domain.viewes;

import java.math.BigDecimal;

public class RAC_SCRAP_VIEW {
	private long scrap_dt;
	private String emp_id;
	private String inspector_id;
	private BigDecimal scrap_qty;
	private String scrap_reason;
	public long getScrap_dt() {
		return scrap_dt;
	}
	public void setScrap_dt(long scrap_dt) {
		this.scrap_dt = scrap_dt;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getInspector_id() {
		return inspector_id;
	}
	public void setInspector_id(String inspector_id) {
		this.inspector_id = inspector_id;
	}
	public BigDecimal getScrap_qty() {
		return scrap_qty;
	}
	public void setScrap_qty(BigDecimal scrap_qty) {
		this.scrap_qty = scrap_qty;
	}
	public String getScrap_reason() {
		return scrap_reason;
	}
	public void setScrap_reason(String scrap_reason) {
		this.scrap_reason = scrap_reason;
	}
}
