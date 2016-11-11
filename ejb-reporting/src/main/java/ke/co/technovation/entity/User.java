package ke.co.technovation.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user", indexes = {
			@Index(columnList="accountCode", name="accodeidx",  unique=true),
			@Index(columnList="username", name="usrnmeidx", unique=true)
		}
)
public class User  extends AbstractEntity{
	
	private static final long serialVersionUID = -5809294654963141829L;

	@Column(name="username", nullable=false)
	private String username;
	
	@Column(name="pwdhash")
	private String pwdhash;

	/**
	 * Used to identify users
	 * across transactions.
	 * More or less like a bank account.
	 * This, without revealing email address
	 */
	@Column(name="accountCode")
	private String accountCode;
	
	@Column(name = "dateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	
	
	@Column(name = "lastLoggedin")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoggedin;
	
	@Column(name = "status")
	private Long status;
	
	@Column(name="type", nullable=false, length=1)
	private Long type;
		
	@PrePersist
	public void onCreate(){
		if(type==null)
			type = Long.valueOf( UserType.UNKNOWN.ordinal() );
		if(dateCreated==null)
			dateCreated = new Date();
		if(status==null)
			status = Long.valueOf( AccountStatus.NEW.ordinal() );
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public String getPwdhash() {
		return pwdhash;
	}
	public void setPwdhash(String pwdhash) {
		this.pwdhash = pwdhash;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Date getLastLoggedin() {
		return lastLoggedin;
	}
	public void setLastLoggedin(Date lastLoggedin) {
		this.lastLoggedin = lastLoggedin;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [username=");
		builder.append(username);
		builder.append(", \naccountCode=");
		builder.append(accountCode);
		builder.append(", \ndateCreated=");
		builder.append(dateCreated);
		builder.append(", \nstatus=");
		builder.append(status);
		builder.append(", \ntype=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

}
