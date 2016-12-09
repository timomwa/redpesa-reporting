package ke.co.technovation.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import ke.co.technovation.dao.InflowTransactionStatsDAOI;

@Stateless
public class InflowTransactionStatsEJBImpl implements InflowTransactionStatsEJBI {

	private Logger logger = Logger.getLogger(getClass());
	
	@Inject
	private InflowTransactionStatsDAOI inflowTrxStatsDAO;
	
	@Override
	public String getDayilyStats(){
		return inflowTrxStatsDAO.getDayilyStats();
	}
	
	@Override
	public String getWeeklyStats(){
		return inflowTrxStatsDAO.getWeeklyStats();
	}
}
