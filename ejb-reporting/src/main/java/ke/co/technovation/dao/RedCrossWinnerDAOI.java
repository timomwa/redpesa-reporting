package ke.co.technovation.dao;

import java.util.Date;
import java.util.List;

import ke.co.technovation.dto.QueryDTO;
import ke.co.technovation.entity.RedCrossWinner;


public interface RedCrossWinnerDAOI extends GenericDAOI<RedCrossWinner, Integer> {

	public List<RedCrossWinner> getUnpaidWinners(int limit);
	
	public RedCrossWinner findBy(String fieldName, Object value);

	public List<RedCrossWinner> listWinnersByDateWon(Date date);
	
	public List<RedCrossWinner> search(QueryDTO queryDTO);

}
