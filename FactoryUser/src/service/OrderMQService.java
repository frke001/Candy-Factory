package service;

import java.io.IOException;
import java.io.StringReader;
import java.util.concurrent.TimeoutException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.unibl.etf.model.Order;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.GetResponse;

import exception.InvalidXMLException;
import util.ConfigUtil;

public class OrderMQService {

	public Order consumeOrder() throws IOException, TimeoutException, InvalidXMLException {
		Order resultOrder = null;
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(ConfigUtil.readConfig().getProperty("MQ_HOST"));
		factory.setUsername(ConfigUtil.readConfig().getProperty("MQ_USERNAME"));
		factory.setPassword(ConfigUtil.readConfig().getProperty("MQ_PASSWORD"));

		try (Connection connection = factory.newConnection()) {

			String queue = ConfigUtil.readConfig().getProperty("MQ_QUEUE_NAME");

			Channel channel = connection.createChannel();
			channel.queueDeclare(queue, true, false, false, null);

			GetResponse response = channel.basicGet(queue, false);
			if (response != null) {
				byte[] body = response.getBody();
				String message = new String(body, "UTF-8");
				if(!validateXML(message))
					throw new InvalidXMLException("Narudžba nije validna(XML ne zadovoljava šemu)");
				resultOrder = fromXML(message);

				long deliveryTag = response.getEnvelope().getDeliveryTag();
				channel.basicAck(deliveryTag, false);
			} else {
				System.out.println("Nema više poruka u redu.");
			}
		}
		return resultOrder;

	}

	private Order fromXML(String order) throws JsonMappingException, JsonProcessingException {
		XmlMapper xmlMapper = new XmlMapper();
		Order resultOrder = xmlMapper.readValue(order, Order.class);
		return resultOrder;
	}
	private boolean validateXML(String xml) {
		try {

            Source schemaSource = new StreamSource(ConfigUtil.readConfig().getProperty("XML_SCHEMA_PATH"));

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(schemaSource);

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xml)));
            return true;
        } catch (SAXException e) {
            System.out.println("XML nije validan: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Došlo je do greške pri validaciji XML-a: " + e.getMessage());
            return false;
        }
	}

}
