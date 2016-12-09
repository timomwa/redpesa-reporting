package ke.co.technovation.web.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import ke.co.technovation.ejb.EncryptionEJBI;
import ke.co.technovation.ejb.UserEJBI;
import ke.co.technovation.entity.User;
import ke.co.technovation.web.PageBean;

@ManagedBean(name="userBean")
@SessionScoped
public class UserBean implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5891473134392469526L;
	
	@EJB
	private EncryptionEJBI encryptionEJB;
	
	@EJB
	private UserEJBI userEJB;
	
	private String oldPassword;
	
	private String newPassword;
	
	private String repeatPassword;
	
	private Logger logger = Logger.getLogger(getClass());

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
	
	public void change(){
		logger.info("\n\t\t Old Password		: "+oldPassword +"\n"
				   +"\t\t New Password		: "+newPassword +"\n"
				   +"\t\t Repeat Password	: "+repeatPassword +"\n");
		
		Subject currentUser = SecurityUtils.getSubject();
    	String user_ = (String) currentUser.getPrincipal();
    	
    	if ( currentUser.isAuthenticated() ) {
    		
    		User user = userEJB.findUserByUsername(user_);
    		String alleged_Hash = encryptionEJB.hashPassword(oldPassword, user_);
    		
    		if(alleged_Hash.equals(user.getPwdhash())){//Current password supplied is correct. Go ahead confirm if new one matches
    			
    			if(newPassword.equals(repeatPassword)){
    				
	    			String new_passhash = encryptionEJB.hashPassword(newPassword, user_);
	    			user.setPwdhash(new_passhash);
	    			
	    			try {
						user = userEJB.save(user);
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Password change was successful. Please use the new password to log in again!");
		 	            FacesContext.getCurrentInstance().addMessage("changepwdForm:changepassword", msg);
		 	      	} catch (Exception e) {
						logger.error(e.getMessage(), e);
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Sorry, problem occurred changing password. Try again later");
		 	            FacesContext.getCurrentInstance().addMessage("changepwdForm:changepassword", msg);
					}
	    			
	    			try{
	    				redirect(PageBean.APPROOT+"/logout");
	    				//SecurityUtils.getSecurityManager().logout(currentUser);
	    			}catch(Exception e){
	    				logger.error(e.getMessage(), e);
	    			}
	    			
    			}else{
    				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "New passwords don't match! Try again.");
	 	            FacesContext.getCurrentInstance().addMessage("changepwdForm:changepassword", msg);
    			}
    			
    		}else{
    			
    			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Your \"Old Password\" supplied is incorrect!");
 	            FacesContext.getCurrentInstance().addMessage("changepwdForm:changepassword", msg);
    		}
    		
    		
    		
    	}else{
    		
    		try {
				redirect("login.jsf");
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
    		
        }
	}
	
	
	 private void redirect(String url) throws IOException {
	    	FacesContext.getCurrentInstance().getExternalContext().redirect(url);
	}

	

}
