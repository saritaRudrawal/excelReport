package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component("emailSender")
public class ReportSender {

	@Autowired
	private JavaMailSenderImpl mailSender;

	@Autowired
	private VelocityEngine velocityEngine;

	private void sendEmail() {

		/*String host = "mail.diaspark.com";
		String from = "sarita.rudrawal@diaspark.com";
		String pass = "s1a2i3s@";*/
		/*Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "mail.diaspark.com");
		props.put("mail.smtp.user", "sarita.rudrawal@diaspark.com");
		props.put("mail.smtp.password", "s1a2i3s@");
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", "false");
		props.put("mail.debug", "true");*/
		Properties props = new Properties();
        try {
            props.load(new FileInputStream(new File("settings.properties")));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
		Session session = Session.getDefaultInstance(props, null);
		
		
		MimeMessage message = new MimeMessage(session);
		
		
		try {
			Address fromAddress = new InternetAddress(
					"sarita.rudrawal@diaspark.com");
			Address toAddress = new InternetAddress(
					"sarita.rudrawal@diaspark.com");
			try {
				message.setFrom(fromAddress);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				message.setRecipient(Message.RecipientType.TO, toAddress);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			try {
				message.setText("Welcome to JavaMail");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			message.setSubject("Testing JavaMail");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			message.setText("Welcome to JavaMail");
			Multipart multipart = new MimeMultipart();
			BodyPart body = new MimeBodyPart();
			body.setContent("hi", "text/html");
			Template template = null ;
			Configuration cfg = new Configuration();
			try {
				template = cfg.getTemplate("html-mail-template.ft1");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Map<String, String> rootMap = new HashMap<String, String>();
            rootMap.put("to", "Bharat Sharma");
            rootMap.put("body", "Sample html email using freemarker");
            rootMap.put("from", "Vijaya.");
            Writer out = new StringWriter();
            try {
				template.process(rootMap, out);
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            body.setContent(out.toString(), "text/html");
			
			multipart.addBodyPart(body);
			body = new MimeBodyPart();
			String filename = "NewExcelFile.xls";
			DataSource source = new FileDataSource(filename);
			body.setDataHandler(new DataHandler(source));
		    body.setFileName(filename);
		    multipart.addBodyPart(body);
		    message.setContent(multipart, "text/html");
		
		
		
		
		
		
		
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Transport transport = null;
		try {
			transport = session.getTransport("smtp");
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			transport.connect();

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			message.saveChanges();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			transport.sendMessage(message, message.getAllRecipients());
			

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			transport.close();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// mailSender.send(preparator);

	}

	public static void main(String args[]) {
		new ReportSender().sendEmail();
	}

}