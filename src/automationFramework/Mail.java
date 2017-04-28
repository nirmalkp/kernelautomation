package automationFramework;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import utility.Commons;
import utility.Constant;

public class Mail extends Commons{ 
	private static final String SOURCE_FOLDER = System.getProperty("user.dir")+"\\test-output\\html"; // SourceFolder path

	@Test(enabled=true)
	public void mail() throws IOException{
		copyReportsToSharedLocation();
		sendEmail("avinashs@goqago.com");
		
	}

	public void sendEmail(String to)
	{
		final String username = "avinashshitole05@gmail.com";
		final String password = "avinash12345";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}

		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("testinga022@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
			String strDate = sdf.format(cal.getTime());
			// System.out.println("Current date in String Format: " + strDate);
			message.setSubject("Kernel Automation Report - alpha Server - "+strDate);
			message.setText("Dear Team,"
					+ "\n\n Please find the Kernel Automation test report");	


			// Create the message part
			BodyPart bodyPart = new MimeBodyPart();
			// Fill the message
			String body = "Dear Team,"
					+"\n\nResult(s) of Regression test case(s) is/are:"
					//+"\n"+readFromTxt()
					+"\n\nReport available at: "+Constant.REPORT_URL;

			bodyPart.setText(body);
			//bodyPart.setText(ExistingUserLogin.class.getSimpleName()+ExistingUserLogin.class.desiredAssertionStatus());
			Multipart multipart = new MimeMultipart("mixed");
			multipart.addBodyPart(bodyPart);
			//			message.setText(ExistingUserLogin.class.getSimpleName()+ExistingUserLogin.class.desiredAssertionStatus());
			//			MimeBodyPart messageBodyPart = new MimeBodyPart();
			//			messageBodyPart.setText(ExistingUserLogin.class.getSimpleName()+ExistingUserLogin.class.desiredAssertionStatus());
			//			DataSource source = new FileDataSource(OUTPUT_ZIP_FILE);
			//			DataSource source = new FileDataSource(OUTPUT_FILE);
			//			messageBodyPart.setDataHandler(new DataHandler(source));
			//			messageBodyPart.setFileName(source.getName());
			//			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			System.out.println("Done");
		}
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}

	}
	public void copyReportsToSharedLocation() throws IOException{
		FileUtils.copyDirectory(new File(SOURCE_FOLDER), new File(Constant.REPORTS_DEPLOY_LOCATION));
	}
}
