package ke.co.technovation.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import co.ke.technovation.entity.MpesaIn;
import ke.co.technovation.constants.AppPropertyHolder;

public class WeeklyMpesaInStatsDAOImpl extends GenericDAOImpl<MpesaIn, Long> implements WeeklyMpesaInStatsDAOI {
	
	@PersistenceContext(unitName=AppPropertyHolder.REDPESA_PU)
	private EntityManager entitiManager;

	private Logger logger = Logger.getLogger(getClass());
	
	
	public String getHistoricalRecords(){
		
		String stringrepresentation_of_resultset = "";
		
		JSONObject mainObject = new JSONObject();
		
		try{
			Query qry = entitiManager.createQuery("select sum(m.transAmount), hour(m.timeStamp) "
					+ "FROM MpesaIn m WHERE hour(m.timeStamp)<=hour( :curdte ) "
					+ "AND  "
					+ "date(m.timeStamp)= date( :curdte ) "
					+ "GROUP by hour(m.timeStamp) ORDER BY hour(m.timeStamp) ASC");
			qry.setParameter("curdte", new Date());
			List<Object[]> rows = qry.getResultList();
			
			JSONObject data = new JSONObject();
			JSONObject hourlyAverages = new JSONObject();
			JSONArray labels =  new JSONArray();
			JSONArray dataArray = new JSONArray();
			JSONArray datasets =  new JSONArray();
			JSONArray avaragesArray =  new JSONArray();
			
			if(rows!=null)
				for(Object[] row : rows){
					BigDecimal transAmount = (BigDecimal) row[0];
					Integer hour = (Integer) row[1];
					//String accRev = nf.format(  transAmount.doubleValue() );
					labels.put(hour.intValue());
					dataArray.put( transAmount.doubleValue() );
				}
			qry = entitiManager.createQuery("select  distinct date(m.timeStamp) from MpesaIn m");
			List<Object> l = qry.getResultList();
			BigInteger days_running = BigInteger.valueOf( l.size() );
			
			qry = entitiManager.createQuery("select hour(timeStamp), sum(transAmount) from MpesaIn group by hour(timeStamp)");
			
			rows = qry.getResultList();
			if(rows!=null){
				for(Object[] row : rows){
					Integer hour = (Integer) row[0];
					BigDecimal total_hour_Rev = (BigDecimal) row[1];
					BigDecimal hourly_average = total_hour_Rev.divide(BigDecimal.valueOf(days_running.intValue()), 2, BigDecimal.ROUND_HALF_EVEN);
					avaragesArray.put( hourly_average.doubleValue() );
				}
			}
			data.put("data", dataArray);
			hourlyAverages.put("data", avaragesArray);
			datasets.put(hourlyAverages);
			datasets.put(data);
			
			mainObject.put("datasets", datasets);
			mainObject.put("labels", labels);
			
			stringrepresentation_of_resultset = mainObject.toString();
			
		}catch(NoResultException e){
			logger.info("No records found");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return stringrepresentation_of_resultset;
		
	}
}
