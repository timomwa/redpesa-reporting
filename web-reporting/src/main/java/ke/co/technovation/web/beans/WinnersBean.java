package ke.co.technovation.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Init;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import co.ke.technovation.entity.PaymentStatus;
import ke.co.technovation.dto.QueryDTO;
import ke.co.technovation.ejb.RedCrossWinnerEJBI;
import ke.co.technovation.ejb.WinnersEJBI;
import ke.co.technovation.entity.RedCrossWinner;

@ManagedBean(name="winnersBean")
@SessionScoped
public class WinnersBean implements Serializable {
	
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * 
	 */
	private static final long serialVersionUID = -9196698594218414463L;
	private List<RedCrossWinner> winners = new ArrayList<RedCrossWinner>();
	private QueryDTO queryDTO;
	
	@EJB
	private WinnersEJBI winnersEJB;
	
	@EJB
	private RedCrossWinnerEJBI redcrossWinnerEJBI;

	@Init
	@PostConstruct
	public void init() {
		queryDTO = new QueryDTO();
		winners = winnersEJB.listWinnersToday();
	}

	public List<RedCrossWinner> getWinners() {
		return winners;
	}

	public void setWinners(List<RedCrossWinner> winners) {
		this.winners = winners;
	}

	public QueryDTO getQueryDTO() {
		return queryDTO;
	}

	public void setQueryDTO(QueryDTO queryDTO) {
		this.queryDTO = queryDTO;
	}
	
	public void search(){
		logger.info("\n\n "+queryDTO+" \n");
		if((queryDTO.getSearchString()==null || queryDTO.getSearchString().isEmpty()) && queryDTO.getDrawNumber()==null){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Please provide Msisdn / Name / TxId / Winning Ticket, or the Draw number.");
			FacesContext.getCurrentInstance().addMessage("winnersForm:searchbtn", msg);
		}else{
			winners = winnersEJB.search(queryDTO);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Fetched "+winners.size()+" record"+(winners.size()>1 ? "s":"")+"");
			FacesContext.getCurrentInstance().addMessage("winnersForm:searchbtn", msg);
		}
	}
	
	
	public String getWinningStatus(int code){
		return PaymentStatus.get(code).name();
	}
	
	
	public void pay(RedCrossWinner winner){
		//Gets the latest status
		logger.info(" \n\n AT PAy method ");
		winner = redcrossWinnerEJBI.findWinnerById( winner.getId() );
		if(redcrossWinnerEJBI.winnerSuccessfullyPaid(winner)){
			logger.info(" \n\n AT PAy method : "+"The winning ticket \""+winner.getTicket_number()+"\" already has a successful payment done. Cannot pay more than once!");
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "The winning ticket \""+winner.getTicket_number()+"\" already has a successful payment done. Cannot pay more than once!");
			FacesContext.getCurrentInstance().addMessage("winnersForm:searchbtn", msg);
		}else{
			boolean success = winnersEJB.markForPayment(winner); 
			logger.info("\n\n success-> "+success);
			if(success){
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Payment in payment queue");
				FacesContext.getCurrentInstance().addMessage("winnersForm:searchbtn", msg);
			}else{
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Could not enqueue for payment. Try again later, if problem is persistent, contact tech support.");
				FacesContext.getCurrentInstance().addMessage("winnersForm:searchbtn", msg);
			}
		}
	}

}
