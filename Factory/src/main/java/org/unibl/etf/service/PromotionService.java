package org.unibl.etf.service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.glassfish.jersey.internal.guava.InetAddresses;
import org.unibl.etf.util.ConfigUtil;

public class PromotionService {

	public static void publishPromotion(String promotion) throws IOException {
		MulticastSocket socket = null;
		byte[] buffer = new byte[1024];
		socket = new MulticastSocket();
		InetAddress address = InetAddress.getByName(ConfigUtil.readConfig().getProperty("MULTICAST_IP"));
		buffer = promotion.getBytes();
		DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, address,
				Integer.parseInt(ConfigUtil.readConfig().getProperty("MULTICAST_PORT")));
		socket.send(datagramPacket);

	}

}
