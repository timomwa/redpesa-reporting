package ke.co.technovation.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import ke.co.technovation.dao.OutflowStatsDAOI;

@Stateless
public class OutflowStatsEJBImpl implements OutflowStatsEJBI {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Inject
	private OutflowStatsDAOI outflowStatsDAO;
	
	@Override
	public String getWeeklyStats(){
		return outflowStatsDAO.getWeeklyStats();
	}
	
	
	@Override
	public String getDayilyStats(){
		return outflowStatsDAO.getDayilyStats();
	}

}
