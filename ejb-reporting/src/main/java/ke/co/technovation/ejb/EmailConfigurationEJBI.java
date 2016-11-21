package ke.co.technovation.ejb;

import ke.co.technovation.constants.EmailTestStatus;
import ke.co.technovation.entity.EmailConfiguration;

public interface EmailConfigurationEJBI {

	public EmailConfiguration save(EmailConfiguration config) throws Exception;
	
	public EmailConfiguration getTheFirstConfig() throws Exception;
	
	public EmailTestStatus testConfig(EmailConfiguration config);
}
