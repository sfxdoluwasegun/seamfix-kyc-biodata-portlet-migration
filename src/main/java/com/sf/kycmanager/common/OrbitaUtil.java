package com.sf.kycmanager.common;

import java.util.Locale;

import org.apache.log4j.Logger;

import com.liferay.portal.DuplicateUserEmailAddressException;
import com.liferay.portal.DuplicateUserScreenNameException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.UserPasswordException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.sf.kycmanager.util.BiocaptureProperties;

public class OrbitaUtil {
	private static Logger logger = Logger.getLogger(OrbitaUtil.class);
	public static long ROLE_ADMINISTRATOR = 10138;
	public static long ROLE_GUEST = 10139;
	public static long ROLE_OWNER = 10140;
	public static long ROLE_POWER_USER = 10141;
	public static long ROLE_USER = 10142;
	public static long ROLE_COMMUNITY_ADMIN = 10143;
	public static long ROLE_COMMUNITY_MEMBER = 10144;
	public static long ROLE_COMMUNITY_OWNER = 10145;
	public static long ROLE_ORGANIAZATION_ADMIN = 10146;
	public static long ROLE_ORGANIAZATION_MEMBER = 10147;
	public static long ROLE_ORGANIAZATION_OWNER = 10148;

	public static final String DEFAULT_PASSWORD = "password";
	public static final String DOMAIN_EMAIL_SUFFIX = "airtel.com";

	private static String companyId = BiocaptureProperties.getProperties().getProperty("COMPANY_ID", "10132");
	private static String communityRole = BiocaptureProperties.getProperties().getProperty("COMMUNITY_ROLE", "10142");
	private static String supportManagerUserGroupId = BiocaptureProperties.getProperties()
			.getProperty("SUPPORT_MANAGER_USER_GROUP_ID", "10411");
	private static String supportManagerCommunity = BiocaptureProperties.getProperties()
			.getProperty("SUPPORT_MANAGER_COMMUNITY", "10408");

	private static long COMPANY_ID = Long.valueOf(companyId);
	public static final long COMMUNITY_ROLE = Long.valueOf(communityRole);
	public static final long SUPPORT_MANAGER_USER_GROUP_ID = Long.valueOf(supportManagerUserGroupId);
	public static final String SUPPORT_MANAGER_COMMUNITY = supportManagerCommunity;

	public final String PERMISSION_GRANTED = "TRUE";
	public final String PERMISSION_DENIED = "FALSE";

	public static long handleCreateOrbitaUserAccount(String firstName, String lastName, String emailAddress,
			String overrideCommunityId, String defaultPassword, ServiceContext serviceContext, long creatorUserId,
			String groupIds, String organizationIds, String roleIds, String userGroupIds)
					throws PortalException, SystemException {

		long createdOrbitaId = 0;
		logger.info("handleCreateUser");
		boolean alreadyInOrbita = Boolean.FALSE;

		// long companyId = COMPANY_ID;
		String jobTitle = "";
		long organizationId = 0;
		long locationId = 0;
		long[] orgAndLocation = new long[2];
		orgAndLocation[0] = organizationId;
		orgAndLocation[1] = locationId;
		int prefixId = 1;
		int suffixId = 1;
		boolean male = true;
		boolean sendEmail = false;
		int birthdayMonth = 2;
		int birthdayDay = 2;
		int birthdayYear = 1980;
		long facebookId = 0;
		String openId = "";
		User aUser = null;

		boolean autoScreenName = true;
		String formattedFullname = firstName + lastName;
		formattedFullname = formattedFullname.replaceAll("/", ".").replaceAll("-", ".").replaceAll("_", ".")
				.replaceAll(" ", ".");
		String screenName = formattedFullname;

		// first we make sure the email and screen name wont cause prob i.e
		// duplicateEmailException or DuplicateUserScreenNameException while
		// creating orbita user
		try {
			aUser = UserLocalServiceUtil.getUserByEmailAddress(COMPANY_ID, emailAddress);
			if (aUser != null) {
				throw new DuplicateUserEmailAddressException("User with the supplied email already exists");
			}
		} catch (NoSuchUserException e) {
			// desired; therefore continue gracefully
		}
		try {
			if (!autoScreenName) {
				aUser = UserLocalServiceUtil.getUserByScreenName(COMPANY_ID, screenName);
				if (aUser != null) {
					throw new DuplicateUserScreenNameException(
							"User with screen name " + screenName + " already exists.");
				}
			}
		} catch (NoSuchUserException e) {
			// desired; therefore continue gracefully
		}

		System.out.println("In pService method, starting user creation");

		User newlyCreatedUser = null;

		boolean autoPassword = false;
		String password1 = defaultPassword;
		String password2 = defaultPassword;
		String asetting = null;
		String emailSuffix = new String();

		try {
			asetting = DOMAIN_EMAIL_SUFFIX;
			if (asetting != null) {
				emailSuffix = asetting;
			}
		} catch (Exception e) {
			logger.error("", e);
		}

		logger.info("Successfully set all parameters");

		if (emailSuffix != null || !emailSuffix.equalsIgnoreCase("")) {
			// emailAddress = createdUser.getEmailAddress();
		}

		// only make AuthorizedUser an orbita user account if he has an email
		String userCommunityId = "";

		// if the overrideCommunityId is null then get the appropriate community
		if (overrideCommunityId == null || overrideCommunityId.equals("")) {
			userCommunityId = String.valueOf(SUPPORT_MANAGER_COMMUNITY);// used
																		// the
																		// citizen
																		// community
																		// by
																		// default
		} else {
			// just override and put user in this community
			userCommunityId = overrideCommunityId;
		}

		logger.info("Successfully set community parameters");
		logger.info("The User Community Id is" + userCommunityId);
		long communityId = new Long(userCommunityId.trim()).longValue();
		// try creating a authorizedUser..

		logger.info("Start creation, screename is " + screenName + " email is " + emailAddress + " communityId is "
				+ communityId);

		boolean addGroupStatus = Boolean.FALSE;

		newlyCreatedUser = UserLocalServiceUtil.addUser(creatorUserId, COMPANY_ID, autoPassword, password1, password2,
				autoScreenName, screenName, emailAddress, facebookId, openId, Locale.US, firstName, "", lastName,
				prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle, convertParams(groupIds),
				convertParams(organizationIds), convertParams(roleIds), convertParams(userGroupIds), sendEmail,
				serviceContext);

		logger.info("Creation succcessful..now adding to community!!!");

		UserLocalServiceUtil.updatePasswordReset(newlyCreatedUser.getUserId(), Boolean.TRUE);
		addGroupStatus = addUserToCommmunity(newlyCreatedUser.getUserId(), communityId);

		logger.info("Added succcessful");

		if (addGroupStatus == false) {
			logger.info("addGroupStatus is false");
			deleteUser(newlyCreatedUser.getUserId());
		}

		long users[] = { newlyCreatedUser.getUserId() };
		UserLocalServiceUtil.addRoleUsers(ROLE_COMMUNITY_MEMBER, users);

		logger.info("Setting Orbita AuthorizedUserId");
		createdOrbitaId = newlyCreatedUser.getUserId();
		return createdOrbitaId;
	}

	/**
	 * Converts the string retrieved from biocapture.properties to a long[] used
	 * in orbita for assigning users to roles, groups, organizations and user
	 * groups
	 * 
	 * @param paramsString
	 * @return
	 */
	private static long[] convertParams(String paramsString) {
		long[] paramsLongArr = null;
		if (paramsString != null) {
			String[] paramsBreakdown = paramsString.split(Constants.ORBITA_PROPERTIES_DELIMITER);
			paramsLongArr = new long[paramsBreakdown.length];
			int i = 0;
			for (String param : paramsBreakdown) {
				paramsLongArr[i] = Long.valueOf(param);
				i++;
			}
		}
		return paramsLongArr;
	}

	public static long getUserByEmailOrScreenName(String param)
			throws NoSuchUserException, PortalException, SystemException {
		long companyId = COMPANY_ID;
		boolean autoScreenName = true;
		User aUser = null;

		try {
			aUser = UserLocalServiceUtil.getUserByEmailAddress(companyId, param);
			if (aUser != null) {
				return aUser.getUserId();
			}
		} catch (NoSuchUserException e) {
			throw new NoSuchUserException("No user was found with the screen name : " + param);
		}
		try {
			if (!autoScreenName) {
				aUser = UserLocalServiceUtil.getUserByScreenName(companyId, param);
				if (aUser != null) {
					return aUser.getUserId();
				}
			}
		} catch (NoSuchUserException e) {
			throw new NoSuchUserException("No user was found with the screen name : " + param);
		}

		return aUser.getUserId();
	}

	public static boolean addUserToCommmunity(long userId, long communityId) throws SystemException, PortalException {
		boolean status = false;
		long[] group = new long[1];
		group[0] = userId; // possible null if user is not

		logger.debug("UserId: " + userId + ", CommunityId:" + communityId);

		if (!UserLocalServiceUtil.hasGroupUser(communityId, userId)) {
			UserLocalServiceUtil.addGroupUsers(communityId, group);
			status = true;
		} else {
			status = false;
		}

		return status;
	}

	public boolean removeUserFromCommmunity(long userId, long communityId) throws SystemException, PortalException {
		boolean status = false;
		long[] group = new long[1];
		group[0] = userId; // possible null if user is not

		if (UserLocalServiceUtil.hasGroupUser(communityId, userId)) {
			UserLocalServiceUtil.unsetGroupUsers(communityId, group, new ServiceContext());
		}
		status = true;

		return status;
	}

	public boolean updateUserScreeNameAndEmail(long userId, String screenname) {
		boolean success = Boolean.FALSE;
		User user = null;
		try {
			user = UserLocalServiceUtil.getUserById(userId);
		} catch (PortalException e) {
			logger.error("", e);
		} catch (SystemException e) {
			logger.error("", e);
		}

		if (user != null) {
			user.setScreenName(screenname);
			user.setEmailAddress(screenname + DOMAIN_EMAIL_SUFFIX);
			try {
				UserLocalServiceUtil.updateUser(user);
				success = Boolean.TRUE;
			} catch (SystemException e) {
				logger.error("", e);
			}
		}

		return success;
	}

	public static boolean deactivateUserLogin(long orbitaId) throws PortalException, SystemException {
		boolean success = Boolean.FALSE;
		User auser = null;
		try {
			auser = UserLocalServiceUtil.getUserById(orbitaId);
		} catch (NoSuchUserException e) {
			logger.error("", e);
			return success;
		}
		if (auser != null) {
			// auser.setActive(Boolean.FALSE);
			auser.setLockout(Boolean.TRUE);
			UserLocalServiceUtil.updateUser(auser);
			success = Boolean.TRUE;
		}

		return success;
	}

	public static boolean reactivateUSerLogin(long orbitaId) throws PortalException, SystemException {
		boolean success = Boolean.FALSE;
		User auser = null;
		try {
			auser = UserLocalServiceUtil.getUserById(orbitaId);
		} catch (NoSuchUserException e) {
			logger.error("", e);
			return success;
		}
		if (auser != null) {
			// auser.setActive(Boolean.TRUE);
			auser.setLockout(Boolean.FALSE);
			UserLocalServiceUtil.updateUser(auser);
			success = Boolean.TRUE;
		}
		return success;
	}

	public static boolean deleteUser(long orbitaId) throws PortalException, SystemException {
		boolean success = Boolean.FALSE;
		User auser = null;

		try {
			auser = UserLocalServiceUtil.getUserById(orbitaId);
		} catch (NoSuchUserException e) {
			logger.error("", e);
			return success;
		}

		if (auser != null) {
			UserLocalServiceUtil.deleteUser(auser);
			logger.debug("successfully deleted orbita account");
			success = Boolean.TRUE;
		}
		return success;
	}

	public boolean resetPassword(long orbitaId) throws PortalException, SystemException {
		boolean success = Boolean.FALSE;
		User auser = null;
		try {
			auser = UserLocalServiceUtil.getUserById(orbitaId);
		} catch (NoSuchUserException e) {
			logger.error("", e);
			return success;
		}

		if (auser != null) {
			try {
				UserLocalServiceUtil.updatePassword(orbitaId, DEFAULT_PASSWORD, DEFAULT_PASSWORD, Boolean.TRUE);
				logger.debug("successfully updated password");
				success = Boolean.TRUE;
			} catch (UserPasswordException p) {
				logger.error("Password is still the same as default");
				success = Boolean.TRUE;
			}
		}
		return success;
	}

	public static String getOrbitaUserFullName(long orbitaId) throws PortalException, SystemException {
		boolean success = Boolean.FALSE;
		User auser = null;
		String orbitaUserFullName = "";

		try {
			auser = UserLocalServiceUtil.getUserById(orbitaId);
		} catch (NoSuchUserException e) {
			logger.error("", e);
			return orbitaUserFullName;
		}

		if (auser != null) {
			orbitaUserFullName = auser.getLastName() + " " + auser.getFirstName() + " " + auser.getMiddleName();
		}

		return orbitaUserFullName;
	}

	public static boolean getOrbitaUserStatus(long orbitaId) throws PortalException, SystemException {
		boolean status = false;
		User auser = null;

		try {
			auser = UserLocalServiceUtil.getUserById(orbitaId);
		} catch (NoSuchUserException e) {
			// logger.error("", e);
			return status;
		}

		if (auser != null) {
			status = auser.isActive();
		}

		return status;
	}

	@SuppressWarnings("deprecation")
	public void addCommunity(String name, String description, String friendlyURL, long creatorUserId,
			ServiceContext serviceContext) throws PortalException, SystemException {

		GroupLocalServiceUtil.addGroup(Long.valueOf(SUPPORT_MANAGER_COMMUNITY), "", 0L, 5l, name, description, 0,
				friendlyURL, false, Boolean.TRUE, serviceContext);
		// GroupLocalServiceUtil.addGroup(Long.valueOf(SUPPORT_MANAGER_COMMUNITY),
		// "", 5l, name, description, 0,
		// friendlyURL, Boolean.TRUE, serviceContext);
	}

	public void deleteCommunity(long groupId) throws PortalException, SystemException {
		GroupLocalServiceUtil.deleteGroup(groupId);
	}

	public void addUserInCommunity(long userId, long groupId) throws PortalException, SystemException {
		long[] userIds = { userId };
		UserLocalServiceUtil.addGroupUsers(groupId, userIds);
	}

	public void addUserGroupInCommunity(long userGroupId, long groupId) throws PortalException, SystemException {
		long[] userGroups = { userGroupId };
		UserGroupLocalServiceUtil.addGroupUserGroups(groupId, userGroups);
	}
}
