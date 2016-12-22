package com.sf.biocapture.pojos;

import java.sql.Timestamp;

public class SearchResult {

	private String customerName;
	private String phoneNumber;
	private String uniqueId;
	private Timestamp registrationTimestamp;
	private Long basicDataId;
	private String serialNumber;
        private String mothersMaidenName;

	public SearchResult() {

	}

	public SearchResult(String customerName, String phoneNumber, String uniqueId, Timestamp registrationTimestamp,
			Long basicDataId, String serialNumber, String mothersMaidenName) {
		this.customerName = customerName;
		this.phoneNumber = phoneNumber;
		this.uniqueId = uniqueId;
		this.registrationTimestamp = registrationTimestamp;
		this.basicDataId = basicDataId;
		this.setSerialNumber(serialNumber);
                this.mothersMaidenName = mothersMaidenName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public Timestamp getRegistrationTimestamp() {
		return registrationTimestamp;
	}

	public void setRegistrationTimestamp(Timestamp registrationTimestamp) {
		this.registrationTimestamp = registrationTimestamp;
	}

	public Long getBasicDataId() {
		return basicDataId;
	}

	public void setBasicDataId(Long basicDataId) {
		this.basicDataId = basicDataId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

        public String getMothersMaidenName() {
            return mothersMaidenName;
        }

        public void setMothersMaidenName(String mothersMaidenName) {
            this.mothersMaidenName = mothersMaidenName;
        }
}
