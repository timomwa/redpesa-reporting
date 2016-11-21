package ke.co.technovation.web.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Init;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import ke.co.technovation.constants.EmailTestStatus;
import ke.co.technovation.ejb.EmailConfigurationEJBI;
import ke.co.technovation.entity.EmailConfiguration;

@ManagedBean(name="emailconfigBean")
@RequestScoped
public class EmailConfigurationBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8660690208989169117L;
	private Logger logger = Logger.getLogger(getClass());
	private EmailConfiguration config;
	
	@EJB
	private EmailConfigurationEJBI emailConfigEJB;
	
	public EmailConfiguration getConfig() {
		return config;
	}
	public void setConfig(EmailConfiguration config) {
		this.config = config;
	}
	
	@Init
	@PostConstruct
	public void init() {
		logger.info("\n\n\n FIRST--> "+config+"  \n\n");
		if(config==null)
			try {
				config = emailConfigEJB.getTheFirstConfig();
			} catch (Exception e) {
				try{
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Problem occurred while fetching configuration");
					FacesContext.getCurrentInstance().addMessage("emailConfigForm:saveconmailconfig", msg);
			
					logger.error(e.getMessage(), e);
				}catch(Exception e1){
					logger.error(e1.getMessage(), e1);
				}
			}
		if(config==null)
			config = new EmailConfiguration();
	}
	
	public void save(){
		
		
		
		try {
			
			config = emailConfigEJB.save(config);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Configuration saved successfully");
			FacesContext.getCurrentInstance().addMessage("emailConfigForm:saveconmailconfig", msg);
	
		} catch (Exception e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Problem occurred while saving configuration");
			FacesContext.getCurrentInstance().addMessage("emailConfigForm:saveconmailconfig", msg);
	
			logger.error(e.getMessage(), e);
		}
		
	}
	
	
	public void testConfig(){
		
		logger.info("\n\n\n "+config+"  \n\n");
		
		config.setReceiver_test_email("timomwa@gmail.com");
		EmailTestStatus status = emailConfigEJB.testConfig(config);
		
		String message = "Configuration is OK!";
		Severity severity = FacesMessage.SEVERITY_INFO;
		
		if(status==EmailTestStatus.SUCCESS){
			
			try {
				config = emailConfigEJB.save(config);
			} catch (Exception e) { 
				logger.error(e.getMessage(), e);
			}
	
		}else if(status==EmailTestStatus.AUTHENTICATION_FAILURE){
			message = "Invalid Credentials! Please supply correct mailer credentials. If you're usig Gmail's SMTP server, please enable insecure apps for the email address '"+
					config.getEmail_username() +"' by following these instructions ->  https://support.google.com/accounts/answer/6010255?hl=en";
			severity = FacesMessage.SEVERITY_WARN;
		}else if(status==EmailTestStatus.NETWORK_FAILURE){
			message = "Connection failed! Check your network connection";
			severity = FacesMessage.SEVERITY_FATAL;
		}else if(status==EmailTestStatus.APPLICATION_FAILURE){
			message = "Connection failed! Check your network connection";
			severity = FacesMessage.SEVERITY_FATAL;
		}
		

		
		FacesMessage msg = new FacesMessage(severity, "", message);
		FacesContext.getCurrentInstance().addMessage("emailConfigForm:saveconmailconfig", msg);
	}
	
}
