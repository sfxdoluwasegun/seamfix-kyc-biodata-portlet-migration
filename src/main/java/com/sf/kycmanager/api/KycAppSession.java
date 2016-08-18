package com.sf.kycmanager.api;

import java.util.List;

import com.sf.biocapture.entity.Zone;

public class KycAppSession {
	
	private static KycAppSession session;
	
	private List<Zone> zoneList;
	
	private KycAppSession(){
		// do nada
	}
	
	public static KycAppSession getInstance(){
		if(session == null){
			synchronized (KycAppSession.class) {
				if(session == null){
					session = new KycAppSession();
				}
			}
		}
		
		return session;
	}

}
