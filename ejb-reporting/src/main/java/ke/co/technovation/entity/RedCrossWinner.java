package ke.co.technovation.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import ke.co.technovation.constants.AppPropertyHolder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="winners", schema=AppPropertyHolder.REDCROS_SCHEMA_NAME)
public class RedCrossWinner  implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5232580101710819618L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_played")
	private Date date_played;
	
	@Column(name="ticket_number", length=100)
	private String ticket_number;
	
	@Column(name="serial_number", length=11)
	private Integer serial_number;
	
	@Column(name="phone_number", length=100)
	private String phone_number;
	
	@Column(name="draw_number", length=11)
	private Integer draw_number;
	
	@Column(name="telco_transaction_id", length=100)//TransactionID
	private String telco_transaction_id;
		
	@Column(name="first_name", length=100)
	private String first_name;
	
	@Column(name="last_name", length=100)
	private String last_name;
	
	@Column(name="price_won", precision=10, scale=2)
	private BigDecimal price_won;
	
	@Column(name="seed", length=11)
	private Integer seed;
	
	@Column(name="is_processed")
	private Boolean is_processed;
	
	@Column(name="is_verified")
	private Boolean is_verified;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_created")
	private Date date_created;
	
	@Column(name="payment_status")
	private Integer payment_status;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDate_played() {
		return date_played;
	}
	public void setDate_played(Date date_played) {
		this.date_played = date_played;
	}
	public String getTicket_number() {
		return ticket_number;
	}
	public void setTicket_number(String ticket_number) {
		this.ticket_number = ticket_number;
	}
	public Integer getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(Integer serial_number) {
		this.serial_number = serial_number;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public Integer getDraw_number() {
		return draw_number;
	}
	public void setDraw_number(Integer draw_number) {
		this.draw_number = draw_number;
	}
	public String getTelco_transaction_id() {
		return telco_transaction_id;
	}
	public void setTelco_transaction_id(String telco_transaction_id) {
		this.telco_transaction_id = telco_transaction_id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public BigDecimal getPrice_won() {
		return price_won;
	}
	public void setPrice_won(BigDecimal price_won) {
		this.price_won = price_won;
	}
	public Integer getSeed() {
		return seed;
	}
	public void setSeed(Integer seed) {
		this.seed = seed;
	}
	public Boolean getIs_processed() {
		return is_processed;
	}
	public void setIs_processed(Boolean is_processed) {
		this.is_processed = is_processed;
	}
	public Boolean getIs_verified() {
		return is_verified;
	}
	public void setIs_verified(Boolean is_verified) {
		this.is_verified = is_verified;
	}
	public Date getDate_created() {
		return date_created;
	}
	public void setDate_created(Date date_created) {
		this.date_created = date_created;
	}
	public Integer getPayment_status() {
		return payment_status;
	}
	public void setPayment_status(Integer payment_status) {
		this.payment_status = payment_status;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof RedCrossWinner)) {
	        return false;
	    }
		RedCrossWinner that = (RedCrossWinner) obj;
		return ( (this.getTicket_number().equals( that.getTicket_number() )) ) ;
	}
	
	@Override
	public String toString() {
		return "RedCrossWinner [id=" + id + ",\ndate_played=" + date_played + ",\nticket_number=" + ticket_number
				+ ",\nserial_number=" + serial_number + ",\nphone_number=" + phone_number + ",\ndraw_number="
				+ draw_number + ",\ntelco_transaction_id=" + telco_transaction_id + ",\nfirst_name=" + first_name
				+ ",\nlast_name=" + last_name + ",\nprice_won=" + price_won + ",\nseed=" + seed + ",\nis_processed="
				+ is_processed + ",\nis_verified=" + is_verified + ",\ndate_created=" + date_created
				+ ",\npayment_status=" + payment_status + "]";
	}
	
	

	
}
