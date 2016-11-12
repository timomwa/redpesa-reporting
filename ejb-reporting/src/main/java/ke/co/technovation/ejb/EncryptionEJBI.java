package ke.co.technovation.ejb;

public interface EncryptionEJBI {
	
	public String hashPassword(String password, String salt);

}
