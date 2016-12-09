package ke.co.technovation.web;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class PageBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2238228609115250365L;
	
	private String page = "home";
	private String loginPage = "login";
	public static final String APPROOT = "/redpesa-reporting";
	private String approot = APPROOT;
	
	

	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public void home() {
		page = "home";
	}
	
	public void portfolio() {
		page = "portfolio";
	}

	public void contactus() {
		page = "contactus";
	}
	
	public void perMsisdnTransactionReport(){
		page = "perMsisdnTransactionReport";
	}
	public void detailedMobileMoneyInflow(){
		page = "detailedMobileMoneyInflow";
	}
	
	public void detailedMobileMoneyOutflow(){
		page = "detailedMobileMoneyOutflow";
	}
	

	public void sameHourComparisonInflow(){
		page = "sameHourComparisonInflow";
	}
	public void sameHourComparisonOutflow(){
		page = "sameHourComparisonOutflow";
	}
	public void perMsisdnTransactionCount(){
		page = "perMsisdnTransactionCount";
	}
	public void perAccountTransactionCount(){
		page = "perAccountTransactionCount";
	}
	public void sameHourPaybillTransactionComparison(){
		page = "sameHourPaybillTransactionComparison";
	}
	
	public void reportSubscription(){
		page = "reportSubscription";
	}
	
	public void profileInfo(){
		page = "profileInfo";
	}
	public void changePassword(){
		page = "changepassword";
	}
	
	public void userManagement(){
		page = "userManagement";
	}
	public void emailSettings(){
		page = "emailSettings";
	}
	public void auditTrailReport(){
		page = "auditTrailReport";
	}
	public void bussinessShortcodeInfo(){
		page = "bussinessShortcodeInfo";
	}
	public void initiatorAccount(){
		page = "initiatorAccount";
	}
	public void periodicReportingSchedule(){
		page = "periodicReportingSchedule";
	}
	
	//TODO store this in a session
	public String getPage() {
		final String temp_page = page;
		page = "home";
		return temp_page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getApproot() {
		return approot;
	}

	public void setApproot(String approot) {
		this.approot = approot;
	}


	
}