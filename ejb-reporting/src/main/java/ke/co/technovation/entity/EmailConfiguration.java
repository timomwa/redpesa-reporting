package ke.co.technovation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jasypt.hibernate4.type.EncryptedBooleanAsStringType;
import org.jasypt.hibernate4.type.EncryptedIntegerAsStringType;
import org.jasypt.hibernate4.type.EncryptedStringType;

@Entity
@Table(name="mailer_config")
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
public class EmailConfiguration extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2288448157951906562L;

	@Type(type="encryptedString")
	@Column(name="email_username")
	private String email_username;
	
	@Type(type="encryptedString")
	@Column(name="email_password")
	private String email_password;
	
	@Type(type="encryptedString")
	@Column(name="send_from_email")
	private String send_from_email;
	
	@Type(type="encryptedString")
	@Column(name="receiver_test_email")
	private String receiver_test_email;
	
	
	@Type(type="encryptedString")
	@Column(name="smtp_host")
	private String smtp_host;
	
	
	@Type(type="encryptedInteger")
	@Column(name="smtp_port")
	private Integer smtp_port;
	
	@Type(type="encryptedBoolean")
	@Column(name="auth")
	private Boolean auth;
	
	@Type(type="encryptedBoolean")
	@Column(name="starttls_enabled")
	private Boolean starttls_enabled;
	
	
	@Type(type="encryptedBoolean")
	@Column(name="active")
	private Boolean active;

	public String getEmail_username() {
		return email_username;
	}

	public void setEmail_username(String email_username) {
		this.email_username = email_username;
	}

	public String getEmail_password() {
		return email_password;
	}

	public void setEmail_password(String email_password) {
		this.email_password = email_password;
	}

	public String getSend_from_email() {
		return send_from_email;
	}

	public void setSend_from_email(String send_from_email) {
		this.send_from_email = send_from_email;
	}

	public String getSmtp_host() {
		return smtp_host;
	}

	public void setSmtp_host(String smtp_host) {
		this.smtp_host = smtp_host;
	}

	public Integer getSmtp_port() {
		return smtp_port;
	}

	public void setSmtp_port(Integer smtp_port) {
		this.smtp_port = smtp_port;
	}

	public Boolean getAuth() {
		return auth;
	}

	public void setAuth(Boolean auth) {
		this.auth = auth;
	}

	public Boolean getStarttls_enabled() {
		return starttls_enabled;
	}

	public void setStarttls_enabled(Boolean starttls_enabled) {
		this.starttls_enabled = starttls_enabled;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	

	public String getReceiver_test_email() {
		return receiver_test_email;
	}

	public void setReceiver_test_email(String receiver_test_email) {
		this.receiver_test_email = receiver_test_email;
	}

	@Override
	public String toString() {
		return "EmailConfiguration [email_username=" + "******encrypted****" + ",\nemail_password=" + "******encrypted****"
				+ ",\nsend_from_email=" + send_from_email + ",\nsmtp_host=" + smtp_host + ",\nsmtp_port=" + smtp_port
				+ ",\nauth=" + auth + ",\nstarttls_enabled=" + starttls_enabled + ",\nactive=" + active + "]";
	}

	
}
