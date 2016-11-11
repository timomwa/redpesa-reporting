package ke.co.technovation.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2019282598055221762L;
	
	
	@ManyToOne(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private Role role;
	
	@ManyToOne(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

}
