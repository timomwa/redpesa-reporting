package ke.co.technovation.dao;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import ke.co.technovation.dto.MsisdnTransactionsDTO;
import ke.co.technovation.dto.QueryDTO;

public class MpesaInDAOImpl extends GenericDAOImpl<MpesaIn, Long> implements MpesaInDAOI {
	
	@PersistenceContext(unitName=AppPropertyHolder.REDPESA_PU)
	private EntityManager entitiManager;
	
	private SimpleDateFormat formatDayOfMonth  = new SimpleDateFormat("d");
	//private DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/"); 
	private Logger logger = Logger.getLogger(getClass());
	//private NumberFormat nf = NumberFormat.getInstance();//Be careful only available in Java 8
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MpesaIn> list(QueryDTO queryDTO){
		List<MpesaIn> mpesaIns = new ArrayList<MpesaIn>();
		try{
			String query = "from MpesaIn WHERE id>0 ";
			
			if(queryDTO.getDatefrom()!=null && queryDTO.getDateto()!=null){
				query = query+" AND date(transTime) between date(:fromTime) and date(:toTime)";
			}else if(queryDTO.getDatefrom()!=null && queryDTO.getDateto()==null){
				query = query+" AND date(transTime) between date(:fromTime) and date(:toTime)";
				queryDTO.setDateto(new Date());
			}
			if(queryDTO.getDateto()!=null && queryDTO.getDatefrom()==null){
				query = query+" AND date(transTime) <= date(:thisTS) ";
			}
			if(queryDTO.getMsisdn()!=null && !queryDTO.getMsisdn().isEmpty()){
				query = query + " AND receiverPartyPublicName like :nameormsisdn";
			}
							
			query = query + " order by transTime desc";
			
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
			mpesaIns = preparedQuery.getResultList();
			
		}catch(NoResultException nre){
			logger.error("Could not find result for query params -> ");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return mpesaIns;
	}
	
	
	
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<MsisdnTransactionsDTO> getTransactions(QueryDTO queryDTO) {
		
		List<MsisdnTransactionsDTO> transactions = new ArrayList<MsisdnTransactionsDTO>();
		
		try{
			String qry = "SELECT "
					+ "count(*), "
					+ "date(transTime), "
					+ "msisdn "
					+ "from MpesaIn "
					+ "WHERE "
					+ "msisdn like :msisdn";
			
			if(queryDTO.getDatefrom()!=null && queryDTO.getDateto()!=null){
				qry = qry+" AND date(transTime) between date(:fromTime) and date(:toTime)";
			}else if(queryDTO.getDatefrom()!=null && queryDTO.getDateto()==null){
				qry = qry+" AND date(transTime) between date(:fromTime) and date(:toTime)";
				queryDTO.setDateto(new Date());
			}
			if(queryDTO.getDateto()!=null && queryDTO.getDatefrom()==null){
				qry = qry+" AND date(transTime) <= date(:thisTS) ";
			}
			qry = qry + " GROUP by date(transTime) , msisdn ORDER BY date(transTime) ASC";
			
			Query preparedQuery = entitiManager.createQuery(qry);
			preparedQuery.setParameter("msisdn", "%"+queryDTO.getMsisdn()+"%");
			
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
			
			List<Object[]> mpesaRecs = preparedQuery.getResultList();
			
			if(mpesaRecs!=null && mpesaRecs.size()>0){
				for(Object[] row : mpesaRecs){
					MsisdnTransactionsDTO msisdntx = new MsisdnTransactionsDTO();
					
					msisdntx.setDate( (Date)row[1] );
					msisdntx.setMsisdn( (String) row[2] );
					msisdntx.setTransactioncount( ((Long)row[0])  );
					transactions.add(msisdntx);
				}
			}
		
		}catch(NoResultException nre){
			logger.error("Could not find result for query params -> "+queryDTO);
		}
		return transactions;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String getaverageDailyRevenue(){
		
		String stringrepresentation_of_resultset = "";
		
		JSONObject mainObject = new JSONObject();
		
		try{
			Query qry = entitiManager.createQuery("select sum(m.transAmount), date(m.timeStamp) "
					+ "FROM MpesaIn m GROUP by date(m.timeStamp) ORDER BY date(m.timeStamp) ASC");
			List<Object[]> rows = qry.getResultList();
			
			JSONObject data0 = new JSONObject();
			JSONObject data1 = new JSONObject();
			JSONArray labels =  new JSONArray();
			JSONArray dataArray0 = new JSONArray();
			JSONArray dataArray1 = new JSONArray();
			JSONArray datasets =  new JSONArray();
			
			if(rows!=null){
				BigDecimal acc_rev = BigDecimal.ZERO;
				BigDecimal count = BigDecimal.ZERO;
				BigDecimal average_daily_revenue = BigDecimal.ZERO;
				for(Object[] row : rows){
					count = count.add(BigDecimal.ONE);
					BigDecimal transAmount = (BigDecimal) row[0];
					Date date = (Date) row[1];
					//Integer hour = (Integer) row[1];
					acc_rev = acc_rev.add(transAmount);
					
					average_daily_revenue = acc_rev.divide(count, 2, BigDecimal.ROUND_HALF_EVEN);
					
					//String avdaily_rev_str = nf.format(  acc_rev.doubleValue() );
					labels.put( convertToPrettyFormat(date) );
					dataArray0.put( average_daily_revenue.doubleValue() );
					dataArray1.put( transAmount );
				}
			}
		
			data0.put("data", dataArray0);//Daily average
			data1.put("data", dataArray1);//Daily Revenue
			data0.put("fillColor", "rgba(151,187,205,1)");
			data0.put("strokeColor", "rgba(151,187,205,0.8)");
			data0.put("highlightFill", "rgba(151,187,205,0.75)");
			data0.put("highlightStroke", "rgba(151,187,205,1)");
			datasets.put(data0);
			datasets.put(data1);
			
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
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String gettotalRevenueToDate(){
		
		String stringrepresentation_of_resultset = "";
		
		JSONObject mainObject = new JSONObject();
		
		try{
			Query qry = entitiManager.createQuery("select sum(m.transAmount), date(m.timeStamp) "
					+ "FROM MpesaIn m GROUP by date(m.timeStamp) ORDER BY date(m.timeStamp) ASC");
			List<Object[]> rows = qry.getResultList();
			
			JSONObject data = new JSONObject();
			JSONArray labels =  new JSONArray();
			JSONArray dataArray = new JSONArray();
			JSONArray datasets =  new JSONArray();
			
			if(rows!=null){
				BigDecimal acc_rev = BigDecimal.ZERO;
				for(Object[] row : rows){
					BigDecimal transAmount = (BigDecimal) row[0];
					Date date = (Date) row[1];
					//Integer hour = (Integer) row[1];
					acc_rev = acc_rev.add(transAmount);
					//String accRev = nf.format(  acc_rev.doubleValue() );
					labels.put( convertToPrettyFormat(date) );
					dataArray.put( acc_rev.doubleValue() );
				}
			}
		
			data.put("data", dataArray);
			data.put("fillColor", "rgba(151,187,205,1)");
			data.put("strokeColor", "rgba(151,187,205,0.8)");
			data.put("highlightFill", "rgba(151,187,205,0.75)");
			data.put("highlightStroke", "rgba(151,187,205,1)");
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
	
	@SuppressWarnings("unchecked")
	@Override
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
			JSONArray labels =  new JSONArray();
			JSONArray dataArray = new JSONArray();
			JSONArray datasets =  new JSONArray();
			
			if(rows!=null)
				for(Object[] row : rows){
					BigDecimal transAmount = (BigDecimal) row[0];
					Integer hour = (Integer) row[1];
					//String accRev = nf.format(  transAmount.doubleValue() );
					labels.put(hour.intValue());
					dataArray.put( transAmount.doubleValue() );
				}
		
			data.put("data", dataArray);
			data.put("fillColor", "rgba(151,187,205,1)");
			data.put("strokeColor", "rgba(151,187,205,0.8)");
			data.put("highlightFill", "rgba(151,187,205,0.75)");
			data.put("highlightStroke", "rgba(151,187,205,1)");
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
