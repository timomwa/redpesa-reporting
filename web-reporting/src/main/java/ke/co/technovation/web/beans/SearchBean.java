package ke.co.technovation.web.beans;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

@ManagedBean
@RequestScoped
public class SearchBean  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3038998698808463L;

	private Logger logger = Logger.getLogger(getClass());
	
	private String msisdn;
	private Date datefrom;
	private Date dateto;

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public Date getDatefrom() {
		return datefrom;
	}

	public void setDatefrom(Date datefrom) {
		this.datefrom = datefrom;
	}

	public Date getDateto() {
		return dateto;
	}

	public void setDateto(Date dateto) {
		this.dateto = dateto;
	}

	public void search(){
		logger.info("\n\n msisdn-> "+msisdn+"\n\n");
		logger.info("\n\n datefrom-> "+datefrom+"\n\n");
		logger.info("\n\n dateto-> "+dateto+"\n\n");
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Search complete. msisdn-> "+msisdn);
        FacesContext.getCurrentInstance().addMessage("searchForm:searchbtn", msg);
	}

}
