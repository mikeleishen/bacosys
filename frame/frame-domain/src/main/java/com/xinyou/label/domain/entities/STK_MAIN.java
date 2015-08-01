package com.xinyou.label.domain.entities;

/**
 *  盘点计划
 * */
public class STK_MAIN extends EntityBase{
	private String inv_id;
	private int stk_status;
	private String stk_memo;
	private long stk_p_bdt;
	private long stk_p_edt;
	private long stk_bdt;
	private long stk_edt;
	private String is_stk;
	
	public String getIs_stk() {
		return is_stk;
	}
	public void setIs_stk(String is_stk) {
		this.is_stk = is_stk;
	}
	public String getInv_id() {
		return inv_id;
	}
	public void setInv_id(String inv_id) {
		this.inv_id = inv_id;
	}
	public int getStk_status() {
		return stk_status;
	}
	public void setStk_status(int stk_status) {
		this.stk_status = stk_status;
	}
	public String getStk_memo() {
		return stk_memo;
	}
	public void setStk_memo(String stk_memo) {
		this.stk_memo = stk_memo;
	}
	public long getStk_p_bdt() {
		return stk_p_bdt;
	}
	public void setStk_p_bdt(long stk_p_bdt) {
		this.stk_p_bdt = stk_p_bdt;
	}
	public long getStk_p_edt() {
		return stk_p_edt;
	}
	public void setStk_p_edt(long stk_p_edt) {
		this.stk_p_edt = stk_p_edt;
	}
	public long getStk_bdt() {
		return stk_bdt;
	}
	public void setStk_bdt(long stk_bdt) {
		this.stk_bdt = stk_bdt;
	}
	public long getStk_edt() {
		return stk_edt;
	}
	public void setStk_edt(long stk_edt) {
		this.stk_edt = stk_edt;
	}
}

