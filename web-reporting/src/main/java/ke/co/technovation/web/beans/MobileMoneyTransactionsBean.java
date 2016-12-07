package ke.co.technovation.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Init;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import co.ke.technovation.entity.MpesaIn;
import co.ke.technovation.entity.MpesaOut;
import ke.co.technovation.dto.QueryDTO;
import ke.co.technovation.ejb.MobileMoneyTransactionsEJBI;

@ManagedBean(name="mobilemoneyTransBean")
@RequestScoped
public class MobileMoneyTransactionsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5287416926523209111L;
	private Logger logger = Logger.getLogger(getClass());
	private List<MpesaIn> mpesaIns;
	private List<MpesaOut> mpesaOuts;
	
	@EJB
	private MobileMoneyTransactionsEJBI transactionsEJB;
	
	
	private QueryDTO queryDTO;
	
	@Init
	@PostConstruct
	public void init() {
		queryDTO = new QueryDTO();
		queryDTO.setLimit(100);
		
		mpesaIns = transactionsEJB.getIns(queryDTO);
		mpesaOuts = transactionsEJB.getOuts(queryDTO);
	}

	public void downloadReport(){
		logger.info("AT downloadReport ...");
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Download function not implemented yet. Check daily report on email");
		FacesContext.getCurrentInstance().addMessage("searchForm:searchbtn", msg);
	}
	
	
	public void searchIns(){
		logger.info("AT searchIns ..."+queryDTO);
		queryDTO.setLimit(1000);
		mpesaIns = transactionsEJB.getIns(queryDTO);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Fetched "+mpesaIns.size()+" record"+(mpesaIns.size()>1 ? "s":"")+"");
		FacesContext.getCurrentInstance().addMessage("searchForm:searchbtn", msg);
	}
	
	public void searchOuts(){
		logger.info("AT searchOuts ..."+queryDTO);
		queryDTO.setLimit(1000);
		mpesaOuts = transactionsEJB.getOuts(queryDTO);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Fetched "+mpesaOuts.size()+" record"+(mpesaOuts.size()>1 ? "s":"")+"");
		FacesContext.getCurrentInstance().addMessage("searchForm:searchbtn", msg);
	}
	
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public List<MpesaIn> getMpesaIns() {
		return mpesaIns;
	}

	public void setMpesaIns(List<MpesaIn> mpesaIns) {
		this.mpesaIns = mpesaIns;
	}

	public List<MpesaOut> getMpesaOuts() {
		return mpesaOuts;
	}

	public void setMpesaOuts(List<MpesaOut> mpesaOuts) {
		this.mpesaOuts = mpesaOuts;
	}

	public QueryDTO getQueryDTO() {
		return queryDTO;
	}

	public void setQueryDTO(QueryDTO queryDTO) {
		this.queryDTO = queryDTO;
	}
	
	

}
