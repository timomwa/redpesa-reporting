package ke.co.technovation.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="transaction")
public class MsisdnTransactionsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7785402231241235458L;

	private Date date;
	
	private Long transactioncount;
	
	private String msisdn;

	@XmlElement(name = "date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@XmlElement(name = "transactioncount")
	public Long getTransactioncount() {
		return transactioncount;
	}

	public void setTransactioncount(Long transactioncount) {
		this.transactioncount = transactioncount;
	}

	@XmlElement(name = "msisdn")
	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	
	

}
