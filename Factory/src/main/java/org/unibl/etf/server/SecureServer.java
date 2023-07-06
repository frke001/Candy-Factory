package org.unibl.etf.server;

import java.io.IOException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import org.unibl.etf.util.ConfigUtil;

public class SecureServer extends Thread{
	
//	public static void main(String args[]) {
		
//		System.setProperty("javax.net.ssl.keyStore", ConfigUtil.readConfig().getProperty("KEYSTORE_PATH"));
//		System.setProperty("javax.net.ssl.keyStorePassword", ConfigUtil.readConfig().getProperty("KEYSTORE_PASSWORD"));
//		
//		SSLServerSocketFactory serverSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
//		try {
//			SSLServerSocket sslServerSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(
//					Integer.parseInt(ConfigUtil.readConfig().getProperty("SECURE_SERVER_PORT")));
//			System.out.println("Server je pokrenut");
//			while(true) {
//				SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
//				new SecureServerThread(sslSocket);
//			}
//		} catch (NumberFormatException ex) {
//			ex.printStackTrace();
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
		
//	}
	@Override
	public void run() {
		System.setProperty("javax.net.ssl.keyStore", ConfigUtil.readConfig().getProperty("KEYSTORE_PATH"));
		System.setProperty("javax.net.ssl.keyStorePassword", ConfigUtil.readConfig().getProperty("KEYSTORE_PASSWORD"));
		
		SSLServerSocketFactory serverSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		try {
			SSLServerSocket sslServerSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(
					Integer.parseInt(ConfigUtil.readConfig().getProperty("SECURE_SERVER_PORT")));
			System.out.println("Server je pokrenut");
			while(true) {
				SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
				new SecureServerThread(sslSocket);
			}
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
