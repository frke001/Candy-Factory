package service;

import util.ConfigUtil;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class MQService {

	public void publishOrder(Order order) throws IOException, TimeoutException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(ConfigUtil.readConfig().getProperty("MQ_HOST"));
		factory.setUsername(ConfigUtil.readConfig().getProperty("MQ_USERNAME"));
		factory.setPassword(ConfigUtil.readConfig().getProperty("MQ_PASSWORD"));

		try (Connection connection = factory.newConnection()) {

			Channel channel = connection.createChannel();
			channel.queueDeclare(ConfigUtil.readConfig().getProperty("MQ_QUEUE_NAME"), true, false, false, null);

			String orderString = toXML(order);

			System.out.println(orderString);

			channel.basicPublish("", ConfigUtil.readConfig().getProperty("MQ_QUEUE_NAME"), null,
					orderString.getBytes("UTF-8"));
		}

	}

	private String toXML(Order order) throws JsonProcessingException {
		String resultString = "";
		XmlMapper xmlMapper = new XmlMapper();
		resultString = xmlMapper.writeValueAsString(order);
		return resultString;
	}
	

	
}
