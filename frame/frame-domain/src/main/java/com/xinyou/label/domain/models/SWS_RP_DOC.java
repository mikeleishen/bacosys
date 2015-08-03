package com.xinyou.label.domain.models;

import java.util.List;

import com.xinyou.label.domain.entities.SWS_RP;
import com.xinyou.label.domain.entities.SWS_STAFF;

/** 报工单信息
 * */
public class SWS_RP_DOC {
	private SWS_RP head;
	private List<SWS_STAFF> body;
	public SWS_RP getHead() {
		return head;
	}
	public void setHead(SWS_RP head) {
		this.head = head;
	}
	public List<SWS_STAFF> getBody() {
		return body;
	}
	public void setBody(List<SWS_STAFF> body) {
		this.body = body;
	}
}

