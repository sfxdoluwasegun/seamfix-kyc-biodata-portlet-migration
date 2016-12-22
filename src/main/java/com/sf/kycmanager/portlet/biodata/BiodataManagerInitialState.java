package com.sf.kycmanager.portlet.biodata;

import java.util.List;

import com.sf.biocapture.entity.SmsActivationRequest;
import com.sf.biocapture.pojos.SearchResult;
import com.sf.lfa.core.InitialPortletState;
import java.util.ArrayList;

public class BiodataManagerInitialState implements InitialPortletState {

	private BiodataManagerDataService dataService = new BiodataManagerDataService();
	
	private Integer pageSize = 25;
	
	private List<SearchResult> data = null;

	public BiodataManagerInitialState() {
		super();
		try {
			//data = dataService.getSomeTableContent(pageSize);
                    data = new ArrayList<SearchResult>();
		} catch (Exception e) {
			
		}
	}

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
