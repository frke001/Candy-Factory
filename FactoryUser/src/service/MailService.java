package service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.unibl.etf.model.Order;

import util.ConfigUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MailService {
	
	//private static final String MAIL_CONFIG_PATH="D:\\Podaci Preneseno\\Nemanja\\Desktop\\Faks\\3. GODINA\\Sesti semestar\\Mrezno i distribuirano programiranje\\ProjekatMDP\\FactoryUser\\resources\\mail.properties";
	
	public void sendMail(Order order) throws IOException, MessagingException {
        Properties properties=new Properties();
        properties.load(new FileInputStream(ConfigUtil.readConfig().getProperty("MAIL_CONFIG_PATH")));
        String username=properties.getProperty("username");
        String password=properties.getProperty("password");
        Session session=Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });
        Message message=new MimeMessage(session);
        message.setFrom(new InternetAddress(username,"Fabrika"));
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(order.getUserEmail()));
        message.setSubject("Narudžba");
        message.setText(order.toString());
        Transport.send(message);
    }
}
