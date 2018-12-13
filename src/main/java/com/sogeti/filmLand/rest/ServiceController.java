package com.sogeti.filmLand.rest;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sogeti.filmLand.Constant.ConstantValue;
import com.sogeti.filmLand.Constant.Path;
import com.sogeti.filmLand.dao.LoginDatabase;
import com.sogeti.filmLand.dao.ServiceDetails;
import com.sogeti.filmLand.dao.SubscribedServices;
import com.sogeti.filmLand.exception.ServiceException;
import com.sogeti.filmLand.rest.model.LoginDatabaseRequest;
import com.sogeti.filmLand.rest.model.NewSusbscriptionRequest;
import com.sogeti.filmLand.rest.model.ResponseModel;
import com.sogeti.filmLand.rest.model.ShareSubscriptionRequest;
import com.sogeti.filmLand.rest.model.SubscribedServicesResponse;
import com.sogeti.filmLand.service.RestService;
import com.sogeti.filmLand.service.UserService;

@RestController
@RequestMapping(ConstantValue.MAPPING_URI)
public class ServiceController {

	private static final Logger FILMLAND_LOG = LoggerFactory.getLogger("FILMLAND_LOG");

	@Autowired(required=true)
	private UserService userService;

	@Autowired
	private RestService restService;

	/**
	 * This rest method is used to add user to the database
	 * 
	 * @param login
	 * 		  {@link LoginDatabaseRequest} object containing required information for storing the user 
	 * @return
	 * 		Success or failure details about the insertion
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 *    		Exception will be thrown in case of error during encryption
	 */
	@PostMapping(path = Path.SIGNUP, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> addUser(@RequestBody LoginDatabaseRequest login)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		boolean result = true;
		ResponseModel messageResponse = null;
		try {
			
			result = userService.saveUser(login);
			if(result==true)
			{
			messageResponse = new ResponseModel(HttpStatus.OK.toString(), ConstantValue.SUCCESSFULLY_CRETED);
			return new ResponseEntity<String>(new Gson().toJson(messageResponse), HttpStatus.OK);
			}
			else {
				messageResponse = new ResponseModel(HttpStatus.CONFLICT.toString(), ConstantValue.EXIST);
				return new ResponseEntity<String>(new Gson().toJson(messageResponse), HttpStatus.CONFLICT);
			}
		} catch (ServiceException e) {
			FILMLAND_LOG.error(e.getMessage());
		}
		return new ResponseEntity<String>(new Gson().toJson(ConstantValue.BANDWIDTH), HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
		

	}

	/**
	 * This method is to validate user authentication
	 * @param user
	 *        {@link LoginDatabaseRequest} object containing required information for authentication
	 * @return
	 *        Authorized or unauthorized to login the application
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws IOException
	 * 		  Exception will be thrown in case of error during decryption 
	 */
	@PostMapping(path = Path.LOGIN, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> validateUser(@RequestBody LoginDatabaseRequest user)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
		boolean result = true;
		ResponseModel messageResponse = null;
		try {
			result = userService.validateUser(user);
			if(result==true)
			{
			messageResponse = new ResponseModel(HttpStatus.OK.toString(), ConstantValue.SUCCESSFUL_LOGIN);
			return new ResponseEntity<String>(new Gson().toJson(messageResponse), HttpStatus.OK);
			}
			else
			{
				messageResponse = new ResponseModel(HttpStatus.CONFLICT.toString(), ConstantValue.UNSUCCESSFUL_LOGIN);
				return new ResponseEntity<String>(new Gson().toJson(messageResponse), HttpStatus.CONFLICT);
			}
		} catch (ServiceException e) {
			FILMLAND_LOG.error(e.getMessage());
		}
		return new ResponseEntity<String>(new Gson().toJson(ConstantValue.BANDWIDTH), HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
	}

	/**
	 * This method will return Available and Subscribed value of a authenticated user 
	 * @param name
	 * 		 {@link value = "name"} Object Containing value name 
	 * @param apiKey
	 * 		 {@link value = "apiKey"}  Object Containing apiKey name 
	 * @return
	 *       Fetch all the ServiceDetail list
	 */
	@GetMapping(path = Path.DETAILS)
	public ResponseEntity<ServiceDetails> fetchUserDetails(@RequestParam(value = "name") String name,
			@RequestHeader(value = "apiKey") String apiKey) {
		String userName = userService.getUserByAPIKey(apiKey);
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(userName)) {
			if (null != name) {
				FILMLAND_LOG.info("Services: {} Successfully fetched all Services",
						restService.fetchAvailableServices(userName));
				return new ResponseEntity<ServiceDetails>(restService.fetchAvailableServices(userName), HttpStatus.OK);
			} else {
				FILMLAND_LOG.info("Services: {} No Services to be fetched");
				return new ResponseEntity<ServiceDetails>(HttpStatus.OK);
			}
		} else {
			FILMLAND_LOG.error("Services: {} Not Authorized ", ConstantValue.UNAUTHORIZED_MESSAGE);
			return new ResponseEntity<ServiceDetails>(HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * This Method will allow you to subscribe a services
	 * @param subscribption
	 * 		  {@link NewSusbscriptionRequest } Object containing subscription details
	 * @param apiKey
	 * 	      {@link value = "apiKey"}  Object Containing apiKey name
	 * @return
	 *        Authorized User has been successfully/not successfully subscribe services 
	 */
	@PostMapping(path = Path.SUBSCRIBE, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> subscribe(@RequestBody NewSusbscriptionRequest subscribption,
			@RequestHeader(value = "apiKey") String apiKey) {
		String userName = userService.getUserByAPIKey(apiKey);
		ResponseModel messageResponse = null;
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(userName)) {
			boolean isAlreadyExists = Boolean.FALSE;

			List<SubscribedServicesResponse> subscribedServiceByName = restService.getSubscribedServiceByName(userName);
			for (SubscribedServicesResponse subscribedService : subscribedServiceByName) {
				if (subscribption.getName().equals(subscribedService.getName())) {
					isAlreadyExists = true;
				}
			}
			if (!isAlreadyExists) {
				SubscribedServices subscribedService = new SubscribedServices(userName, subscribption.getName(), "1",
						"10", new Date(), new Date());
				restService.saveSubscribedService(subscribedService);
				messageResponse = new ResponseModel("Success" + HttpStatus.OK,
						" Subscription item " + subscribedService.getName() + " has been added to the bucket list of "
								+ subscribedService.getUser_name());
				FILMLAND_LOG.info("Services: {} Successfully subscribed services ", subscribedService.getUser_name());
				return new ResponseEntity<String>(new Gson().toJson(messageResponse), HttpStatus.OK);
			} else {
				messageResponse = new ResponseModel("Failed" + HttpStatus.CONFLICT,
						"Requested Subscrition is already subscribed");
				FILMLAND_LOG.warn("Services: {} Requested Subscrition is already subscribed ");
				return new ResponseEntity<String>(new Gson().toJson(messageResponse), HttpStatus.CONFLICT);
			}
		} else {
			messageResponse = new ResponseModel("Failed" + HttpStatus.UNAUTHORIZED, ConstantValue.UNAUTHORIZED_MESSAGE);
			FILMLAND_LOG.error("Services: {} Not Authorized ", ConstantValue.UNAUTHORIZED_MESSAGE);
			return new ResponseEntity<String>(new Gson().toJson(messageResponse), HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * This method will allow you to share a services across registered friends 
	 * @param shareSubscriptionRequest
	 *        {@link ShareSubscriptionRequest} Containing items to be shared across authorized User
	 * @param apiKey
	 *        {@link value = "apiKey"}  Object Containing apiKey name
	 * @return
	 *        shared or unable to share the services
	 */
	@PostMapping(path = Path.SHARESUBSCRIBE, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> ShareSubscription(@RequestBody ShareSubscriptionRequest shareSubscriptionRequest,
			@RequestHeader(value = "apiKey") String apiKey) {
		String userName = userService.getUserByAPIKey(apiKey);
		LoginDatabase registeredDetails = userService.getDetailsByName(shareSubscriptionRequest.getFriend());
		ResponseModel messageResponse = null;
		if (null != registeredDetails) {
			if (org.apache.commons.lang3.StringUtils.isNotEmpty(userName)) {
				boolean isExists = Boolean.FALSE;
				List<SubscribedServicesResponse> subscribedServiceByName = restService
						.getSubscribedServiceByName(userName);
				for (SubscribedServicesResponse subscribedService : subscribedServiceByName) {
					if (shareSubscriptionRequest.getServiceName().equals(subscribedService.getName())) {
						isExists = true;
					}
				}
				if (isExists) {
					restService.updateSubscribedService(shareSubscriptionRequest.getFriend(),
							shareSubscriptionRequest.getServiceName(), userName);
					messageResponse = new ResponseModel("Success" + HttpStatus.OK,
							" Successfully shared the item to " + shareSubscriptionRequest.getFriend());
					FILMLAND_LOG.info("Services: {} Successfully shared the services ",
							shareSubscriptionRequest.getFriend());
					return new ResponseEntity<String>(new Gson().toJson(messageResponse), HttpStatus.OK);
				} else {
					messageResponse = new ResponseModel("Not Available" + HttpStatus.BAD_REQUEST,
							"Requested Subscrition is not available with the existing subscriber :" + userName);
					FILMLAND_LOG
							.warn("Services: {} Requested Subscrition is not available with the existing subscriber");
					return new ResponseEntity<String>(new Gson().toJson(messageResponse), HttpStatus.BAD_REQUEST);
				}
			} else {
				messageResponse = new ResponseModel("Failed" + HttpStatus.UNAUTHORIZED,
						ConstantValue.UNAUTHORIZED_MESSAGE);
				FILMLAND_LOG.error("Services: {} Not Authorized ", ConstantValue.UNAUTHORIZED_MESSAGE);
				return new ResponseEntity<String>(new Gson().toJson(messageResponse), HttpStatus.UNAUTHORIZED);
			}
		} else {
			messageResponse = new ResponseModel("Failed" + HttpStatus.BAD_REQUEST,
					"Requested shared friend not available");
			FILMLAND_LOG.error("Services: {} Requested shared friend not available ",
					shareSubscriptionRequest.getFriend());
			return new ResponseEntity<String>(new Gson().toJson(messageResponse), HttpStatus.BAD_REQUEST);
		}
	}

}
