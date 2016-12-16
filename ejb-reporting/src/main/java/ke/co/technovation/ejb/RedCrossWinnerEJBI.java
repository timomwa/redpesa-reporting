package ke.co.technovation.ejb;

import java.util.List;

import co.ke.technovation.entity.MpesaOut;
import ke.co.technovation.dto.QueryDTO;
import ke.co.technovation.entity.RedCrossWinner;


public interface RedCrossWinnerEJBI {
	
	public RedCrossWinner save(RedCrossWinner winner) throws Exception ;
	
	public List<RedCrossWinner> getWinners(int limit);
	
	public boolean updatePaymentStatus(MpesaOut mpesaOut);
	
	public boolean winnerSuccessfullyPaid(RedCrossWinner winner);

	public List<RedCrossWinner> listTodayWinners();

	public List<RedCrossWinner> search(QueryDTO queryDTO);
	
	public RedCrossWinner findWinnerById(int id);

}
