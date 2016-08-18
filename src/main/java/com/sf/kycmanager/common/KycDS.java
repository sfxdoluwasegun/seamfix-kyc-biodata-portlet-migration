package com.sf.kycmanager.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.sf.biocapture.entity.EnrollmentRef;
import com.sf.biocapture.entity.KycBlacklist;
import com.sf.biocapture.entity.Node;
import com.sf.lfa.ext.core.DataService;

import nw.orm.core.query.QueryModifier;
import nw.orm.core.query.QueryParameter;
import nw.orm.core.service.Nworm;

public abstract class KycDS extends DataService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Nworm dbService;
	protected Logger logger = Logger.getLogger(getClass());

	public KycDS() {
		dbService = Nworm.getInstance();
	}

	public Nworm getDBService() {
		if (dbService == null) {
			synchronized (KycDS.class) {
				dbService = Nworm.getInstance();
			}
		}
		return dbService;
	}

	public Long countOf(Class<?> clazz, Criterion... criterions) {
		QueryModifier qm = new QueryModifier(clazz);
		qm.addProjection(Projections.rowCount());
		return dbService.getByCriteria(Long.class, qm, criterions);
	}

	public Long countRefs() {
		QueryModifier qm = new QueryModifier(EnrollmentRef.class);
		qm.addProjection(Projections.rowCount());

		return dbService.getByCriteria(Long.class, qm);
	}

	public List<EnrollmentRef> listEnrollmentRefs(Integer index) {
		QueryModifier qm = new QueryModifier(EnrollmentRef.class);
		qm.setPaginated(index, 300);
		qm.transformResult(false);

		return dbService.getListByCriteria(EnrollmentRef.class, qm);
	}

	public EnrollmentRef getEnrollmentRef(String ref) {
		return dbService.getByCriteria(EnrollmentRef.class, Restrictions.eq("code", ref));
	}

	public EnrollmentRef getEnrollmentRefByMac(String macAddress) {
		return dbService.getByCriteria(EnrollmentRef.class, Restrictions.eq("macAddress", macAddress));
	}

	public Node getNodeByMac(String macAddress) {
		return dbService.getByCriteria(Node.class, Restrictions.eq("macAddress", macAddress));
	}

	public Node getNodeByRef(String ref) {
		String hql = "SELECT n FROM Node n, EnrollmentRef e WHERE e.macAddress = n.macAddress AND e.code = :ref";
		return dbService.getByHQL(Node.class, hql, QueryParameter.create("ref", ref));
	}

	public String getKitTagByMacAddress(String macAddress) {
		QueryModifier qm = new QueryModifier(EnrollmentRef.class);
		qm.addProjection(Projections.property("code"));
		qm.setPaginated(0, 1);
		return dbService.getByCriteria(String.class, qm, Restrictions.eq("macAddress", macAddress));
	}

	public Date getDateTime(int hourOfDay) {
		Calendar cale = Calendar.getInstance();
		cale.set(cale.get(Calendar.YEAR), cale.get(Calendar.MONTH), cale.get(Calendar.DAY_OF_MONTH), hourOfDay, 0, 0);
		System.out.println(cale.getTime());
		return cale.getTime();
	}

	public Date getDay(int dayOfMonth) {
		Calendar cale = Calendar.getInstance();
		cale.set(cale.get(Calendar.YEAR), cale.get(Calendar.MONTH), dayOfMonth, 0, 0, 0);
		System.out.println(cale.getTime());
		return cale.getTime();
	}

	public Date getMonth(int month) {
		Calendar cale = Calendar.getInstance();
		cale.set(cale.get(Calendar.YEAR), month, 1, 0, 0, 0);
		System.out.println(cale.getTime());
		return cale.getTime();
	}

	public Map<String, Integer> getUsedLicensesBreakdown() {
		Map<String, Integer> usedLicenseBreakdown = new HashMap<String, Integer>();

		String hql = "select e from EnrollmentRef e where e.macAddress is not null ";

		List<EnrollmentRef> enrollmentRefs = dbService.getListByHQL(hql, new HashMap(), EnrollmentRef.class);

		if ((enrollmentRefs != null) && !enrollmentRefs.isEmpty()) {
			int droidKits = 0;
			int windowsKits = 0;
			int otherKits = 0;

			for (EnrollmentRef ref : enrollmentRefs) {
				if (ref.getCode().toLowerCase().contains("droid")) {
					droidKits++;
				} else if (ref.getCode().toLowerCase().contains("kycncc")) {
					windowsKits++;
				} else {
					otherKits++;
				}
			}
			usedLicenseBreakdown.put("droid-kits", droidKits);
			usedLicenseBreakdown.put("windows-kits", windowsKits);
			usedLicenseBreakdown.put("other-kits", otherKits);
		} else {
			enrollmentRefs = new ArrayList<EnrollmentRef>();
		}
		return usedLicenseBreakdown;
	}

	/**
	 * 
	 * @param kitTagNo,
	 *            kit's tag
	 * @param revoked,
	 *            true if kit's license should be revoked, false if otherwise
	 * @param blacklisted,
	 *            true if kit should be blacklisted, false if it is to be
	 *            whitelisted
	 */
	public Boolean doBlacklistOp(String kitTagNo, Boolean revoked, Boolean blacklisted) {
		logger.debug("--->> kit to blacklist/whitelist: " + kitTagNo);
		KycBlacklist kb = null;
		if (kitTagNo != null && !kitTagNo.isEmpty()) {
			kb = dbService.getByCriteria(KycBlacklist.class, Restrictions.eq("blacklistedItem", kitTagNo));
		}

		// blacklist or whitelist kit
		try {
			blacklistKit(kb, kitTagNo, revoked, blacklisted);
		} catch (Exception ex) {
			logger.error("Error in blacklisting kit " + kitTagNo, ex);
			return false;
		}

		// update node
		Node node = getNodeByRef(kitTagNo);
		if (node != null) {
			node.setActive(!blacklisted);
			node.setBlacklisted(blacklisted);

			dbService.update(node);
		}
		return true;
	}

	public Boolean blacklistKit(KycBlacklist kb, String kitTagNo, Boolean revoked, Boolean blacklisted) {
		KycBlacklist kbl = null;
		if (kb != null) {
			kbl = kb;
		} else {
			kbl = new KycBlacklist();
			kbl.setDeleted(false);
			kbl.setItemType(Constants.DEFAULT_ITEM_TYPE);
		}
		kbl.setActive(blacklisted);
		kbl.setBlacklistedItem(kitTagNo);
		kbl.setRevoked(revoked);

		return dbService.createOrUpdate(kbl);
	}

}
