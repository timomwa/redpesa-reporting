package ke.co.technovation.dao;

import co.ke.technovation.entity.MpesaIn;

public interface MpesaInDAOI extends GenericDAOI<MpesaIn, Long> {

	public String getHistoricalRecords();

	public String gettotalRevenueToDate();

}
