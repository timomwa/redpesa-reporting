package ke.co.technovation.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import ke.co.technovation.constants.AppPropertyHolder;

public class OutflowStatsDAOImpl implements OutflowStatsDAOI {
	
	@PersistenceContext(unitName=AppPropertyHolder.REDPESA_PU)
	private EntityManager entitiManager;
	
	private Logger logger = Logger.getLogger(getClass());
	private SimpleDateFormat formatDayOfMonth  = new SimpleDateFormat("d");
	
	@Override
	public String getWeeklyStats(){
		String resp = "";
		
		try{
			
			JSONObject rootObject = new JSONObject();
			JSONArray datasets =  new JSONArray();
			
			JSONObject data0 = new JSONObject();
			JSONObject data1 = new JSONObject();
			JSONArray labels = new JSONArray();
			JSONArray dailyPayout = new JSONArray();
			JSONArray dailyAverage = new JSONArray();
			
			Query qry = entitiManager.createNativeQuery("select week(transactionCompletedDateTime)-43 wk, sum(transactionAmount) amt from mpesa_out where resultCode=0 and status=0 and resultType=0 group by  week(transactionCompletedDateTime) order by week(transactionCompletedDateTime) asc");
			List<Object[]> rows = qry.getResultList();
			if(rows!=null){
				BigDecimal week_count = BigDecimal.ZERO;
				BigDecimal total_payouts = BigDecimal.ZERO;
				BigDecimal weekly_average_payouts = BigDecimal.ZERO;
				
				for(Object[] row : rows){
					week_count = week_count.add(BigDecimal.ONE);
					BigDecimal outflow = (BigDecimal) row[1];
					total_payouts = total_payouts.add(outflow);
					weekly_average_payouts = total_payouts.divide(week_count, 2, RoundingMode.HALF_EVEN);
					Integer week = (Integer) row[0];
					labels.put( week.intValue() );
					dailyPayout.put(  outflow.doubleValue()  );
					dailyAverage.put( weekly_average_payouts.doubleValue()  );
				}
			}
			
			data0.put("data", dailyAverage);//Weekly average Payout
			data1.put("data", dailyPayout);//Weekly Payout
			datasets.put(data0);
			datasets.put(data1);
			
			rootObject.put("datasets", datasets);
			rootObject.put("labels", labels);
			
			resp = rootObject.toString();
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		return resp;
	}

	@SuppressWarnings({"unchecked" })
	@Override
	public String getDayilyStats(){
		String resp = "";
		
		try{
			
			JSONObject rootObject = new JSONObject();
			JSONArray datasets =  new JSONArray();
			
			JSONObject data0 = new JSONObject();
			JSONObject data1 = new JSONObject();
			JSONArray labels = new JSONArray();
			JSONArray dailyPayout = new JSONArray();
			JSONArray dailyAverage = new JSONArray();
			
			Query qry = entitiManager.createQuery("select sum(transactionAmount), date(transactionCompletedDateTime) from MpesaOut where resultCode=0 and status=0 and resultType=0 group by date(transactionCompletedDateTime) order by date(transactionCompletedDateTime) asc");
			List<Object[]> rows = qry.getResultList();
			if(rows!=null){
				BigDecimal day_count = BigDecimal.ZERO;
				BigDecimal total_payouts = BigDecimal.ZERO;
				BigDecimal daily_average_payouts = BigDecimal.ZERO;
				
				for(Object[] row : rows){
					day_count = day_count.add(BigDecimal.ONE);
					BigDecimal outflow = (BigDecimal) row[0];
					total_payouts = total_payouts.add(outflow);
					daily_average_payouts = total_payouts.divide(day_count, 2, RoundingMode.HALF_EVEN);
					Date date = (Date) row[1];
					labels.put( convertToPrettyFormat(date) );
					dailyPayout.put(  outflow.doubleValue()  );
					dailyAverage.put( daily_average_payouts.doubleValue()  );
				}
			}
			
			data0.put("data", dailyAverage);//Daily average
			data1.put("data", dailyPayout);//Daily Payout
			datasets.put(data0);
			datasets.put(data1);
			
			rootObject.put("datasets", datasets);
			rootObject.put("labels", labels);
			
			resp = rootObject.toString();
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		return resp;
	}

	
	
	
	public String convertToPrettyFormat(Date date){
		int day = Integer.parseInt(formatDayOfMonth.format(date));
		String suff  = getDayNumberSuffix(day);
		DateFormat prettier_df = new SimpleDateFormat("d'"+suff+"' MMM");
	    return prettier_df.format(date);
	}
	
	
	public static String getDayNumberSuffix(int day) {
	    if (day >= 11 && day <= 13) {
	        return "th";
	    }
	    switch (day % 10) {
	    case 1:
	        return "st";
	    case 2:
	        return "nd";
	    case 3:
	        return "rd";
	    default:
	        return "th";
	    }
	}

	
}
