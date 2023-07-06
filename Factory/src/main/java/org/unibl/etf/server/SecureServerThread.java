package org.unibl.etf.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;

import javax.net.ssl.SSLSocket;

import org.unibl.etf.dao.FactoryUserDAO;
import org.unibl.etf.model.FactoryUser;
import org.unibl.etf.model.Order;
import org.unibl.etf.util.ConfigUtil;

public class SecureServerThread extends Thread {

	private SSLSocket socket;
	private BufferedReader in;
	private PrintWriter out;
	private FactoryUserDAO factoryUserDAO;

	public SecureServerThread(SSLSocket socket) {
		this.socket = socket;
		factoryUserDAO = new FactoryUserDAO();
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		start();

	}

	@Override
	public void run() {
		try {
			System.out.println("Novi korisnik...");
			boolean authentication = false;
			String loginRequest = "";
			while (!authentication) {
				loginRequest = in.readLine();
				if (loginRequest.startsWith(ServerProtocolUtil.LOGIN.getMessage())) {
					try {
						String params[] = loginRequest.split(ServerProtocolUtil.SEPARATOR.getMessage());
						String namRequest = params[1];

						if (factoryUserDAO.checkLogin(namRequest)) {
							authentication = true;
							out.println(ServerProtocolUtil.OK.getMessage());
						} else {
							out.println(ServerProtocolUtil.NOT_OK.getMessage());
						}
					} catch (IndexOutOfBoundsException ex) {
						out.println(ServerProtocolUtil.INVALID_REQUEST.getMessage());
						ex.printStackTrace();
					}
				} else {
					out.println(ServerProtocolUtil.INVALID_REQUEST.getMessage());
				}
				String orderRequest = "";
				while (!ServerProtocolUtil.END.getMessage().equals(orderRequest)) {
					orderRequest = in.readLine();
					if (!ServerProtocolUtil.END.getMessage().equals(orderRequest)) {
						String path = ConfigUtil.readConfig().getProperty("ORDER_FILE_PATH") + File.separator + new Date().getTime() + ".txt";
						try {
							PrintWriter printWriter = new PrintWriter(
									new FileWriter(new File(path)));
							printWriter.println(orderRequest);
							printWriter.close();
							out.println(ServerProtocolUtil.OK.getMessage());
						} catch (IOException ex) {
							out.println(ServerProtocolUtil.NOT_OK.getMessage());
						}
					}
				}
			}
			in.close();
			out.close();
			socket.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
