package ke.co.technovation.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import co.ke.technovation.entity.MpesaOut;
import ke.co.technovation.constants.AppPropertyHolder;
import ke.co.technovation.dto.QueryDTO;

public class MpesaOutDAOImpl implements MpesaOutDAOI {

	@PersistenceContext(unitName=AppPropertyHolder.REDPESA_PU)
	private EntityManager entitiManager;
	
	private Logger logger = Logger.getLogger(getClass());
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MpesaOut> list(QueryDTO queryDTO){
		
		List<MpesaOut> mpesaOuts = new ArrayList<MpesaOut>();
		
		try{
			String query = "from MpesaOut WHERE id>0 ";
			
			if(queryDTO.getDatefrom()!=null && queryDTO.getDateto()!=null){
				query = query+" AND date(transactionCompletedDateTime) between date(:fromTime) and date(:toTime)";
			}else if(queryDTO.getDatefrom()!=null && queryDTO.getDateto()==null){
				query = query+" AND date(transactionCompletedDateTime) between date(:fromTime) and date(:toTime)";
				queryDTO.setDateto(new Date());
			}
			if(queryDTO.getDateto()!=null && queryDTO.getDatefrom()==null){
				query = query+" AND date(transactionCompletedDateTime) <= date(:thisTS) ";
			}
			
			if(queryDTO.getMsisdn()!=null && !queryDTO.getMsisdn().isEmpty()){
				query = query + " AND (receiverPartyPublicName like :nameormsisdn OR conversationID like :nameormsisdn OR transId like :nameormsisdn OR transactionReceipt like :nameormsisdn)";
			}
							
			query = query + " order by transactionCompletedDateTime desc";
			
			Query preparedQuery = entitiManager.createQuery(query);
			if(queryDTO.getDateto()!=null && queryDTO.getDatefrom()==null){
				preparedQuery.setParameter("thisTS", queryDTO.getDateto());
			}else if(queryDTO.getDatefrom()!=null && queryDTO.getDateto()!=null){
				preparedQuery.setParameter("fromTime", queryDTO.getDatefrom());
				preparedQuery.setParameter("toTime", queryDTO.getDateto());
			}
			if(queryDTO.getLimit()!=null && queryDTO.getLimit().compareTo(0)>0)
				preparedQuery.setMaxResults(queryDTO.getLimit().intValue());
			if(queryDTO.getStart()!=null && queryDTO.getStart().compareTo(-1)>0)
				preparedQuery.setFirstResult(queryDTO.getStart().intValue());
			if(queryDTO.getMsisdn()!=null && !queryDTO.getMsisdn().isEmpty()){
				preparedQuery.setParameter("nameormsisdn", "%"+queryDTO.getMsisdn()+"%");
			}
			
			mpesaOuts = preparedQuery.getResultList();
			
		}catch(NoResultException nre){
			logger.error("Could not find result for query params -> ");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return mpesaOuts;
	}

}
