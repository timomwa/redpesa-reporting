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

public class InflowTransactionStatsDAOImpl implements InflowTransactionStatsDAOI {
	
	@PersistenceContext(unitName=AppPropertyHolder.REDPESA_PU)
	private EntityManager entitiManager;
	
	private Logger logger = Logger.getLogger(getClass());
	private SimpleDateFormat formatDayOfMonth  = new SimpleDateFormat("d");
	
	@SuppressWarnings("unchecked")
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
			
			Query qry = entitiManager.createQuery("select count(*), date(transTime) from MpesaIn group by date(transTime) order by date(transTime) asc");
			List<Object[]> rows = qry.getResultList();
			if(rows!=null){
				BigDecimal day_count = BigDecimal.ZERO;
				BigDecimal total_trx = BigDecimal.ZERO;
				BigDecimal daily_average_payouts = BigDecimal.ZERO;
				
				for(Object[] row : rows){
					day_count = day_count.add(BigDecimal.ONE);
					Long trx_count = (Long) row[0];
					total_trx = total_trx.add(BigDecimal.valueOf(trx_count));
					daily_average_payouts = total_trx.divide(day_count, 2, RoundingMode.HALF_EVEN);
					Date date = (Date) row[1];
					labels.put( convertToPrettyFormat(date) );
					dailyPayout.put(  trx_count.doubleValue()  );
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

	@SuppressWarnings("unchecked")
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
			
			Query qry = entitiManager.createNativeQuery("select week(transTime)-43 wk, sum(transAmount) amt from mpesa_in group by  week(transTime) having wk>0 order by week(transTime) asc");
			List<Object[]> rows = qry.getResultList();
			if(rows!=null){
				BigDecimal week_count = BigDecimal.ZERO;
				BigDecimal total_pay_ins = BigDecimal.ZERO;
				BigDecimal weekly_average_trx = BigDecimal.ZERO;
				
				for(Object[] row : rows){
					week_count = week_count.add(BigDecimal.ONE);
					BigDecimal outflow = (BigDecimal) row[1];
					total_pay_ins = total_pay_ins.add(outflow);
					weekly_average_trx = total_pay_ins.divide(week_count, 2, RoundingMode.HALF_EVEN);
					Integer week = (Integer) row[0];
					labels.put( week.intValue() );
					dailyPayout.put(  outflow.doubleValue()  );
					dailyAverage.put( weekly_average_trx.doubleValue()  );
				}
			}
			
			data0.put("data", dailyAverage);//Weekly average Trx
			data1.put("data", dailyPayout);//Weekly Trx
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
