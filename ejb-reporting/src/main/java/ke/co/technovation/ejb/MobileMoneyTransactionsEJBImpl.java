package ke.co.technovation.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;

import co.ke.technovation.entity.MpesaIn;
import co.ke.technovation.entity.MpesaOut;
import ke.co.technovation.dao.MpesaInDAOI;
import ke.co.technovation.dao.MpesaOutDAOI;
import ke.co.technovation.dto.QueryDTO;

@Stateless
public class MobileMoneyTransactionsEJBImpl implements MobileMoneyTransactionsEJBI {

	private Logger logger = Logger.getLogger(getClass());
	
	@Inject
	private MpesaInDAOI mpesaInDAOI;
	
	@Inject
	private MpesaOutDAOI mpesaOutDAOI;
	
	
	@Override
	public List<MpesaOut> getOuts(QueryDTO queryDTO){
		List<MpesaOut> mpesaOuts = new ArrayList<MpesaOut>();
		try{
			mpesaOuts = mpesaOutDAOI.list(queryDTO);
		}catch(NoResultException e){
			logger.warn("Could not get any records.");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return mpesaOuts;
	}
	
	
	@Override
	public List<MpesaIn> getIns(QueryDTO queryDTO){
		List<MpesaIn> mpesaIns = new ArrayList<MpesaIn>();
		try{
			mpesaIns = mpesaInDAOI.list(queryDTO);
		}catch(NoResultException e){
			logger.warn("Could not get any records.");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return mpesaIns;
	}
	
}
