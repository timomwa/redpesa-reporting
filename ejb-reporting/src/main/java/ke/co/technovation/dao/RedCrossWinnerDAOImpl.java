package ke.co.technovation.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import co.ke.technovation.entity.PaymentStatus;
import ke.co.technovation.entity.RedCrossWinner;
import ke.co.technovation.constants.AppPropertyHolder;
import ke.co.technovation.dto.QueryDTO;

public class RedCrossWinnerDAOImpl extends GenericDAOImpl<RedCrossWinner, Integer> implements RedCrossWinnerDAOI {

	private Logger logger  = Logger.getLogger(getClass());
	
	@PersistenceContext(unitName=AppPropertyHolder.REDCROSS_PU)
	protected EntityManager redcrossem;
	
	@Override
	public RedCrossWinner findBy(String fieldName, Object value) {
		
		RedCrossWinner winner = null;
		
		try{
			Query qry = redcrossem.createQuery("from RedCrossWinner WHERE "+fieldName+" = :"+fieldName);
			qry.setParameter(fieldName, value);
			qry.setMaxResults(1);
			winner = (RedCrossWinner) qry.getSingleResult();
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return winner;
	}
	
	@Override
	public RedCrossWinner save(RedCrossWinner entity) throws Exception {
		return redcrossem.merge(entity);
	}	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<RedCrossWinner> getUnpaidWinners(int limit){
		
		List<RedCrossWinner> winners = new ArrayList<RedCrossWinner>();
		
		try{
			Query qry = redcrossem.createQuery("from RedCrossWinner WHERE is_verified=:is_verified AND is_processed=:is_processed AND payment_status=:payment_status");
			qry.setParameter("is_verified", Boolean.TRUE);
			qry.setParameter("is_processed", Boolean.TRUE);
			qry.setParameter("payment_status", PaymentStatus.JUST_IN.getCode());
			qry.setMaxResults(limit);
			winners = qry.getResultList();
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return winners;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<RedCrossWinner> listWinnersByDateWon(Date date){
		
		List<RedCrossWinner> winners = new ArrayList<RedCrossWinner>();
		
		try{
			Query qry = redcrossem.createQuery("from RedCrossWinner WHERE price_won>:price_won AND is_verified=:is_verified AND date_created=:date_created");
			qry.setParameter("is_verified", Boolean.TRUE);
			qry.setParameter("date_created", date);
			qry.setParameter("price_won", BigDecimal.ZERO);
			winners = qry.getResultList();
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return winners;
		
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<RedCrossWinner> search(QueryDTO queryDTO){
	
		List<RedCrossWinner> winners = new ArrayList<RedCrossWinner>();
		
		try{
			String query = "from RedCrossWinner WHERE price_won>:price_won AND is_verified=:is_verified";
			if(queryDTO.getDatefrom()!=null && queryDTO.getDateto()!=null){
				query = query+" AND date(date_created) between date(:fromTime) and date(:toTime)";
			}else if(queryDTO.getDatefrom()!=null && queryDTO.getDateto()==null){
				query = query+" AND date(date_created) between date(:fromTime) and date(:toTime)";
				queryDTO.setDateto(new Date());
			}
			if(queryDTO.getDateto()!=null && queryDTO.getDatefrom()==null){
				query = query+" AND date(date_created) <= date(:thisTS) ";
			}
			
			if(queryDTO.getDrawNumber()!=null && queryDTO.getDrawNumber().compareTo(-1)<0){
				query = query + " AND draw_number=:draw_number ";
			}
			
			if(queryDTO.getSearchString()!=null && !queryDTO.getSearchString().isEmpty()){
				query = query + " AND (first_name like :nameormsisdn OR"
								 + "   last_name like :nameormsisdn OR "
								 + "   ticket_number like :nameormsisdn OR "
								 + "   phone_number like :nameormsisdn OR "
								 + "   telco_transaction_id like :nameormsisdn )";
			}
			
			Query preparedQuery = redcrossem.createQuery(query);
			preparedQuery.setParameter("is_verified", Boolean.TRUE);
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
			if(queryDTO.getDrawNumber()!=null && queryDTO.getDrawNumber().compareTo(-1)<0){
				preparedQuery.setParameter( "draw_number", queryDTO.getDrawNumber() );
			}
			if(queryDTO.getSearchString()!=null && !queryDTO.getSearchString().isEmpty()){
				preparedQuery.setParameter("nameormsisdn", "%"+queryDTO.getSearchString()+"%");
			}
			
			preparedQuery.setParameter( "price_won", BigDecimal.ZERO );
			
			winners = preparedQuery.getResultList();
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return winners;
	}
}
