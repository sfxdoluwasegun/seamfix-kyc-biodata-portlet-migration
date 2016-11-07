package com.sf.kycmanager.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import nw.commons.NeemClazz;
import nw.orm.core.service.Nworm;

public class StartupListener extends NeemClazz implements ServletContextListener {

	public StartupListener() {
		// do nada
	}

	public void contextInitialized(ServletContextEvent arg0) {
		logger.debug("Starting up Biodata Manager");
		Nworm instance = Nworm.getInstance();
		instance.enableSessionByContext();
		instance.disableJTA();
		logger.debug("DB Pool initialization complete");
		OrbitaService os = new OrbitaService();
		os.initializeOrbita();
		logger.debug("Orbita Service initialization complete");
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		logger.debug("Shutting down Biodata Manager");
		Nworm.getInstance().closeFactory();
		logger.debug("Goodbye KYC Manager");
	}

	@Override
	public void setTargetPropertyFilename() {
		// TODO Auto-generated method stub
		
	}

}
