package ke.co.technovation.dao;

import co.ke.technovation.entity.MpesaIn;

public interface WeeklyMpesaInStatsDAOI extends GenericDAOI<MpesaIn, Long> {

	public String getHistoricalRecords();

}
