package ke.co.technovation.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
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
			Query qry = entitiManager.createNativeQuery("select sum(transAmount) amt , week(transTime)-43 wk from mpesa_in  group by week(transTime) having wk>= 0");
			List<Object[]> rows = qry.getResultList();
			
			JSONObject data = new JSONObject();
			JSONObject weeklyAverages = new JSONObject();
			JSONArray labels =  new JSONArray();
			JSONArray dataArray = new JSONArray();
			JSONArray weeklyAverageArray = new JSONArray();
			JSONArray datasets =  new JSONArray();
			JSONArray avaragesArray =  new JSONArray();
			
			if(rows!=null){
				BigDecimal week_count = BigDecimal.ZERO;
				BigDecimal cumulative_rev = BigDecimal.ZERO;
				BigDecimal weekly_average = BigDecimal.ZERO;
				for(Object[] row : rows){
					week_count = week_count.add(BigDecimal.ONE);
					BigDecimal transAmount = (BigDecimal) row[0];
					cumulative_rev = cumulative_rev.add(transAmount);
					weekly_average = cumulative_rev.divide(week_count, 2, RoundingMode.HALF_EVEN);
					
					Integer week = (Integer) row[1];
					//String accRev = nf.format(  transAmount.doubleValue() );
					
					labels.put(week.intValue());
					weeklyAverageArray.put(weekly_average.doubleValue());
					dataArray.put( transAmount.doubleValue() );
				}
			}
			
			data.put("data", dataArray);
			weeklyAverages.put("data", weeklyAverageArray);
			datasets.put(weeklyAverages);
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
