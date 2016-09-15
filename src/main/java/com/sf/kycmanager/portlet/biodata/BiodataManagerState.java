package com.sf.kycmanager.portlet.biodata;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import com.sf.biocapture.pojos.DemoObj;
import com.sf.biocapture.pojos.SearchResult;
import com.sf.lfa.ext.core.SfState;

public class BiodataManagerState extends SfState {

	private static final long serialVersionUID = 4606275149321918890L;

	private Integer pageSize = 25;

	private String phoneNumber = "";
	private String name = "";
	private String uniqueID = "";
	private String id = "";
	private Long pk = 0L;

	private String passport;
	private Integer fingerPrintCount;
	private String specialDataCaptured;
	private Map<String, String> demographics = null;

	private List<SearchResult> biodata = null;
	private List<SearchResult> data = null;

	private List<Object[]> biodataObject = null;

	private List<DemoObj> demographicsData = null;
	
	private BiodataManagerDataService dataService = new BiodataManagerDataService();

	public BiodataManagerState() {
		try {
			data = dataService.getSomeTableContent(pageSize);
		} catch (Exception e) {
			
		}
	}

	public BiodataManagerState(PortletRequest portletRequest) {
		super();
		try {
			data = dataService.getSomeTableContent(pageSize);
		} catch (Exception e) {
			
		}
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public Integer getFingerPrintCount() {
		return fingerPrintCount;
	}

	public void setFingerPrintCount(Integer fingerPrintCount) {
		this.fingerPrintCount = fingerPrintCount;
	}

	public Map<String, String> getDemographics() {
		return demographics;
	}

	public void setDemographics(Map<String, String> demographics) {
		this.demographics = demographics;
	}

	public List<SearchResult> getBiodata() {
		return biodata;
	}

	public void setBiodata(List<SearchResult> biodata) {
		this.biodata = biodata;
	}

	public List<Object[]> getBiodataObject() {
		return biodataObject;
	}

	public void setBiodataObject(List<Object[]> biodataObject) {
		this.biodataObject = biodataObject;
	}

	public List<DemoObj> getDemographicsData() {
		return demographicsData;
	}

	public void setDemographicsData(List<DemoObj> demographicsData) {
		this.demographicsData = demographicsData;
	}

	public String getSpecialDataCaptured() {
		return specialDataCaptured;
	}

	public void setSpecialDataCaptured(String specialDataCaptured) {
		this.specialDataCaptured = specialDataCaptured;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<SearchResult> getData() {
		return data;
	}

	public void setData(List<SearchResult> data) {
		this.data = data;
	}

	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}
}
