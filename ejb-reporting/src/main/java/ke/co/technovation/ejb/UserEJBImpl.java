package ke.co.technovation.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ke.co.technovation.dao.UserDAOI;
import ke.co.technovation.entity.User;

@Stateless
public class UserEJBImpl implements UserEJBI {
	
	@Inject
	private UserDAOI userDAO;
	
	@Override
	public User findUserByUsername(String username){
		return userDAO.findBy("username", username);
	}
	
	@Override
	public User save(User user) throws Exception{
		return userDAO.save(user);
	}

}
