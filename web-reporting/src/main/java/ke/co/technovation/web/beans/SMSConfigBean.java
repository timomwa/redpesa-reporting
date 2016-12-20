package ke.co.technovation.web.beans;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Init;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import ke.co.technovation.dto.QueryDTO;
import ke.co.technovation.ejb.OutgoingSMSEJBI;
import ke.co.technovation.ejb.SMSConfigEJBI;
import ke.co.technovation.entity.SMS;
import ke.co.technovation.entity.SMSConfig;
import ke.co.technovation.httputils.GenericHttpResp;

@ManagedBean(name="smsconfigBean")
@RequestScoped
public class SMSConfigBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 606675393330699562L;
	private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(getClass());
	
	private SMSConfig smsconfig;
	
	private QueryDTO queryDTO;
	
	@EJB
	private SMSConfigEJBI smsconfigEJB;
	
	@EJB
	private OutgoingSMSEJBI outgoingSMSEJB;

	@Init
	@PostConstruct
	public void init() {
		queryDTO = new QueryDTO();
		smsconfig = smsconfigEJB.getFirstConfig();
		if(smsconfig==null)
			smsconfig = new SMSConfig();
	}
	
	public void test(){
		SMS sms = new SMS();
		sms.setMsisdn("254720988636");
		sms.setSms("Test SMS from Web app Date: "+(new Date()));
		
		GenericHttpResp response = null;
		try{
			response = outgoingSMSEJB.sendSMS(sms);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage());
			FacesContext.getCurrentInstance().addMessage("smsConfigForm:testsmsConfig", msg);
	
		}
		if(response!=null && response.getResp_code()==201){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Configuration is OK!");
			FacesContext.getCurrentInstance().addMessage("smsConfigForm:testsmsConfig", msg);
		
		}else{
			if(response==null){
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Failed!");
				FacesContext.getCurrentInstance().addMessage("smsConfigForm:testsmsConfig", msg);
			}else{
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "", response.getBody());
				FacesContext.getCurrentInstance().addMessage("smsConfigForm:testsmsConfig", msg);
				
			}
			
		}
		
	}
	
	
	public void save(){
		try {
			smsconfig = smsconfigEJB.save(smsconfig);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Save successful");
			FacesContext.getCurrentInstance().addMessage("smsConfigForm:savesmsConfig", msg);
	
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Failed to save. Try again later");
			FacesContext.getCurrentInstance().addMessage("smsConfigForm:savesmsConfig", msg);
	
		}
	}

	public SMSConfig getSmsconfig() {
		return smsconfig;
	}

	public void setSmsconfig(SMSConfig smsconfig) {
		this.smsconfig = smsconfig;
	}

	public QueryDTO getQueryDTO() {
		return queryDTO;
	}

	public void setQueryDTO(QueryDTO queryDTO) {
		this.queryDTO = queryDTO;
	}
	
	

}
