package com.sf.kycmanager.api;



import nw.commons.NeemClazz;

import org.hibernate.envers.RevisionListener;

import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.sf.biocapture.entity.AuditEntity;

/**
 * Used by envers for audit purposes
 * @author nuke
 *
 */
public class AuditListener extends NeemClazz implements RevisionListener {

	@Override
	public void newRevision(Object entity) {
		ServiceContext ctx = ServiceContextThreadLocal.getServiceContext();
		AuditEntity ae = (AuditEntity) entity;
		try {
			if(ctx != null){
		        User user = UserLocalServiceUtil.getUser(ctx.getUserId());
		        
		        ae.setOrbitaId(ctx.getUserId());
//		        ae.setRemoteAddress(ctx.getRemoteAddr());
		        ae.setRemoteHost(user.getLastLoginIP());
		        ae.setEmailAddress(user.getEmailAddress());
//		        ae.setUserAgent(ctx.getHeaders().get("user-agent"));
		    }else{
		    	ae.setOrbitaId(0L);
		        ae.setEmailAddress("sys.admin@airtelkyc.com");
		        ae.setRemoteAddress("127.0.0.1");
		        ae.setRemoteHost("127.0.0.1");
		        ae.setUserAgent("System");
		        ae.setCurrentURL("localhost");
		    }
			
		} catch (Exception e) {
			logger.error("Exception ", e);
		}
	}

	@Override
	public void setTargetPropertyFilename() {
		// TODO Auto-generated method stub
		
	}

}
