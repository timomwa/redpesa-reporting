package ke.co.technovation.ejb;

import java.io.ByteArrayOutputStream;
import java.util.List;

import ke.co.technovation.dto.MsisdnTransactionsDTO;
import ke.co.technovation.dto.QueryDTO;

public interface PerMsisdnTransactionCountEJBI {
	
	
	public List<MsisdnTransactionsDTO> getTransactions(QueryDTO queryDTO);
	public ByteArrayOutputStream generatePDF(QueryDTO queryDTO)  throws Exception;

}
