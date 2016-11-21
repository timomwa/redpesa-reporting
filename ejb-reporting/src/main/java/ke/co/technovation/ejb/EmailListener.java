package ke.co.technovation.ejb;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Observes;

import org.apache.log4j.Logger;

import ke.co.technovation.entity.Mail;

@Singleton
@Startup
public class EmailListener {
	
	private Logger logger = Logger.getLogger(getClass());
	
	final Queue<Mail> emails = new ConcurrentLinkedQueue<Mail>();

    public void listen(@Observes Mail mail) {
    	logger.info("\t\t Observed. ping =>> " + mail);
    	logger.info(" Size of set B4 ---> "+emails.size());
    	emails.offer(mail);
    	logger.info(" Size of set AFTR ---> "+emails.size());
    }

}
