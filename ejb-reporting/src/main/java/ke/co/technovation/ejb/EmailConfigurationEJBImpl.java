package ke.co.technovation.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import ke.co.technovation.constants.EmailTestStatus;
import ke.co.technovation.dao.EmailConfigurationDAOI;
import ke.co.technovation.entity.EmailConfiguration;

@Stateless
public class EmailConfigurationEJBImpl implements EmailConfigurationEJBI {

	@Inject
	private EmailConfigurationDAOI mailconfigDAO;
	
	@EJB
	private MailerEJBI mailerEJB;
	
	@Override
	public EmailConfiguration save(EmailConfiguration config) throws Exception{
		return mailconfigDAO.save(config);
	}
	
	
	@Override
	public EmailConfiguration getTheFirstConfig() throws Exception{
		return mailconfigDAO.poll();
	}
	
	@Override
	public EmailTestStatus testConfig(EmailConfiguration config){
		return mailerEJB.sendTestMail(config);
	}
	
}
