package ke.co.technovation.ejb;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.log4j.Logger;
import org.jasypt.digest.PooledStringDigester;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.hibernate4.encryptor.HibernatePBEEncryptorRegistry;

import ke.co.technovation.entity.AccountStatus;
import ke.co.technovation.entity.AccountType;
import ke.co.technovation.entity.User;

@Singleton
@Startup
public class InitializationEJB {
	
	public static PooledPBEStringEncryptor db_encryptor;
	public static PooledStringDigester password_digestor;
	private static final String password_ = "_TheRestlessGeek1985!";
	private static final String ADMIN_USERNAME = "lottoadmin";

	private Logger logger = Logger.getLogger(getClass());
	
	@EJB
	private UserEJBI userEJB;
	
	@PostConstruct
	public void init(){
		
		createAdminUser();
		
		initEncryptors();
		
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
	
	
	public void initEncryptors(){
		
		int cores = Runtime.getRuntime().availableProcessors();
		logger.info("\n\t *** cores - "+cores+" ***");
		
		if(db_encryptor==null){
			logger.info("\n\t  *** initializing pooled encryptor  ***\n");
			db_encryptor = new PooledPBEStringEncryptor();
			db_encryptor.setPassword(password_);
			db_encryptor.setPoolSize(cores);
			HibernatePBEEncryptorRegistry registry = HibernatePBEEncryptorRegistry.getInstance();
			registry.registerPBEStringEncryptor("myHibernateStringEncryptor", db_encryptor);
			logger.info("\n\t  *** successfully initialized pooled encryptor  ***\n");
		}
		
		if(password_digestor==null){
			logger.info("\n\t  *** initializing poled password digestor  ***\n");
			password_digestor = new PooledStringDigester();
			password_digestor.setPoolSize(cores);          
			password_digestor.setAlgorithm("SHA-1");
			password_digestor.setIterations(50000);//TODO figure out whether this affects performance
			logger.info("\n\t  *** successfully initialized pooled password digestor  ***\n");
		}
	}

}
