package ke.co.technovation.web;

import java.io.IOException;
import java.io.Serializable;

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
public class LoginBean implements Serializable {
	
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
		    boolean loginsuccess = false;
    		try {
		    	
		    	token.setRememberMe(true);
				currentUser.login( token );
				User user = userEJB.findUserByUsername(loginUsername);
	    		currentUser.getSession().setAttribute("user", user);
	    		loginsuccess = true;
	    		
	    	   
	        	
				
		    } catch ( UnknownAccountException uae ) {
		    	logger.error(uae.getMessage());
			} catch ( IncorrectCredentialsException ice ) {
				logger.error(ice.getMessage());
			} catch ( LockedAccountException lae ) {
				logger.error(lae.getMessage());
			} catch ( AuthenticationException ae ) {
				logger.error(ae.getMessage());
			}catch(Exception e){
				logger.error(e.getMessage(),e);
			}
    		logger.info("\n\t\tLogin Success :: "+loginsuccess);
    		
    		if(loginsuccess){
    			
    			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Successfully logged in.");
 	            FacesContext.getCurrentInstance().addMessage("loginForm:login", msg);
 	 
 	            try {
					redirect("app.jsf");
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
    		}else{
    			
    		    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Incorrect credentials");
		        FacesContext.getCurrentInstance().addMessage("loginForm:login", msg);
		     
			}
		    
    	}else{
    		logger.info("\n\n USER IS CURRENTLY LOGGED IN! \n\n ");
    		try {
				redirect("app.jsf");
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
        }
    	
    	
        
    }
 
    private void redirect(String url) throws IOException {
    	FacesContext.getCurrentInstance().getExternalContext().redirect(url);
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