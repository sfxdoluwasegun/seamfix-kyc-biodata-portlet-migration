package com.sf.kycmanager.common;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.sf.biocapture.entity.enums.KycManagerRole;
import com.sf.biocapture.entity.security.KMRole;
import com.sf.biocapture.entity.security.KMUser;
import com.sf.lfa.core.InitialPortletState;
import com.sf.lfa.core.OrbitaConstants;
import com.sf.lfa.core.enums.OrbitaRole;
import com.sf.lfa.entity.OrbitaSite;
import com.sf.lfa.entity.OrbitaUser;
import com.sf.lfa.entity.OrbitaUserGroup;
import com.sf.lfa.ext.service.SiteService;
import com.sf.lfa.ext.service.UserGroupService;
import com.sf.lfa.ext.service.UserService;

public class OrbitaService extends KycDS {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(OrbitaService.class);
	private SiteService siteService;
	private UserService userService;
	// private PageService pageService;
	private UserGroupService userGroupService;
	private ServiceContext context;

	private Company defaultCompany;
	private User defaultUser;

	private OrbitaUserGroup defUserGrp;

	private Map<KycManagerRole, KMRole> roleMap;

	public OrbitaService() {
		init();
	}

	protected void init() {
		logger.debug("*Inside the the initilizer of Orbita Setup...");
		context = ServiceContextThreadLocal.getServiceContext();
		userGroupService = new UserGroupService(context);
		siteService = new SiteService(context);
		userService = new UserService(context);
		// pageService = new PageService(context);
		getDefaultLiferayCompany();
		defUserGrp = userGroupService.findByName(defaultCompany.getCompanyId(), Constants.KM_USER_GROUPNAME);
		roleMap = new HashMap<KycManagerRole, KMRole>();

	}

	private void getDefaultLiferayCompany() {
		try {
			String webId = PropsUtil.get("company.default.web.id");
			defaultCompany = CompanyLocalServiceUtil.getCompanyByWebId(webId);
			defaultUser = defaultCompany.getDefaultUser();
		} catch (PortalException e) {
			logger.error("Exception : ", e);
		} catch (SystemException e) {
			logger.error("Exception : ", e);
		} catch (Exception e) {
			logger.error("Exception : ", e);
		}
	}

	public void initializeOrbita() {
		logger.debug("Inside the initializeOrbita method...");
		if (defUserGrp == OrbitaConstants.NOGROUP) {
			createDefaulUserGroup();
		}

		// create kyc sites
		for (KycCommunity site : KycCommunity.values()) {
			validateSite(site.getName(), site.getName(), site.getUrl());
			// Set<JqsPage> pages = JqsPage.listPages(site);
			// int priority = 1;
			// for(JqsPage page: pages) {
			// validatePage(oSite, page.getName(), page.getUrl(),
			// page.getName(), page.getName(), priority, page.getPortletId());
			// priority =+1;
			// }
		}
		logger.debug("Determining the existence of KYC Roles");

		for (KycManagerRole role : KycManagerRole.values()) {
			roleMap.put(role, validateKycRoles(role));
		}

		logger.debug("About to determine existence of super admin");
		OrbitaUser admin = createOrbitaSupportAdmin();
		if (admin != OrbitaConstants.NOBODY) {
			KMUser auser = getDBService().getByCriteria(KMUser.class,
					Restrictions.eq("orbitaId", admin.getPrimaryKey()));
			if (auser == null) {
				auser = getDBService().getByCriteria(KMUser.class,
						Restrictions.eq("emailAddress", Constants.SYSTEM_ADMIN_EMAIL));
				if (auser == null) {
					auser = new KMUser();
					auser.setOrbitaId(admin.getPrimaryKey());
					auser.setEmailAddress(Constants.SYSTEM_ADMIN_EMAIL);
					auser.setMobile("07089326376");

					KMRole role = roleMap.get(KycManagerRole.SYSTEM);
					if (role == null) {
						role = new KMRole();
						role.setRole(KycManagerRole.SYSTEM);
						dbService.create(role);
					}
					auser.setPassword("*peters#");
					auser.addRole(role);
					dbService.create(auser);
				} else {
					auser.setOrbitaId(admin.getPrimaryKey());
					dbService.update(auser);
				}
			}
		}

	}

	private void createDefaulUserGroup() {
		OrbitaUserGroup oug = new OrbitaUserGroup();
		oug.setDescription("Default KM user group");
		oug.setName(Constants.KM_USER_GROUPNAME);
		oug.setCompanyId(defaultCompany.getCompanyId());
		oug.setCreatorUserId(defaultUser.getUserId());
		oug.setActive(true);
		defUserGrp = userGroupService.create(oug);
	}

	//

	// public OrbitaPage validatePage(OrbitaSite site, String name, String url,
	// String description, String title, int priority, String pid) {
	// OrbitaPage page = pageService.findByFriendlyURL(url,
	// site.getPrimaryKey(), true);
	// if(page == OrbitaConstants.NOPAGE) {
	// page = new OrbitaPage();
	// page.setCompanyId(defaultCompany.getCompanyId());
	// page.setDescription(description);
	// page.setFriendlyURL(url);
	// page.setHidden(false);
	// page.setPageType("portlet");
	// page.setPageName(name);
	// page.setPageTitle(title);
	// page.setParentLayoutID(0L);
	// page.setPrivatePage(true);
	// page.setPriority(priority);
	// page.setParentSiteId(site.getPrimaryKey());
	// page.setCreatorUserId(site.getCreatorUserId());
	// page = pageService.create(page);
	// pageService.addPortlet(page.getPrimaryKey(), pid);
	// }
	//
	// return page;
	// }

	public OrbitaSite getCommunityByName(KycCommunity site) {
		if ((defaultCompany == null) || (site == null) || (siteService == null))
			return null;

		return siteService.findByName(defaultCompany.getPrimaryKey(), site.getName());
	}

	private OrbitaUser createOrbitaSupportAdmin() {
		OrbitaUser oUser = userService.findByEmail(defaultCompany.getPrimaryKey(), Constants.SYSTEM_ADMIN_EMAIL);
		OrbitaSite site = siteService.findByName(this.getDefaultCompany().getCompanyId(),
				KycCommunity.SYSTEM_COMMUNITY.getName());
		if (oUser == OrbitaConstants.NOBODY && site != OrbitaConstants.NOSITE) {

			try {
				oUser = getDummyLiferayData(new long[] { site.getPrimaryKey() });
				oUser = userService.create(oUser);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
		}
		return oUser;
	}

	private OrbitaUser getDummyLiferayData(long[] userSites) throws PortalException, SystemException {
		OrbitaUser userData = new OrbitaUser();
		userData.setAutoScreenName(true);
		userData.setAutoPassword(false);
		userData.setBirthdayDay(28);
		userData.setBirthdayMonth(5);
		userData.setBirthdayYear(1981);
		userData.setMale(true);
		userData.setPassword("*peters#");
		userData.setPasswordConfirmation("*peters#");
		userData.setCompanyId(this.getDefaultCompany().getCompanyId());
		userData.setCreatorId(this.getDefaultUser().getUserId());
		userData.setFirstName("SYS");
		userData.setLastName("ADMIN");
		userData.setJobTitle("System Admin");
		userData.setUserEmail(Constants.SYSTEM_ADMIN_EMAIL);
		userData.setGroupIds(userSites);
		userData.setUserGroupIds(new long[] { this.defUserGrp.getPrimaryKey() });
		Role role = RoleLocalServiceUtil.getRole(this.getDefaultCompany().getCompanyId(),
				OrbitaRole.USER.getRoleName());
		userData.setRoleIds(new long[] { role.getPrimaryKey() });
		return userData;
	}

	private KMRole validateKycRoles(KycManagerRole r) {
		KMRole role = getRoleByName(r);
		if (role == null) {
			role = new KMRole();
			role.setRole(r);
			getDBService().create(role);
		}
		return role;
	}

	private KMRole getRoleByName(KycManagerRole r) {
		return getDBService().getByCriteria(KMRole.class, Restrictions.eq("role", r));
	}

	private OrbitaSite validateSite(String siteName, String description, String url) {
		OrbitaSite site = siteService.findByName(defaultCompany.getPrimaryKey(), siteName);
		if (site == OrbitaConstants.NOSITE) {
			site = new OrbitaSite();
			site.setCompanyId(defaultCompany.getCompanyId());
			site.setCreatorUserId(defaultUser.getUserId());
			site.setDescription(description);
			site.setFriendlyUrl(url);
			site.setGroupType(GroupConstants.TYPE_SITE_PRIVATE);
			site.setManualMembership(false);
			site.setMembershipRestriction(GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION);
			site.setName(siteName);
			site.setParentGroupId(0L);
			site.setLiveGroupId(0L);
			site = siteService.create(site);
		}
		return site;
	}

	public UserService getUserService() {
		return this.userService;
	}

	public UserGroupService getUserGroupService() {
		return this.userGroupService;
	}

	//
	public SiteService getSiteService() {
		return this.siteService;
	}

	// public PageService getPageService() {
	// return this.pageService;
	// }

	public User getDefaultUser() {
		return this.defaultUser;
	}

	public Company getDefaultCompany() {
		return this.defaultCompany;
	}

	@Override
	public InitialPortletState initializeState() {
		// NA
		return null;
	}

	// public OrbitaUserGroup getDefaultUserGroup() {
	// return this.defUserGrp;
	// }

}
