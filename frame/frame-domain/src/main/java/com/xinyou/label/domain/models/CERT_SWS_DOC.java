package com.xinyou.label.domain.models;

import java.util.List;

import com.xinyou.label.domain.viewes.CERT_DOC_VIEW;
import com.xinyou.label.domain.viewes.CERT_SWS_RE_VIEW;

public class CERT_SWS_DOC {
	
	private CERT_DOC_VIEW certDoc;
	private List<CERT_SWS_RE_VIEW> swsList;
	
	public CERT_DOC_VIEW getCertDoc() {
		return certDoc;
	}
	public void setCertDoc(CERT_DOC_VIEW certDoc) {
		this.certDoc = certDoc;
	}
	public List<CERT_SWS_RE_VIEW> getSwsList() {
		return swsList;
	}
	public void setSwsList(List<CERT_SWS_RE_VIEW> swsList) {
		this.swsList = swsList;
	}
	
	

}

