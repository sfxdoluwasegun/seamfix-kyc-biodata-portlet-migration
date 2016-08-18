package com.sf.kycmanager.portlet.biodata;

import java.util.List;

import com.sf.biocapture.entity.SmsActivationRequest;
import com.sf.lfa.core.InitialPortletState;

public class BiodataManagerInitialState implements InitialPortletState {

	private Integer pageSize = 25;

	private List<SmsActivationRequest> biodata = null;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<SmsActivationRequest> getBiodata() {
		return biodata;
	}

	public void setBiodata(List<SmsActivationRequest> biodata) {
		this.biodata = biodata;
	}

}
