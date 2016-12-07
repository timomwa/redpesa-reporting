package ke.co.technovation.ejb;

import java.util.List;

import co.ke.technovation.entity.MpesaIn;
import co.ke.technovation.entity.MpesaOut;
import ke.co.technovation.dto.QueryDTO;

public interface MobileMoneyTransactionsEJBI {
	
	public List<MpesaIn> getIns(QueryDTO queryDTO);
	
	public List<MpesaOut> getOuts(QueryDTO queryDTO);

}
