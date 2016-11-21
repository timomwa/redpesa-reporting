package ke.co.technovation.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import ke.co.technovation.entity.EmailConfiguration;

public class EmailConfigurationDAOImpl extends GenericDAOImpl<EmailConfiguration, Long>  implements EmailConfigurationDAOI {

	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public EmailConfiguration poll(){
		EmailConfiguration config = null;
		try{
			Query qry = em.createQuery("from EmailConfiguration order by id DESC");
			qry.setMaxResults(1);
			config = (EmailConfiguration) qry.getSingleResult();
		}catch(NoResultException nre){
			logger.warn("Could not find any record");
		}
		return config;
	}
}
