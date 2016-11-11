package ke.co.technovation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3817830703329744557L;
	
	@Column(name="name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
