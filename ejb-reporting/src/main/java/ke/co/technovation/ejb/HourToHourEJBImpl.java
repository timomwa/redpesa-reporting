package ke.co.technovation.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import ke.co.technovation.dao.MpesaInDAOI;

@Stateless
public class HourToHourEJBImpl implements HourToHourEJBI {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Inject
	private MpesaInDAOI mpesaDAOI;
	
	
	public String getStats(){
		String stats =  mpesaDAOI.getHistoricalRecords();
		return stats;
	}
	

}
