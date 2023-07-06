package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.unibl.etf.server.ServerProtocolUtil;
import util.ConfigUtil;

public class FactoryUserAuthenticationService {
	
	private SSLSocket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	public FactoryUserAuthenticationService() {
		//SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
         String trustStorePath = ConfigUtil.readConfig().getProperty("TRUSTSTORE_PATH");
         String trustStorePassword = ConfigUtil.readConfig().getProperty("TRUSTSTORE_PASSWORD");
         
		try {
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	         KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	         trustStore.load(new FileInputStream(trustStorePath), trustStorePassword.toCharArray());
	         trustManagerFactory.init(trustStore);
	         SSLContext sslContext = SSLContext.getInstance("TLS");
	         sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
	         SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
			socket = (SSLSocket) sslSocketFactory.createSocket(ConfigUtil.readConfig().getProperty("SECURE_SERVER_HOST"), 
					Integer.parseInt(ConfigUtil.readConfig().getProperty("SECURE_SERVER_PORT")));
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
	}
	
	public boolean sendLoginRequest(String name) throws IOException {
		
		out.println(ServerProtocolUtil.LOGIN.getMessage()+ServerProtocolUtil.SEPARATOR.getMessage()+name);
		String result;
		result = in.readLine();
		if(ServerProtocolUtil.INVALID_REQUEST.getMessage().equals(result))
			throw new IllegalArgumentException("Pogrešno ime");
		return ServerProtocolUtil.OK.getMessage().equals(result);
			
	}
	
	public boolean sendOrderStatus(String order) throws IOException {
		out.println(order);
		String result;
		result = in.readLine();
		return ServerProtocolUtil.OK.getMessage().equals(result);
	}
	
	public void end() {
		out.println(ServerProtocolUtil.END.getMessage());
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
