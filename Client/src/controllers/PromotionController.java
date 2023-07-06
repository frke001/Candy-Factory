package controllers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import util.ConfigUtil;
import util.MyLogger;

public class PromotionController extends Thread {

	TextArea promotionArea;

	public PromotionController(TextArea promotionArea) {
		this.promotionArea = promotionArea;
	}

	@Override
	public void run() {
		MulticastSocket socket = null;
		byte[] buffer = new byte[1024];
		try {
			socket = new MulticastSocket(Integer.parseInt(ConfigUtil.readConfig().getProperty("MULTICAST_PORT")));
			InetAddress multicastAddress = InetAddress.getByName(ConfigUtil.readConfig().getProperty("MULTICAST_IP"));
			socket.joinGroup(multicastAddress);

			while (true) {
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				String promotion = new String(packet.getData(), 0, packet.getLength());
				Platform.runLater(() -> {
					this.promotionArea.setText(promotion);

				});
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			MyLogger.log(Level.SEVERE, ex.getMessage());
		}
	}

}
