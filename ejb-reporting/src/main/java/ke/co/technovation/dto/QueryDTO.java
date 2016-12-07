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
	private Integer limit;
	private Integer start;
	
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
	
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	@Override
	public String toString() {
		return "QueryDTO [start=" + start + ",limit=" + limit + ",msisdn=" + msisdn + ",\ndatefrom=" + datefrom + ",\ndateto=" + dateto + "]";
	}
	
	

}
