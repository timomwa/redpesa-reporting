package ke.co.technovation.ejb;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.List;

import org.apache.log4j.Logger;

import co.ke.technovation.entity.PaymentStatus;
import ke.co.technovation.dto.QueryDTO;
import ke.co.technovation.entity.RedCrossWinner;

@Stateless
@Remote(WinnersEJBI.class)
public class WinnersEJBImpl implements WinnersEJBI {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@EJB
	private RedCrossWinnerEJBI redcrossWinnerEJBI;
	
	@Override
	public List<RedCrossWinner> listWinnersToday(){
		return redcrossWinnerEJBI.listTodayWinners();
	}
	
	
	@Override
	public List<RedCrossWinner> search(QueryDTO queryDTO){
		return redcrossWinnerEJBI.search(queryDTO);
	}
	
	@Override
	public boolean markForPayment(RedCrossWinner winner){
		boolean success = false;
		if(!redcrossWinnerEJBI.winnerSuccessfullyPaid(winner)){
			winner.setPayment_status(PaymentStatus.JUST_IN.getCode());
			try {
				redcrossWinnerEJBI.save(winner);
				success = true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return success;
	}
	

}
