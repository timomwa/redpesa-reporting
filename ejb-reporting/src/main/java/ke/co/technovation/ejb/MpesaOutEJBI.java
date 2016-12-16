package ke.co.technovation.ejb;

import co.ke.technovation.entity.MpesaOut;
import ke.co.technovation.entity.RedCrossWinner;

public interface MpesaOutEJBI {

	public MpesaOut save(MpesaOut mpesaOut) throws Exception;

	public MpesaOut findByTransactionId(String transID);

	public MpesaOut getPaymentFromTicketNumber(RedCrossWinner winner);

}
