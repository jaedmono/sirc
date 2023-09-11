package co.gov.movilidadbogota.core.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.movilidadbogota.core.dao.ParametroSimurDAO;
import co.gov.movilidadbogota.core.dto.ParametroDTO;
import co.gov.movilidadbogota.core.util.SystemParameters;

/**
 * 
 * @author franzjr
 *
 */

@Service
public class NotificationSender {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationSender.class);

	@Autowired
	private ParametroSimurDAO parametroSimurDAO;

	public void sendEmail(String recipient, String subject, String msg) throws MessagingException {

		Properties props = new Properties();

		ParametroDTO host = parametroSimurDAO.findByKey(SystemParameters.EMAIL_HOST.getValue());
		ParametroDTO socketFactoryPort = parametroSimurDAO
				.findByKey(SystemParameters.EMAIL_SOCKETFACTORY_PORT.getValue());
		ParametroDTO socketFactoryClass = parametroSimurDAO
				.findByKey(SystemParameters.EMAIL_SOCKETFACTORY_CLASS.getValue());
		ParametroDTO auth = parametroSimurDAO.findByKey(SystemParameters.EMAIL_AUTH.getValue());
		ParametroDTO port = parametroSimurDAO.findByKey(SystemParameters.EMAIL_PORT.getValue());
		ParametroDTO from = parametroSimurDAO.findByKey(SystemParameters.EMAIL_FROM.getValue());
		ParametroDTO password = parametroSimurDAO.findByKey(SystemParameters.EMAIL_PASSWORD.getValue());

		props.put("mail.smtp.host", host.getValorParametro());
		props.put("mail.smtp.socketFactory.port", socketFactoryPort.getValorParametro());
		props.put("mail.smtp.socketFactory.class", socketFactoryClass.getValorParametro());
		props.put("mail.smtp.auth", auth.getValorParametro());
		props.put("mail.smtp.port", port.getValorParametro());
		props.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from.getValorParametro(), password.getValorParametro());
			}
		});

		try {

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from.getValorParametro()));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
			message.setSubject(subject);
			message.setText(msg, "utf-8", "html");

			Transport.send(message);

		} catch (MessagingException e) {
			LOGGER.error(e.getMessage());
			throw new RuntimeException(e);
		}

	}

	// //Unit Test -> RUN AS JAVA
	// public void main(String args[]) throws Exception {
	//
	// NotificationSender.sendEmail("jbarrios@zenware.com.co",
	// "NotificationSender Test", "<html>ja<br>ja<br>test</html>");
	// }

}