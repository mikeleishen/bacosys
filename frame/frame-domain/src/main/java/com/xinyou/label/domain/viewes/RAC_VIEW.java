package com.xinyou.label.domain.viewes;

import java.math.BigDecimal;

public class RAC_VIEW {
	private String rac_seqno;
	private String rac_id;
	private String rac_name;
	private BigDecimal rac_pkg_qty;
	
	public String getRac_seqno() {
		return rac_seqno;
	}
	public void setRac_seqno(String rac_seqno) {
		this.rac_seqno = rac_seqno;
	}
	public String getRac_id() {
		return rac_id;
	}
	public void setRac_id(String rac_id) {
		this.rac_id = rac_id;
	}
	public String getRac_name() {
		return rac_name;
	}
	public void setRac_name(String rac_name) {
		this.rac_name = rac_name;
	}
	public BigDecimal getRac_pkg_qty() {
		return rac_pkg_qty;
	}
	public void setRac_pkg_qty(BigDecimal rac_pkg_qty) {
		this.rac_pkg_qty = rac_pkg_qty;
	}
}

