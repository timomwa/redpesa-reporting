package ke.co.technovation.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jasypt.hibernate4.type.EncryptedBooleanAsStringType;
import org.jasypt.hibernate4.type.EncryptedIntegerAsStringType;
import org.jasypt.hibernate4.type.EncryptedStringType;


@Entity
@Table(name="outgoing_email", indexes = {
		@Index(columnList="entryTime", name="mlentryTimeIdx"),
		@Index(columnList="priority", name="mlpriorityidx"),
		@Index(columnList="status", name="mlstatusIdx"),
		@Index(columnList="sendAfter", name="mlsendafterIdx"),
		@Index(columnList="retry_count", name="mlretryctIdx"),
		@Index(columnList="max_retries", name="mlmxrtryIdx"),
		@Index(columnList="entryTime,priority,status,retry_count,max_retries", name="mlfetchIdx")
})
@TypeDefs({
	@TypeDef(
	    name="encryptedString", 
	    typeClass=EncryptedStringType.class, 
	    parameters= {
	        @Parameter(
	        		name="encryptorRegisteredName", 
	        		value="myHibernateStringEncryptor"
	        		)
	    }
	    
	),
	@TypeDef(
		    name="encryptedInteger", 
		    typeClass=EncryptedIntegerAsStringType.class,
		    parameters= {
		        @Parameter(
		        		name="encryptorRegisteredName", 
		        		value="myHibernateStringEncryptor"
		        		)
		    }
		    
		),
	@TypeDef(
		    name="encryptedBoolean", 
		    typeClass=EncryptedBooleanAsStringType.class,
		    parameters= {
		        @Parameter(
		        		name="encryptorRegisteredName", 
		        		value="myHibernateStringEncryptor"
		        		)
		    }
		    
		)
})
@NamedQueries({
	@NamedQuery(
	name = Mail.UNSENT_EMAILS_ORDER_BY_PRIORITY_AND_TIME_IN,
	query = "from "
			+ "	 Mail "
			+ " WHERE "
			+ "  status in ( :statuses ) AND "
			+ "  max_retries>retry_count AND "
			+ "  sendAfter < :currentTstamp "
			+ " ORDER BY "
			+ "  priority asc, entryTime desc"
	)
})
public class Mail extends AbstractEntity {
	
	@Transient
	public static final String UNSENT_EMAILS_ORDER_BY_PRIORITY_AND_TIME_IN = "getunsentmail.order.by.priority.and.timein";
	/**
	 * 
	 */
	private static final long serialVersionUID = 2093661334120582280L;

	@Type(type="encryptedString")
	@Column(name="email", nullable=false)
	private String email;//Comma seperated 
	
	@Type(type="encryptedString")
	@Column(name="subject")
	private String subject;
	
	@Type(type="encryptedString")
	@Column(name="body", length=20000, nullable=false)
	private String body;
	
	@Type(type="encryptedString")
	@Column(name="attachmentURI", length=255)
	private String attachmentURI;
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="entryTime")
	private Date entryTime;
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="timeSent")
	private Date timeSent;
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sendAfter")
	private Date sendAfter;//We can only send the email after this time
	
	@Enumerated(EnumType.STRING)
	@Column(name="status", length=50)
	private ProcessingStatus status;
		
	@Column(name="config_id")
	private Long config_id;//The configuration to use sending the email
	
	@Column(name="retry_count")
	private Long retry_count;
	
	@Column(name="max_retries")
	private Long max_retries;//Maximum number of minutes to wait before failing an email permanently
	
	@Column(name="retry_wait_time")
	private Long retry_wait_time;//In minutes
		
	@Column(name="priority")
	private Long priority;//0-Instant, 1-Normal, 2-Low priority, 3-Least priority
	
	
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getBody() {
		return body;
	}


	public void setBody(String body) {
		this.body = body;
	}


	public String getAttachmentURI() {
		return attachmentURI;
	}


	public void setAttachmentURI(String attachmentURI) {
		this.attachmentURI = attachmentURI;
	}


	public Date getEntryTime() {
		return entryTime;
	}


	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}


	public Date getTimeSent() {
		return timeSent;
	}


	public void setTimeSent(Date timeSent) {
		this.timeSent = timeSent;
	}


	public Date getSendAfter() {
		return sendAfter;
	}


	public void setSendAfter(Date sendAfter) {
		this.sendAfter = sendAfter;
	}


	public ProcessingStatus getStatus() {
		return status;
	}


	public void setStatus(ProcessingStatus status) {
		this.status = status;
	}


	public Long getConfig_id() {
		return config_id;
	}


	public void setConfig_id(Long config_id) {
		this.config_id = config_id;
	}


	public Long getRetry_count() {
		return retry_count;
	}


	public void setRetry_count(Long retry_count) {
		this.retry_count = retry_count;
	}


	public Long getMax_retries() {
		return max_retries;
	}


	public void setMax_retries(Long max_retries) {
		this.max_retries = max_retries;
	}


	public Long getRetry_wait_time() {
		return retry_wait_time;
	}


	public void setRetry_wait_time(Long retry_wait_time) {
		this.retry_wait_time = retry_wait_time;
	}


	public Long getPriority() {
		return priority;
	}


	public void setPriority(Long priority) {
		this.priority = priority;
	}


	@PrePersist
	@PreUpdate
	public void update(){
		if(priority==null)
			priority = 1L;
		if(retry_wait_time==null)
			retry_wait_time = 5L;
		if(max_retries==null)
			max_retries = 4L;
		if(retry_count==null)
			retry_count = 0L;
		if(entryTime==null)
			entryTime = new Date();
		if(sendAfter==null)
			sendAfter = entryTime;
		if(status==null)
			status = ProcessingStatus.JUST_IN;
		if(config_id==null)
			config_id = -1L;		
	}

	

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Mail)) {
	        return false;
	    }
		Mail that = (Mail) obj;
		return ( this.getId().compareTo( that.getId() ) == 0) ;
	}


	@Override
	public String toString() {
		return "Email [email=" + email + ",\nsubject=" + subject + ",\nbody=" + body + ",\nattachmentURI="
				+ attachmentURI + ",\nentryTime=" + entryTime + ",\ntimeSent=" + timeSent + ",\nsendAfter=" + sendAfter
				+ ",\nstatus=" + status + ",\nconfig_id=" + config_id + ",\nretry_count=" + retry_count
				+ ",\nmax_retries=" + max_retries + ",\nretry_wait_time=" + retry_wait_time + ",\npriority=" + priority
				+ "]";
	}
	

	
}
