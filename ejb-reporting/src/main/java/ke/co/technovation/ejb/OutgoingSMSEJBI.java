package ke.co.technovation.ejb;

import ke.co.technovation.entity.SMS;
import ke.co.technovation.httputils.GenericHttpResp;

public interface OutgoingSMSEJBI {
	
	public GenericHttpResp sendSMS(SMS sms);

}
