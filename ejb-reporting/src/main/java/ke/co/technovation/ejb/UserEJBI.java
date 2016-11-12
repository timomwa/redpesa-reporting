package ke.co.technovation.ejb;

import ke.co.technovation.entity.User;

public interface UserEJBI {

	public User findUserByUsername(String loginUsername);

	public User save(User user) throws Exception;

}
