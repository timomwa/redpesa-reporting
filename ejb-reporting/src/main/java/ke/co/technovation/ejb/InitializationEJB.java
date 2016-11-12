package ke.co.technovation.ejb;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.log4j.Logger;

import ke.co.technovation.entity.AccountStatus;
import ke.co.technovation.entity.AccountType;
import ke.co.technovation.entity.User;

@Singleton
@Startup
public class InitializationEJB {
	
	private static final String ADMIN_USERNAME = "admin";

	private Logger logger = Logger.getLogger(getClass());
	
	@EJB
	private UserEJBI userEJB;
	
	@PostConstruct
	public void init(){
		
		createAdminUser();
		
	}

	private void createAdminUser() {
		
		try{
			
			User user = userEJB.findUserByUsername(ADMIN_USERNAME);
			
			if(user==null){
				user = new User();
				user.setAccountCode("ADMIN001");
				user.setDateCreated(new Date());
				user.setLastLoggedin(new Date());
				user.setPwdhash("keyLSt+vYk1c0mAOzd0UFYpIFEzeW0xfzQ==");
				user.setStatus(Long.valueOf( AccountStatus.ACTIVE.getCode() ));
				user.setType(AccountType.ADMIN_USER.getCode());
				user.setUsername( ADMIN_USERNAME );
				user = userEJB.save(user);
			}
		
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
	}

}
