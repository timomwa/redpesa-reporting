package ke.co.technovation.ejb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import ke.co.technovation.dao.MailDAOI;
import ke.co.technovation.entity.Mail;
import ke.co.technovation.entity.ProcessingStatus;

@Singleton
@Startup
public class EmailProcessorEJB {
	
	@Inject
	private Event<Mail> mailEvent;

	private Logger logger = Logger.getLogger(getClass());
	private Integer max_emails_in_memory = 50;
	public static final List<ProcessingStatus> statuses = new ArrayList<ProcessingStatus>();
	public static final TimerConfig timerConfig = new TimerConfig();
	public static final ScheduleExpression scheduleExpression = new ScheduleExpression().hour("*").minute("*").second("*/1");
	{
		statuses.add(ProcessingStatus.JUST_IN);
		statuses.add(ProcessingStatus.PROCESSED_IN_ERROR);
		timerConfig.setInfo("Every 1 second timer");
		timerConfig.setPersistent(false);
	}
	
	@Inject
	private MailDAOI mailDAO;
	
	@Resource
	private TimerService timerService;

	@PostConstruct
	public void initialize() {
		timerService.createCalendarTimer(scheduleExpression, timerConfig);
	}
	

	@Timeout
	public void programmaticTimout(Timer timer) {
		
		//logger.info("Timeout has happened ?  ---> " + timer.getInfo().toString());
		try {
			
			try{
				timer.cancel();//We cancel it to avoid the nasty jboss warnings.
			}catch(Exception e){
				logger.error(e.getMessage(), e);
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("statuses", statuses);
			params.put("currentTstamp", Calendar.getInstance().getTime());
			List<Mail> outgoing_emails = mailDAO.findByNamedQuery(Mail.UNSENT_EMAILS_ORDER_BY_PRIORITY_AND_TIME_IN,
					params, 0, max_emails_in_memory);

			if (outgoing_emails != null) {

				for (Mail mail : outgoing_emails) {
					try{
						mailEvent.fire(mail);
					}catch(Exception e){
						logger.error(e.getMessage(), e);
					}
				}

			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}finally{
			try{
				timerService.createCalendarTimer(scheduleExpression, timerConfig);
			}catch(Exception e){
				logger.error(e.getMessage(), e);
			}
		}
	}

}
