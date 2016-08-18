package com.sf.kycmanager.common;

import com.sf.biocapture.entity.enums.KycManagerRole;

public enum KycCommunity {
	ADMIN_COMMUNITY(KycManagerRole.ADMIN, "KYC Admin", "/admin"), 
	SYSTEM_COMMUNITY(KycManagerRole.SYSTEM, "System","/system"), 
	SUPPORT_COMMUNITY(KycManagerRole.SUPPORT, "KYC Support", "/support"),
	TECHNICAL_COMMUNITY(KycManagerRole.TECH, "Tech Support","/techsupport"), 
	FIELD_SUPPORT_AGENT_COMMUNITY(KycManagerRole.FSA, "FSA","/fsa"), 
	KYC_DISTRICT_MANAGER_COMMUNITY(KycManagerRole.KDM, "KDM", "/kdm"), 
	AGENT_COMMUNITY(KycManagerRole.AGENT, "KYC Agents","/agents"),
	KYC_ZONAL_CORDINATOR_COMMUNITY(KycManagerRole.KZC, "Zonal Cordinators","/zonalc"), 
	SALES_COMMUNITY(KycManagerRole.SALES, "Sales","/sales"), 
	USER_COMMUNITY(KycManagerRole.USER, "Users","/users"), 
	SUPERVISOR_COMMUNITY(KycManagerRole.SUPERVISOR,"Supervisor","/supervisor"), 
	FOREIGN_NATIONAL_ROLE_COMMUNITY(KycManagerRole.FOREIGN_NATIONAL_ROLE,"Foreign National Role","/foreignnational"), 
	CORPORATE_ROLE_COMMUNITY(KycManagerRole.CORPORATE_ROLE,"Corporate Role","/corporate"), 
	ADDITIONAL_SIM(KycManagerRole.ADDITIONAL_SIM,"Additional sim role","/additionalsim");

	private String name;
	private String url;
	private KycManagerRole role;

	private KycCommunity(KycManagerRole role, String name, String url) {
		this.setName(name);
		this.setUrl(url);
		this.setRole(role);
	}

	// private KycCommunity( String name, String url) {
	// this.setName(name);
	// this.setUrl(url);
	// }

	public static KycCommunity fromRole(KycManagerRole role) {
		KycCommunity community = null;
		if (role != null) {
			KycCommunity[] cs = KycCommunity.values();
			for (KycCommunity kc : cs) {
				if (kc.role == role) {
					community = kc;
					break;
				}
			}
		}

		return community;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public KycManagerRole getRole() {
		return role;
	}

	public void setRole(KycManagerRole role) {
		this.role = role;
	}
}
