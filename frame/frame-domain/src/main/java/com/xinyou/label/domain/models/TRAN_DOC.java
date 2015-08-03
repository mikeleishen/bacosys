package com.xinyou.label.domain.models;

import java.util.List;

import com.xinyou.label.domain.entities.RBA_CTN_RE;
import com.xinyou.label.domain.entities.TRAN_BACO;
import com.xinyou.label.domain.entities.TRAN_BASE_DOC;
import com.xinyou.label.domain.entities.TRAN_ITM;
import com.xinyou.label.domain.entities.TRAN_ITM_BASE;
import com.xinyou.label.domain.entities.TRAN_MAIN;

public class TRAN_DOC {
	
	public TRAN_MAIN getHead() {
		return head;
	}
	public void setHead(TRAN_MAIN head) {
		this.head = head;
	}
	public List<TRAN_ITM> getBody_itm() {
		return body_itm;
	}
	public void setBody_itm(List<TRAN_ITM> body_itm) {
		this.body_itm = body_itm;
	}
	public List<TRAN_BACO> getBody_baco() {
		return body_baco;
	}
	public void setBody_baco(List<TRAN_BACO> body_baco) {
		this.body_baco = body_baco;
	}
	
	public List<TRAN_BASE_DOC> getDoc_base() {
		return doc_base;
	}
	public void setDoc_base(List<TRAN_BASE_DOC> doc_base) {
		this.doc_base = doc_base;
	}
	public List<TRAN_ITM_BASE> getBody_itm_base() {
		return body_itm_base;
	}
	public void setBody_itm_base(List<TRAN_ITM_BASE> body_itm_base) {
		this.body_itm_base = body_itm_base;
	}

	public List<RBA_CTN_RE> getRba_ctn_re() {
		return rba_ctn_re;
	}
	public void setRba_ctn_re(List<RBA_CTN_RE> rba_ctn_re) {
		this.rba_ctn_re = rba_ctn_re;
	}

	private TRAN_MAIN head;
	private List<TRAN_ITM> body_itm;
	private List<TRAN_BACO> body_baco;
	private List<TRAN_ITM_BASE> body_itm_base;
	private List<TRAN_BASE_DOC> doc_base;
	private List<RBA_CTN_RE> rba_ctn_re;
 }

