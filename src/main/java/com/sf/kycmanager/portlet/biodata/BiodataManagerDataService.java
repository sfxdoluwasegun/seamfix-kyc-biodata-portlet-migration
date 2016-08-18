package com.sf.kycmanager.portlet.biodata;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.io.IOUtils;

import com.sf.biocapture.pojos.DemoObj;
import com.sf.biocapture.pojos.SearchResult;
import com.sf.kycmanager.common.KycDS;
import com.sf.lfa.core.InitialPortletState;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class BiodataManagerDataService extends KycDS {

	private static final long serialVersionUID = 2676067398630474192L;

	@Override
	public InitialPortletState initializeState() {

		BiodataManagerInitialState initialState = new BiodataManagerInitialState();
		initialState.setBiodata(null);

		return initialState;
	}

	public List<SearchResult> getTableContent(String phoneNumber, String name, String uniqueId, int pageSize) {

		// if all search parameter is null, then don't search the db
		if ((phoneNumber == null && name == null && uniqueId == null)
				|| (phoneNumber.trim().equals("") && name.trim().equals("") && uniqueId.trim().equals(""))) {
			return null;
		}

		List<SearchResult> searchResult = new ArrayList<SearchResult>();

		String hql = "SELECT s.firstname, s.surname, s.PHONE_NUMBER , s.UNIQUE_ID, s.RECEIPT_TIMESTAMP, s.id, s.SIM_SERIAL FROM biodata_demographics s WHERE s.id is not null ";

		String extraHql = "";

		if (phoneNumber != null && !phoneNumber.trim().equals("")) {
			extraHql += " AND s.PHONE_NUMBER = '" + phoneNumber + "'";
		}

		if (name != null && !name.trim().equals("")) {
			extraHql += " AND ((lower(s.firstname) like lower('%" + name + "%')) OR (lower(s.surname) like lower('%"
					+ name + "%')))";
		}

		if (uniqueId != null && !uniqueId.trim().equals("")) {
			extraHql += " AND lower(s.UNIQUE_ID) like lower('%" + uniqueId + "%')";
			extraHql += " OR lower(s.SIM_SERIAL) like lower('%" + uniqueId + "%')";
		}

		extraHql += " AND rowNum <= " + pageSize + " ORDER BY s.RECEIPT_TIMESTAMP DESC ";

		String query = hql + extraHql;

		Object o = dbService.getBySQL(Object.class, query, null);

		if ((o != null) && (o instanceof ArrayList)) {

			ArrayList<Object> arrObject = (ArrayList<Object>) o;

			for (ListIterator l = arrObject.listIterator(); l.hasNext();) {
				Object obj = l.next();
				if (obj instanceof Object[]) {
					Object[] arr = (Object[]) obj;
					try {

						SearchResult result = new SearchResult();
						result.setCustomerName(arr[1].toString() + " " + arr[0].toString());
						result.setPhoneNumber(arr[2].toString());
						result.setUniqueId(arr[3].toString());
						result.setRegistrationTimestamp((Timestamp) arr[4]);
						result.setBasicDataId(((BigDecimal) arr[5]).longValue());
						result.setSerialNumber(arr[6].toString());

						searchResult.add(result);
					} catch (Exception e) {
						logger.error("Exception: ", e);
					}
				}
			}

		}

		return searchResult;

	}

	public String getPassport(Long id) {

		String sql = "SELECT * FROM PASSPORT p WHERE p.BASIC_DATA_FK = " + id;

		Object o = dbService.getBySQL(Object.class, sql, null);

		String encodedStr = "";

		if ((o != null) && (o instanceof ArrayList)) {

			ArrayList<Object> arrObject = (ArrayList<Object>) o;

			for (ListIterator l = arrObject.listIterator(); l.hasNext();) {
				Object obj = l.next();
				if (obj instanceof Object[]) {
					Object[] arr = (Object[]) obj;
					try {
						Blob image = (Blob) arr[1];
						// InputStream in = image.getBinaryStream();
						// OutputStream out = new FileOutputStream("Text.png");
						// IOUtils.copy(in, out);

						encodedStr = Base64.encode(IOUtils.toByteArray(image.getBinaryStream()));
						// encodedStr = Base64.encode(IOUtils.toByteArray(new
						// FileInputStream(new File("Text.png"))));

						image.free(); // free up resources

					} catch (Exception e) {
						logger.error("Exception: " + e.getMessage(), e);
					}
				}
			}

		}

		// logger.debug("Encoded string: " + encodedStr);

		return encodedStr;

	}

	public Integer getFingerPrintCount(Long id) {

		String sql = "SELECT COUNT(ID) FROM WSQ_IMAGE w WHERE w.BASIC_DATA_FK = " + id;

		Object o = dbService.getBySQL(Object.class, sql, null);

		Integer count = 0;

		if ((o != null) && (o instanceof ArrayList)) {

			ArrayList<Object> arrObject = (ArrayList<Object>) o;

			for (ListIterator l = arrObject.listIterator(); l.hasNext();) {
				Object obj = l.next();
				count = Integer.valueOf(obj.toString());
			}

		}
		// logger.debug("Fingerprint count retrieved: " + count);
		return count;
	}

	public String getSpecialDataCaptured(Long id) {
		String sql = "SELECT BIOMETRICDATATYPE FROM SPECIAL_DATA w WHERE w.BASIC_DATA_FK = " + id;

		Object o = dbService.getBySQL(Object.class, sql, null);
		String results = "N/A";

		if ((o != null) && (o instanceof ArrayList)) {
			// logger.debug("Query results is not null...");
			results = "";
			ArrayList<Object> arrObject = (ArrayList<Object>) o;
			for (ListIterator l = arrObject.listIterator(); l.hasNext();) {
				Object obj = l.next();
				results += obj.toString() + ",";
				// logger.debug("Data type retrieved: " + obj.toString());
			}
			if (!results.equals("")) {
				results = results.substring(0, results.length() - 1);
			} else {
				results = "N/A";
			}
		}
		// logger.debug("Final results: " + results);
		return results;
	}

	public List<DemoObj> getDemographicsData(Long id) {
		String sql = "SELECT b.firstname, b.surname,  b.othername,  b.birthday,  b.gender, "
				+ " b.nationality, b.stateoforigin,  b.occupation, b.subscriberType, b.registrationlga, "
				+ " b.residentialAddress, b.residentialAddressLGA, "
				+ " b.residentialAddressState, b.email, b.dda2, b.dda3, b.dda1, "
				+ " b.registrationType, b.companyId,  b.companyName, b.resident, "
				+ " b.postalCode,  b.companyAddress,  b.companyAddressLGA, b.companyAddressState, "
				+ " b.companyAddressPostalCode, b.id,  b.phone_number,  b.unique_id,  b.mothersMaidenName, "
				+ " b.clientId,  b.mac_address,  b.receipt_timestamp , b.EXPIRY_DATE,"
				+ " b.coutryissuedcode , b.PASSPORT_NUMBER, "
				+ " b.confirmation_status, b.confirmation_timestamp, b.dda20 "
				+ " FROM biodata_demographics b WHERE b.id = " + id;

		Object o = dbService.getBySQL(Object.class, sql, null);
		List<DemoObj> results = new ArrayList<DemoObj>();

		if ((o != null) && (o instanceof ArrayList)) {

			ArrayList<Object> arrObject = (ArrayList<Object>) o;

			for (ListIterator l = arrObject.listIterator(); l.hasNext();) {
				Object obj = l.next();
				if (obj instanceof Object[]) {
					Object[] arr = (Object[]) obj;

					results.add(new DemoObj("FIRST NAME", arr[0] == null ? "N/A" : arr[0].toString()));
					results.add(new DemoObj("SURNAME", arr[1] == null ? "N/A" : arr[1].toString()));
					results.add(new DemoObj("OTHER NAME", arr[2] == null ? "N/A" : arr[2].toString()));
					try {
						results.add(new DemoObj("BIRTHDAY",
								arr[3] == null ? "N/A" : new SimpleDateFormat("dd-MMM-yyyy").format((Date) arr[3])));
					} catch (Exception e) {
						logger.error("Error in formatting birthday:", e);
						results.add(new DemoObj("BIRTHDAY", arr[3] == null ? "N/A" : arr[3].toString()));
					}
					results.add(new DemoObj("GENDER", arr[4] == null ? "N/A" : arr[4].toString()));
					results.add(new DemoObj("NATIONALITY", arr[5] == null ? "N/A" : arr[5].toString()));
					results.add(new DemoObj("STATE OF ORIGIN", arr[6] == null ? "N/A" : arr[6].toString()));

					results.add(new DemoObj("SUBSCRIBER TYPE", arr[8] == null ? "NEW" : arr[8].toString()));
					results.add(new DemoObj("REGISTRATION LGA", arr[9] == null ? "N/A" : arr[9].toString()));
					results.add(new DemoObj("RESIDENTIAL ADDRESS", arr[10] == null ? "N/A" : arr[10].toString()));
					results.add(new DemoObj("RESIDENTIAL ADDRESS LGA", arr[11] == null ? "N/A" : arr[11].toString()));
					results.add(new DemoObj("RESIDENTIAL ADDRESS STATE", arr[12] == null ? "N/A" : arr[12].toString()));
					results.add(new DemoObj("REGISTRATION TYPE", arr[17] == null ? "N/A" : arr[17].toString()));
					results.add(new DemoObj("COMPANY ID", arr[18] == null ? "N/A" : arr[18].toString()));
					results.add(new DemoObj("COMPANY NAME", arr[19] == null ? "N/A" : arr[19].toString()));
					results.add(new DemoObj("POSTAL CODE", arr[21] == null ? "N/A" : arr[21].toString()));
					results.add(new DemoObj("COMPANY ADDRESS", arr[22] == null ? "N/A" : arr[22].toString()));
					results.add(new DemoObj("COMPANY ADDRESS LGA", arr[23] == null ? "N/A" : arr[23].toString()));
					results.add(new DemoObj("COMPANY ADDRESS STATE", arr[24] == null ? "N/A" : arr[24].toString()));
					results.add(
							new DemoObj("COMPANY ADDRESS POSTAL CODE", arr[25] == null ? "N/A" : arr[25].toString()));

					results.add(new DemoObj("EMAIL", arr[13] == null ? "N/A" : arr[13].toString()));
					results.add(new DemoObj("PHONE NUMBER", arr[27] == null ? "N/A" : arr[27].toString()));
					results.add(new DemoObj("UNIQUE ID", arr[28] == null ? "N/A" : arr[28].toString()));
					results.add(new DemoObj("CLIENT ID (KIT TAG)", arr[30] == null ? "N/A" : arr[30].toString()));

					try {
						results.add(new DemoObj("PASSPORT EXPIRY DATE",
								arr[33] == null ? "N/A" : new SimpleDateFormat("dd-MMM-yyyy").format((Date) arr[33])));
					} catch (Exception e) {
						logger.error("Error in formatting birthday:", e);
						results.add(new DemoObj("PASSPORT EXPIRY DATE", arr[33] == null ? "N/A" : arr[33].toString()));
					}

					results.add(new DemoObj("COUNTRY ISSUED CODE", arr[34] == null ? "N/A" : arr[34].toString()));
					results.add(new DemoObj("PASSPORT NUMBER", arr[35] == null ? "N/A" : arr[35].toString()));

					results.add(new DemoObj("OCCUPATION", arr[7] == null ? "N/A" : arr[7].toString()));
					results.add(new DemoObj("PASSPORT IMAGE OVERRIDE REASON",
							arr[38] == null ? "N/A" : arr[38].toString()));

					// agility status
					String agilityStatus = arr[36] == null ? "" : arr[36].toString();
					if (agilityStatus.equals("1")) {
						// msisdn has been sent to agility
						results.add(new DemoObj("AGILITY STATUS", "Sent to Agility"));
						try {
							results.add(new DemoObj("CONFIRMED BY AGILITY", arr[37] == null ? "N/A"
									: new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a").format((Date) arr[37])));
						} catch (Exception e) {
							logger.error("Error in formatting agility timestamp:", e);
							results.add(
									new DemoObj("CONFIRMED BY AGILITY", arr[37] == null ? "N/A" : arr[37].toString()));
						}
					} else {
						results.add(new DemoObj("AGILITY STATUS", "Not Sent"));
						results.add(new DemoObj("CONFIRMED BY AGILITY", "N/A"));
					}

				}
			}

		}

		return results;
	}

	// public BasicData getBasicDataID(String uniqueID) {
	//
	// String hql = "SELECT b FROM BasicData b, UserId u WHERE b.userId = u.id
	// AND u.uniqueId = '" + uniqueID + "' ";
	// Map<String, Object> parameters = new HashMap<String, Object>();
	//
	// return dbService.getByHQL(hql, parameters, BasicData.class);
	// }

}
