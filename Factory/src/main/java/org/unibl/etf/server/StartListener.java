package org.unibl.etf.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.unibl.etf.util.ConfigUtil;

public class StartListener implements ServletContextListener {
	
	public static Registry registry;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		new SecureServer().start();
		try {
			registry = LocateRegistry.createRegistry(Integer.parseInt(ConfigUtil.readConfig().getProperty("REGISTRY_PORT")));
			System.out.println("Startovan registar");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
