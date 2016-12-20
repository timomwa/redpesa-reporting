package ke.co.technovation.entity;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="winner_notification_sms")
public class SMS  extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8145312416765065277L;
	
	@Column(name="sms", length=4090)
	private String sms;
	
	@Column(name="msisdn", length=15)
	private String msisdn;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private ProcessingStatus status;
	
	@Column(name="timeStamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeStamp;
	
	
	@Column(name="resp_status")
	private Integer resp_status;
	
	@Column(name="resp", length=2048)
	private String resp;

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public ProcessingStatus getStatus() {
		return status;
	}

	public void setStatus(ProcessingStatus status) {
		this.status = status;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
	
	public Integer getResp_status() {
		return resp_status;
	}

	public void setResp_status(Integer resp_status) {
		this.resp_status = resp_status;
	}

	public String getResp() {
		return resp;
	}

	public void setResp(String resp) {
		this.resp = resp;
	}
	
	

	@PostConstruct
	@PrePersist
	public void update(){
		if(status==null)
			status = ProcessingStatus.JUST_IN;
		if(timeStamp==null)
			timeStamp = new Date();
	}

}
