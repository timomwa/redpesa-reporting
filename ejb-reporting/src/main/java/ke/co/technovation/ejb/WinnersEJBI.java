package ke.co.technovation.ejb;

import java.util.List;

import ke.co.technovation.dto.QueryDTO;
import ke.co.technovation.entity.RedCrossWinner;

public interface WinnersEJBI {

	public List<RedCrossWinner> listWinnersToday();

	public List<RedCrossWinner> search(QueryDTO queryDTO);
	
	public boolean markForPayment(RedCrossWinner winner);
}
