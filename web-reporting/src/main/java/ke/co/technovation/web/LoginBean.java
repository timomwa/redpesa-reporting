package ke.co.technovation.web;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Size;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

@ManagedBean
@RequestScoped
public class LoginBean {
	
	@Size(min=4, max=10)
	private String loginUsername;
	
	@Size(min=4, max=10)
	private String loginPassword;
	
    public void login() {
    	Subject currentUser = SecurityUtils.getSubject();
    	
    	System.out.println( "\n\n\n currentUser--> "+currentUser);
        if ("BootsFaces".equalsIgnoreCase(loginUsername) && "rocks!".equalsIgnoreCase(loginPassword)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Congratulations! You've successfully logged in.");
            FacesContext.getCurrentInstance().addMessage("loginForm:password", msg);
 
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "That's the wrong password. Hint: BootsFaces rocks!");
            FacesContext.getCurrentInstance().addMessage("loginForm:password", msg);
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