package ke.co.technovation.ejb;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

@Stateless
public class EncryptionEJBImpl implements EncryptionEJBI {

	private Logger logger = Logger.getLogger(getClass());
	
	public final int DEFAULT_ITERATIONS = 5000;
	public final int DEFAULT_KEYLENGTH = 200;
	private String DEFAULT_ALGORITHM = "PBKDF2WithHmacSHA512";
	
	@Override
	public String hashPassword(String password, String salt) {
		byte[] hash_byte = hashPassword(password.toCharArray(), salt.getBytes(), DEFAULT_ITERATIONS, DEFAULT_KEYLENGTH);
		return Base64.getEncoder().encodeToString(hash_byte);
	}

	private byte[] hashPassword(final char[] password, final byte[] salt, final int iterations,
			final int keyLength) {

		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance(DEFAULT_ALGORITHM );
			PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
			SecretKey key = skf.generateSecret(spec);
			return key.getEncoded();

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			logger.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
	}

}
