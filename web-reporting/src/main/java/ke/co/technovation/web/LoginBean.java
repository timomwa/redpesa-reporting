package ke.co.technovation.web;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Size;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import ke.co.technovation.ejb.EncryptionEJBI;
import ke.co.technovation.ejb.UserEJBI;
import ke.co.technovation.entity.User;

@ManagedBean
@RequestScoped
public class LoginBean {
	
	@EJB
	private EncryptionEJBI encryptionEJB;
	
	@EJB
	private UserEJBI userEJB;
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Size(min=4, max=10)
	private String loginUsername;
	
	@Size(min=4, max=100)
	private String loginPassword;
	
    public void login() {
    	
    	Subject currentUser = SecurityUtils.getSubject();
    	String user_ = (String) currentUser.getPrincipal();
    	
    	logger.info("\n\n user_ -> "+user_+" \n\n ");
	
		if ( !currentUser.isAuthenticated() ) {
    		
    		logger.info("\n\n USER IS NOT LOGGED IN! \n\n ");
    		
    		String receivedhash = encryptionEJB.hashPassword(loginPassword, loginUsername);
    		UsernamePasswordToken token = new UsernamePasswordToken(loginUsername, receivedhash);
		    
    		try {
		    	
		    	token.setRememberMe(true);
				currentUser.login( token );
				User user = userEJB.findUserByUsername(loginUsername);
	    		currentUser.getSession().setAttribute("user", user);
	    		
	    	    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Successfully logged in.");
	            FacesContext.getCurrentInstance().addMessage("loginForm:password", msg);
	 
		    } catch ( UnknownAccountException uae ) {
		    	logger.error(uae.getMessage(), uae);
			} catch ( IncorrectCredentialsException ice ) {
				logger.error(ice.getMessage(), ice);
				
			    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Incorrect credentials");
		        FacesContext.getCurrentInstance().addMessage("loginForm:password", msg);
		     
			} catch ( LockedAccountException lae ) {
				logger.error(lae.getMessage(), lae);
			} catch ( AuthenticationException ae ) {
				logger.error(ae.getMessage(), ae);
			}catch(Exception e){
				logger.error(e.getMessage(),e);
			}
		    
    	}else{
    		logger.info("\n\n USER IS CURRENTLY LOGGED IN! \n\n ");
        }
    	
    	System.out.println( "\n\n\n currentUser--> "+currentUser);
        if ("BootsFaces".equalsIgnoreCase(loginUsername) && "rocks!".equalsIgnoreCase(loginPassword)) {
        
        } else {
        }
        
    }
 
    public void forgotPassword() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Default user name: BootsFaces");
        FacesContext.getCurrentInstance().addMessage("loginForm:username", msg);
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Default password: rocks!");
        FacesContext.getCurrentInstance().addMessage("loginForm:password", msg);
    }

    public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
    
	
    
}