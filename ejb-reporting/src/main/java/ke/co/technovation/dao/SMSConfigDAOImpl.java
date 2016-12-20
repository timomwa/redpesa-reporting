package ke.co.technovation.dao;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import ke.co.technovation.entity.SMSConfig;

public class SMSConfigDAOImpl extends GenericDAOImpl<SMSConfig, Long>  implements SMSConfigDAOI {

	private Logger logger = Logger.getLogger(getClass());
	
	public SMSConfig getFirst(){
		
		SMSConfig config = null;
		
		try{
			Query qry = em.createQuery("from SMSConfig ORDER BY id desc");
			qry.setFirstResult(0);
			qry.setMaxResults(1);
			config = (SMSConfig) qry.getSingleResult();
		}catch(javax.persistence.NoResultException nre){
			logger.warn(" Could not find any sms config.");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return config;
	}
}
