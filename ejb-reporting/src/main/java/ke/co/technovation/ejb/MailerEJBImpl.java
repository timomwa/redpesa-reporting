package ke.co.technovation.ejb;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Queue;

import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import ke.co.technovation.constants.EmailTestStatus;
import ke.co.technovation.dao.MailDAOI;
import ke.co.technovation.entity.EmailConfiguration;
import ke.co.technovation.entity.Mail;
import ke.co.technovation.entity.ProcessingStatus;

@Stateless
public class MailerEJBImpl implements MailerEJBI {
	
	private static final String KEY_SMTP_PORT = "mail.smtp.port";
	private static final String KEY_SMTP_SMTP_AUTH = "mail.smtp.auth";
	private static final Object KEY_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	private Logger logger = Logger.getLogger(getClass());
	static Properties mailServerProperties;
	static Session getMailSession;
	private final String SENDER_NAME = "Shinda Washinde Stats Bot";
	
	@Inject
	private MailDAOI mailDAO;
	
	@Inject
	private EmailConfigurationEJBI emailConfigEJB;
	
	
	@PostConstruct
	public void init(){
		setupProperties();
		
	}
	
	private void setupProperties() {
		
		EmailConfiguration config = null;
		
		try{
			
			config = emailConfigEJB.getTheFirstConfig();
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e );
		}
		
		mailServerProperties = new Properties();
		mailServerProperties.put("mail.smtp.port", config.getSmtp_port());
		mailServerProperties.put("mail.smtp.auth", config.getAuth());
		mailServerProperties.put("mail.smtp.starttls.enable", config.getStarttls_enabled());
		
		
	}
	
	@Override
	@Asynchronous
	public void processMails(Queue<Mail> emails){
		
		try{
			
			while(emails.size()>0){
				
				try{
					
					Mail email = emails.poll();
					ProcessingStatus status = sendEmail(email);
					email.setStatus(status);

					if (status != ProcessingStatus.PROCESSED_SUCCESSFULLY) {

						DateTime dateTime = new DateTime(email.getSendAfter());
						dateTime = dateTime.plusMinutes(email.getRetry_wait_time().intValue());
						email.setSendAfter(dateTime.toDate());
						if (email.getRetry_count() >= 1)
							email.setRetry_wait_time(email.getRetry_wait_time() + email.getRetry_count() );
						email.setRetry_count(email.getRetry_count() + 1);
					}

					email = saveEmail(email);// Re-queue?
					
				}catch(Exception e){
					logger.error(e.getMessage(), e);
				}
				
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
	}
	
	@Override
	public Mail saveEmail(Mail email) throws Exception{
		return mailDAO.save(email);
	}
	
	@Override
	public boolean queueMail(Mail mail){
		
		boolean success = true;
		
		try {
			
			if(mail.getConfig_id()==null){
				EmailConfiguration config = emailConfigEJB.getTheFirstConfig();
				if(config!=null)
					mail.setConfig_id(config.getId());
			}
				
			mail = mailDAO.save(mail);
			
		} catch (Exception e) {
			success = false;
			logger.error(e.getMessage(), e );
		}
		
		return success;
	}
	
	
	@Override
	public ProcessingStatus sendEmail(Mail email){
		
		ProcessingStatus status = ProcessingStatus.PROCESSED_SUCCESSFULLY;
		Transport transport = null;
		
		try{
			
			EmailConfiguration config = null;
			
			if(email.getConfig_id().compareTo(-1L)<=0){
				config = emailConfigEJB.getTheFirstConfig();
				if(config!=null)
					email.setConfig_id(config.getId());
			}else{
				config = emailConfigEJB.findConfigById(email.getConfig_id());
			}
			
			Session mailsession = Session.getDefaultInstance(mailServerProperties, null);
			MimeMessage message = new MimeMessage(mailsession);
			
			String[] emails = email.getEmail().split("[,]");
			
			for(int i = 0; i<emails.length ; i++)
				if(emails[i]!=null && !emails[i].trim().isEmpty())
					message.addRecipient(Message.RecipientType.TO, new InternetAddress( emails[i] ));
			
			message.setSubject(email.getSubject());
			message.setSender(new InternetAddress(config.getSend_from_email(),SENDER_NAME));
			message.setFrom(new InternetAddress(config.getSend_from_email(),SENDER_NAME));
			message.setContent(email.getBody(), "text/html");
			
			transport = mailsession.getTransport("smtp");
			transport.connect(config.getSmtp_host(), config.getEmail_username(), config.getEmail_password());
			
			transport.sendMessage(message, message.getAllRecipients());		
		
			status = ProcessingStatus.PROCESSED_SUCCESSFULLY;
			
		}catch(Exception e){
			
			status = ProcessingStatus.PROCESSED_IN_ERROR;
			logger.error(e.getMessage(), e );
		
		}finally{
			
			try{
				if(transport!=null)
					transport.close();
			}catch(Exception e){
				logger.error(e.getMessage(), e );
			}
			
		}
		
		return status;
	}
	
	
	@Override
	public EmailTestStatus sendTestMail(EmailConfiguration config){
		
		EmailTestStatus status  = EmailTestStatus.SUCCESS;
		Transport transport = null;
		
		try {
		
			Properties properties = new Properties();
			properties.put(KEY_SMTP_PORT, config.getSmtp_port());
			properties.put(KEY_SMTP_SMTP_AUTH, config.getAuth());
			properties.put(KEY_SMTP_STARTTLS_ENABLE, config.getStarttls_enabled());
			
			Session mailsession = Session.getDefaultInstance(properties, null);
			MimeMessage message = new MimeMessage(mailsession);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(config.getReceiver_test_email()));
			message.setSubject("Test Email - OK");
			message.setSender(new InternetAddress(config.getSend_from_email(),"Mobile Money Stats Bot - Test"));
			message.setFrom(new InternetAddress(config.getSend_from_email(),"Mobile Money Stats Bot - Test"));
		
			String emailBody = "Test email by application. Sanity Check email. If you can read this, email configuration is ok! " + "<br><br> Regards, <br>Stats Bot";
			message.setContent(emailBody, "text/html");
			
			transport = mailsession.getTransport("smtp");
			transport.connect(config.getSmtp_host(), config.getEmail_username(), config.getEmail_password());
			
			transport.sendMessage(message, message.getAllRecipients());
			
		} catch(javax.mail.AuthenticationFailedException e) {
			
			status = EmailTestStatus.AUTHENTICATION_FAILURE;
			logger.error(e.getMessage(), e );
			
		}catch (MessagingException | UnsupportedEncodingException e) {
			
			if(e instanceof MessagingException)
				status = EmailTestStatus.NETWORK_FAILURE;
			if(e instanceof UnsupportedEncodingException)
				status = EmailTestStatus.APPLICATION_FAILURE;
			
			logger.error(e.getMessage(), e );
			
		} catch(Exception e){
			status = EmailTestStatus.APPLICATION_FAILURE;
			logger.error(e.getMessage(), e );
		}finally{
			
			try{
				if(transport!=null)
					transport.close();
			}catch(Exception e){
				logger.error(e.getMessage(), e );
			}
			
		}
		
		return status;
		
	}

}
