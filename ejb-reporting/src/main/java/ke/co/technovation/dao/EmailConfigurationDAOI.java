package ke.co.technovation.dao;

import ke.co.technovation.entity.EmailConfiguration;

public interface EmailConfigurationDAOI extends GenericDAOI<EmailConfiguration, Long> {

	public EmailConfiguration poll();

}
