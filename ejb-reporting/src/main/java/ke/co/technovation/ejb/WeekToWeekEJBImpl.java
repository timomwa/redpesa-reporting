package ke.co.technovation.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import ke.co.technovation.dao.WeeklyMpesaInStatsDAOI;

@Stateless
public class WeekToWeekEJBImpl implements WeekToWeekEJBI{
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Inject
	private WeeklyMpesaInStatsDAOI weeklyMpesaInDAO;
	
	@Override
	public String getStats(){
		String stats =  weeklyMpesaInDAO.getHistoricalRecords();
		return stats;
	}

}
