package ke.co.technovation.ejb;

import java.util.Queue;

import ke.co.technovation.constants.EmailTestStatus;
import ke.co.technovation.entity.EmailConfiguration;
import ke.co.technovation.entity.Mail;
import ke.co.technovation.entity.ProcessingStatus;

public interface MailerEJBI {

	public EmailTestStatus sendTestMail(EmailConfiguration config);
	
	public boolean queueMail(Mail mail);

	public ProcessingStatus sendEmail(Mail email);

	public Mail saveEmail(Mail email) throws Exception;

	public void processMails(Queue<Mail> emails);

}
