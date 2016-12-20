package ke.co.technovation.dao;

import ke.co.technovation.entity.SMSConfig;

public interface SMSConfigDAOI extends GenericDAOI<SMSConfig, Long> {

	public SMSConfig getFirst(); 

}
