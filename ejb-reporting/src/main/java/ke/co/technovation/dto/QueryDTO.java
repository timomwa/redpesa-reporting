package ke.co.technovation.dto;

import java.io.Serializable;
import java.util.Date;

public class QueryDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 302828033885821626L;
	
	private String msisdn;
	private Date datefrom;
	private Date dateto;
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public Date getDatefrom() {
		return datefrom;
	}
	public void setDatefrom(Date datefrom) {
		this.datefrom = datefrom;
	}
	public Date getDateto() {
		return dateto;
	}
	public void setDateto(Date dateto) {
		this.dateto = dateto;
	}
	@Override
	public String toString() {
		return "QueryDTO [msisdn=" + msisdn + ",\ndatefrom=" + datefrom + ",\ndateto=" + dateto + "]";
	}
	
	

}
