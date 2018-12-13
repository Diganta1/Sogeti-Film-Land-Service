package com.sogeti.filmLand.service;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sogeti.filmLand.Constant.ConstantValue;
import com.sogeti.filmLand.controller.AvailableServiceController;
import com.sogeti.filmLand.controller.UserController;
import com.sogeti.filmLand.dao.AvailableServices;
import com.sogeti.filmLand.dao.LoginDatabase;
import com.sogeti.filmLand.exception.ServiceException;
import com.sogeti.filmLand.rest.model.LoginDatabaseRequest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Component
@Service

public class UserService {
	
	private static final Logger FILMLAND_LOG = LoggerFactory.getLogger("FILMLAND_LOG");

	@Autowired
	private UserController userController;

	@Autowired
	private AvailableServiceController resultController;

	public Iterable<LoginDatabase> getUserDetails() {
		return userController.findAll();
	}
	
	

	/**This method will allow you to login to a application
	 * @param login
	 *        {@link LoginDatabaseRequest} object contain login data
	 * @return
	 *        Save the user
	 * @throws ServiceException
	 *        Customized exception to handle in case encryption or login data is not relevant
	 */
	public boolean saveUser(LoginDatabaseRequest login) throws ServiceException {
		
		    
		try {
			final Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, ConstantValue.VECTOR_STRING,ConstantValue.SECRET_KEY);
			final byte[] encryptedByteArray = cipher.doFinal(login.getPassword().getBytes());
			login.setPassword((new BASE64Encoder()).encode(encryptedByteArray));
			LoginDatabase registeringDetails = new LoginDatabase();
			registeringDetails.setEmail_id(login.getEmail_id());
			registeringDetails.setName(login.getName());
			registeringDetails.setPassword(login.getPassword());
			registeringDetails.setAutoizationKey(String.format(ConstantValue.APIKEY_FORMAT, login.getEmail_id(), login.getName()));
			userController.save(registeringDetails);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			if (e.getMessage() != null) {
				throw new ServiceException(e.getMessage(), e);
			}

		} catch (Exception e) {
			FILMLAND_LOG.error("Specific Email ID: {} Exists", login.getEmail_id());
			return false;
		}
		FILMLAND_LOG.info("User Email ID: {}  Successfully registered in the database", login.getEmail_id());
		return true;

	}

	/**Return User by validating authorization key 
	 * @param apiKey
	 *        object containing authorization value
	 * @return
	 *        User name
	 */
	public String getUserByAPIKey(String apiKey) {
		return userController.findNameByApiKey(apiKey);
	}
	/**Return login details
	 * @param username
	 *       containing the name of subscriber
	 * @return
	 *       return Login database object
	 */
	public LoginDatabase getDetailsByName(String username) {
		return userController.findDetailsByName(username);
	}

	/**This method validate the user credential with database and allow login to data base
	 * @param user
	 *        {@link LoginDatabaseRequest} Containing the user object
	 * @return
	 *        Successful or unsuccessful string value
	 * @throws ServiceException
	 *        Customized exception to handle in case decryption not possible or login data is not relevant 
	 */
	public boolean validateUser(LoginDatabaseRequest user) throws ServiceException {
		try {
			LoginDatabase registeringDetails = new LoginDatabase();
			registeringDetails.setEmail_id(user.getEmail_id());
			registeringDetails.setPassword(user.getPassword());
			Iterable<LoginDatabase> userList = userController.findAll();
			for (LoginDatabase userLogin : userList) {
				final Cipher cipher = initCipher(Cipher.DECRYPT_MODE,ConstantValue.VECTOR_STRING,ConstantValue.SECRET_KEY);
				final byte[] encryptedByteArray = (new BASE64Decoder()).decodeBuffer(userLogin.getPassword());
				final byte[] decryptedByteArray = cipher.doFinal(encryptedByteArray);
				if (user.getEmail_id().equals(userLogin.getEmail_id())
						&& user.getPassword().equals(new String(decryptedByteArray,ConstantValue.CHARSET_VALUE))) {
					FILMLAND_LOG.info("User: {} Successfully logged in", user.getEmail_id());
					return true;
				}
			}
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
				| IOException e) {
			if (e.getMessage() != null) {
				throw new ServiceException(e.getMessage(), e);
			}
		}
		FILMLAND_LOG.info("User: {} Failed logged in", user.getEmail_id());
		return false;
	}
	

	/**This method will provide list of services subscribed or available for a customer
	 * @param name
	 *        Take a user name
	 * @return
	 *        return value list 
	 */
	public List<AvailableServices> fetchAvailableServices(String name) {
		Iterable<AvailableServices> result = resultController.findAll();
		List<AvailableServices> resultList = new ArrayList<>();
		for (AvailableServices result2 : result) {
			if (name.equals(result2.getUser_name())) {
				resultList.add(result2);
			}
		}
		return resultList;
	}

	/**This method will return hash algorithm value for a password
	 * @param input
	 *        containing a secret key
	 * @return
	 *        MD5 algorithm secret data
	 * @throws NoSuchAlgorithmException
	 *        In case algorithm is invalid 
	 */
	private String md5(final String input) throws NoSuchAlgorithmException {
		final MessageDigest md = MessageDigest.getInstance(ConstantValue.ALGORITHM);
		final byte[] messageDigest = md.digest(input.getBytes());
		final BigInteger number = new BigInteger(1, messageDigest);
		return String.format(ConstantValue.HASHING_FORMAT, number);
	}

	/**This method will Serialized and make a 128-bits (32 caracters) "hash" value
	 * @param mode
	 *       obtain a decrypt or encrypt status
	 * @param initialVectorString
	 *       obtain a synchronous parameter
	 * @param secretKey
	 *       obtain a secret string
	 * @return
	 *       128 bit hash value
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 *         In case unable to interpretation the algorithm 
	 */
	private Cipher initCipher(final int mode, final String initialVectorString, final String secretKey)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException {
		final SecretKeySpec skeySpec = new SecretKeySpec(md5(secretKey).getBytes(), ConstantValue.CIPHER_ALGORITHM);
		final IvParameterSpec initialVector = new IvParameterSpec(initialVectorString.getBytes());
		final Cipher cipher = Cipher.getInstance(ConstantValue.CIPHER_ALGORITHM_128);
		cipher.init(mode, skeySpec, initialVector);
		return cipher;
	}
}