
/**
 * @author Charles Ofoegbu
 * 
 */
package com.sf.kycmanager.common;

import com.sf.kycmanager.util.BiocaptureProperties;

public class Constants {
	
	public static final String SYSTEM_ADMIN_EMAIL = BiocaptureProperties.getProperties().getProperty("SYSTEM_ADMIN_EMAIL");
	public static final String SUPPORT_ADMIN_PASSWORD = "test";
	public static final String SUPPORT_ADMIN_FIRSTNAME = "Support";
	public static final String SUPPORT_ADMIN_LASTNAME = "Admin";
	
	//STATUS CONSTANTS
	public static final String FAULT_STATUS_RESOLVED = "Resolved";
	public static final String FAULT_STATUS_UNRESOLVED = "UnResolved";
	
	// SETTINGS NAMES & DEFAULTS
	public static final String PAGE_SIZE = "PAGE_SIZE";
	public static final String PAGE_SIZE_VALUE = "25";
	
	//CRUD CONSTANTS
	public static final String CREATE = "CREATE";
	public static final String UPDATE = "UPDATE";
	public static final String DELETE = "DELETE";
	
	//ISSUE TYPE CONSTANTS
	public static final String ISSUE_TYPE_FAULTY = "Faulty";
	public static final String ISSUE_TYPE_STOLLEN = "Stollen";
	
	//ENTITY CONTANTS
	public static final String ENTITY_NODE	= "NODE";
	public static final String ENTITY_DEVICE = "DEVICE";
	public static final String ENTITY_REPORT = "REPORT";
	public static final String ENTITY_LICENSE = "LICENSE";
	public static final String ENTITY_RECIPIENT = "RECIPIENT";
	public static final String ENTITY_NODE_MANAGER = "NODE_MANAGER";
	public static final String ENTITY_MESSAGE_CENTER = "ENTITY_MESSAGE_CENTER";
	public static final String ENTITY_NOTIFICATION_RECIEPIENT_MAPPER = "NOTIFICATION_RECIEPIENT_MAPPER";
	public static final String ENTITY_FIELD_SUPPORT_AGENT= "ENTITY_FIELD_SUPPORT_AGENT";
	public static final String ENTITY_SUPPORT_MANAGER= "ENTITY_SUPPORT_MANAGER";
	public static final String ENTITY_ORBITA_USER= "ENTITY_ORBITA_USER";
	
	//MISC
	public static final String LICENSE_DELIMITER = "\n";
	
	//MEMCACHE SALTS
	public static final String SYNC_FILE_LEVEL_GRAPH_SALT_1 = "SYNC_FILE_LEVEL_GRAPH";
	public static final String SYNC_FILE_LEVEL_GRAPH_SALT_2 = "SYNC_FILE_LEVEL_GRAPH_2";
	public static final String ACTIVE_CLIENTS = "ACTIVE_CLIENTS";
	public static final String CLIENTS_DISTRIBUTION = "CLIENTS_DISTRIBUTION";
	public static final String DAILY_SYNC = "DAILY_SYNC";
	public static final String DAILY_ACTIVATION = "DAILY_ACTIVATION";
	
	//DEVICE TYPES
	public static final String DEVICE_TYPE_CAMERA = "Camera";
	public static final String DEVICE_TYPE_FINGER_PRINT_SCANNER = "Fingerprint_Scanner";
	

	//LICENSE TYPES
	public static final String LICENSE_TYPE_GRFINGERLICENSE = "GrFingerLicenseAgreement.txt";
	public static final String LICENSE_TYPE_WSQ_CONTRY = "WSQCountry.txt";
	public static final String LICENSE_TYPE_WSQ_STATE = "WSQState.txt";
	
	public static final int SYNC_FILE_LEVEL_GRAPH_RESOLUTION = 144;	
	
	//Orbita Constants - Added by Nnanna Madu (Feb, 2015)
	public static final String DEFAULT_PASSWORD = "*p@ssw0rd#";
	public static final String ORBITA_PROPERTIES_DELIMITER = ",";
	public static final String DEFAULT_ITEM_TYPE = "TAG";
	public static final int DEFAULT_PAGE_SIZE = 25;
	public static final String KM_USER_GROUPNAME = "KycManager Users";
	
	public static final String TOTAL_LICENCE_QUOTA = "TOTAL_LICENCE_QUOTA";
	
}
