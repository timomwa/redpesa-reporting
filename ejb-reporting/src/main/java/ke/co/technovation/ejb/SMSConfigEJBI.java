package ke.co.technovation.ejb;

import ke.co.technovation.entity.SMSConfig;

public interface SMSConfigEJBI {
	
	public SMSConfig save(SMSConfig config) throws Exception;
	
	public SMSConfig getFirstConfig();

}
