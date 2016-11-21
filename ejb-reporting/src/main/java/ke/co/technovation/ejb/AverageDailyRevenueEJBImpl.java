package ke.co.technovation.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import ke.co.technovation.dao.MpesaInDAOI;

@Stateless
public class AverageDailyRevenueEJBImpl implements AverageDailyRevenueEJBI {

	private Logger logger = Logger.getLogger(getClass());
	
	@Inject
	private MpesaInDAOI mpesaDAOI;
	
	@Override
	public String getaverageDailyRevenue(){
		return mpesaDAOI.getaverageDailyRevenue();
	}
}
