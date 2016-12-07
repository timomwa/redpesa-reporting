package ke.co.technovation.dao;

import java.util.List;

import co.ke.technovation.entity.MpesaOut;
import ke.co.technovation.dto.QueryDTO;

public interface MpesaOutDAOI {
	
	public List<MpesaOut> list(QueryDTO queryDTO);

}
