package com.sogeti.filmLand.Constant;

public final class ConstantValue {

	public static final String MAPPING_URI = "/rest/jwt";
	public static final String UNAUTHORIZED_MESSAGE = "Unauthorized to access service";
	public static final String VECTOR_STRING = "0123456789123456";
	public static final String SECRET_KEY = "JWT";
	public static final String APIKEY_FORMAT = "%s_%s";
	public static final String CHARSET_VALUE = "UTF8";
	public static final String ALGORITHM = "MD5";
	public static final String HASHING_FORMAT = "%032x";
	public static final String CIPHER_ALGORITHM = "AES";
	public static final String CIPHER_ALGORITHM_128 = "AES/CFB8/NoPadding";
	public static final String CRON_EXPRESSION  = "0 0/1 * 1/1 * ? ";
	
	/*Response Object */
	
	public static final String SUCCESSFULLY_CRETED = "User has been Successfully Registered";
	public static final String EXIST = "Email id is alreay exists";
	public static final String SUCCESSFUL_LOGIN = "Login Successfull";
	public static final String UNSUCCESSFUL_LOGIN = "Login Failed";
	public static final String BANDWIDTH = "Please try after some time";
	private ConstantValue() {
	    super();
	  }

}
