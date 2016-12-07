package ke.co.technovation.web.beans;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="reportConstantsBean")
@RequestScoped
public class ReportConstantsBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2577530842018301734L;
	
	public final static String JASPER_PAYBILL_ACC_DIST = "http://139.59.144.70:8080/jasperserver/flow.html?_flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Freports%2Fredpesa&reportUnit=%2Freports%2Fredpesa%2FPaybill_Account_Number_Distribution_to_date&standAlone=true&j_username=reporter&j_password=reporter&decorate=no";
	

	public String getPaybillAccDistURL(){
		return JASPER_PAYBILL_ACC_DIST;
	}

}
