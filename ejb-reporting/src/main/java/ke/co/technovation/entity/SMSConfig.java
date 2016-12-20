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
@Table(name="sms_config")
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
public class SMSConfig extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4598877793351647883L;
	
	@Type(type="encryptedString")
	@Column(name="apiURL")
	private String apiURL;
	
	
	@Type(type="encryptedString")
	@Column(name="payloadTemplate")
	private String payloadTemplate;
	
	@Type(type="encryptedString")
	@Column(name="username")
	private String username;
	
	@Type(type="encryptedString")
	@Column(name="password")
	private String password;
	
	@Type(type="encryptedString")
	@Column(name="apiKey")
	private String apiKey;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiURL() {
		return apiURL;
	}

	public void setApiURL(String apiURL) {
		this.apiURL = apiURL;
	}

	public String getPayloadTemplate() {
		return payloadTemplate;
	}

	public void setPayloadTemplate(String payloadTemplate) {
		this.payloadTemplate = payloadTemplate;
	}

	@Override
	public String toString() {
		return "SMSConfig [apiURL=" + apiURL + ",\npayloadTemplate=" + payloadTemplate + ",\nusername=" + username
				+ ",\npassword=" + password + ",\napiKey=" + apiKey + "]";
	}
	

}
