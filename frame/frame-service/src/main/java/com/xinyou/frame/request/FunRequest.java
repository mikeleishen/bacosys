package com.xinyou.frame.request;

import com.xinyou.frame.domain.entities.FUN_MAIN;

public class FunRequest {
	private String token;
	private String sys_guid;
	private String fun_seqno;
	
	private FUN_MAIN fun_main;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSys_guid() {
		return sys_guid;
	}
	public void setSys_guid(String sys_guid) {
		this.sys_guid = sys_guid;
	}
	public FUN_MAIN getFun_main() {
		return fun_main;
	}
	public void setFun_main(FUN_MAIN fun_main) {
		this.fun_main = fun_main;
	}
	public String getFun_seqno() {
		return fun_seqno;
	}
	public void setFun_seqno(String fun_seqno) {
		this.fun_seqno = fun_seqno;
	}
}
