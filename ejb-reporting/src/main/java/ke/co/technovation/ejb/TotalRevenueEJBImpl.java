package ke.co.technovation.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import ke.co.technovation.dao.MpesaInDAOI;

@Stateless
public class TotalRevenueEJBImpl implements TotalRevenueEJBI {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Inject
	private MpesaInDAOI mpesaDAOI;
	
	
	public String getStats(){
		return mpesaDAOI.gettotalRevenueToDate();
	}
	

}
