package ke.co.technovation.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

@MappedSuperclass
public class AbstractEntity implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -5890276591006211776L;
	@Id
	@Column(name = "id", nullable = false)
	@SequenceGenerator(name = "patient_seq", sequenceName = "patient_id_seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "patient_seq")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	


}
