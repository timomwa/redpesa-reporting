package ke.co.technovation.dao;

import java.util.List;

import co.ke.technovation.entity.MpesaIn;
import ke.co.technovation.dto.MsisdnTransactionsDTO;
import ke.co.technovation.dto.QueryDTO;

public interface MpesaInDAOI extends GenericDAOI<MpesaIn, Long> {

	public String getHistoricalRecords();

	public String gettotalRevenueToDate();

	public List<MsisdnTransactionsDTO> getTransactions(QueryDTO queryDTO);
	
	public String getaverageDailyRevenue();

}
