package ke.co.technovation.threads.emailer;

import java.util.Hashtable;
import java.util.Queue;

import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import co.ke.technovation.ejb.GreeterEJBI;
import ke.co.technovation.ejb.MailerEJBI;
import ke.co.technovation.entity.Mail;
import ke.co.technovation.entity.ProcessingStatus;

public class EmailerWorker implements Runnable {

	private Context context = null;
	
	private MailerEJBI mailer;

	private Logger logger = Logger.getLogger(getClass());

	private boolean run = true;

	private Queue<Mail> emails;

	public EmailerWorker(Queue<Mail> emails) throws NamingException {
		this.emails = emails;
		initEjb();
	}

	private void initEjb() throws NamingException {
		final Hashtable<String,Object> props = new Hashtable<String,Object>();
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		props.put(Context.PROVIDER_URL, "remote://localhost:4777");
		props.put(Context.SECURITY_PRINCIPAL, "ejb");
		props.put(Context.SECURITY_CREDENTIALS, "test");
		props.put("jboss.naming.client.ejb.context", true);
		context = new javax.naming.InitialContext(props);
		mailer = (MailerEJBI) context.lookup("redpesa-reporting/redpesa-reporting-1.0.jar/MailerEJBImpl!ke.co.technovation.ejb.MailerEJBI" );
    }

	@Override
	public void run() {

		while (run) {

			try {

				Mail email = emails.poll();

				if (email != null) {
					// Any Mail with ID -1 is poison pill.
					if (email.getId() != null && email.getId().compareTo(-1L) == 0) {
						setRun(false);
						break;
					}

					ProcessingStatus status = mailer.sendEmail(email);
					email.setStatus(status);

					if (status != ProcessingStatus.PROCESSED_SUCCESSFULLY) {

						DateTime dateTime = new DateTime(email.getSendAfter());
						dateTime = dateTime.plusMinutes(email.getRetry_wait_time().intValue());
						email.setSendAfter(dateTime.toDate());
						if (email.getRetry_count() >= 1)
							email.setRetry_wait_time(email.getRetry_wait_time() * 2);
						email.setRetry_count(email.getRetry_count() + 1);

						email = mailer.saveEmail(email);// Re-queue
					}
				}

				Thread.sleep(1000);// Wait for a second

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

	}

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

}
