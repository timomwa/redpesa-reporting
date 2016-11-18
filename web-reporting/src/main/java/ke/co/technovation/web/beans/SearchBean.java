package ke.co.technovation.web.beans;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Init;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.omnifaces.util.Faces;

import ke.co.technovation.dto.MsisdnTransactionsDTO;
import ke.co.technovation.dto.QueryDTO;
import ke.co.technovation.ejb.PerMsisdnTransactionCountEJBI;
import ke.co.technovation.ejb.ReportSampleEJBI;

@ManagedBean
@RequestScoped
public class SearchBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3038998698808463L;

	private Logger logger = Logger.getLogger(getClass());
	private List<MsisdnTransactionsDTO> transactions;

	@EJB
	private PerMsisdnTransactionCountEJBI perMsisdnTxCountEJB;
	
	@EJB
	private ReportSampleEJBI reportSampleEJBI;

	private QueryDTO queryDTO;

	@Init
	@PostConstruct
	public void init() {
		queryDTO = new QueryDTO();
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public List<MsisdnTransactionsDTO> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<MsisdnTransactionsDTO> transactions) {
		this.transactions = transactions;
	}

	public QueryDTO getQueryDTO() {
		return queryDTO;
	}

	public void setQueryDTO(QueryDTO queryDTO) {
		this.queryDTO = queryDTO;
	}

	public void downloadReport() throws Exception {
		logger.info("BEGIN...");
		
		if (queryDTO.getMsisdn() == null || queryDTO.getMsisdn().trim().isEmpty()) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Please provide msisdn");
			FacesContext.getCurrentInstance().addMessage("searchForm:searchbtn", msg);
		}else{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Download will start shortly...");
			FacesContext.getCurrentInstance().addMessage("searchForm:searchbtn", msg);
			String filename = queryDTO.getMsisdn()+"_"+"Transactions"+".pdf";
			Faces.sendFile(perMsisdnTxCountEJB.generatePDF(queryDTO).toByteArray(), filename, true);
		}
		
	    logger.info("END...");
	}

	public void search() {

		if (queryDTO.getMsisdn() == null || queryDTO.getMsisdn().trim().isEmpty()) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Please provide msisdn");
			FacesContext.getCurrentInstance().addMessage("searchForm:searchbtn", msg);
		} else {
			logger.info("\n\n queryDTO-> " + queryDTO + "\n\n");
			transactions = perMsisdnTxCountEJB.getTransactions(queryDTO);
			logger.info("\n\n size -> " + transactions.size() + "\n\n");
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Found " + transactions.size()
					+ " record" + (transactions.size() == 0 || transactions.size() > 1 ? "s" : ""));
			FacesContext.getCurrentInstance().addMessage("searchForm:searchbtn", msg);
		}
	}

}
