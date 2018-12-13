package com.sogeti.filmLand.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sogeti.filmLand.controller.AvailableServiceController;
import com.sogeti.filmLand.controller.SubscribedServiceController;
import com.sogeti.filmLand.dao.AvailableServices;
import com.sogeti.filmLand.dao.ServiceDetails;
import com.sogeti.filmLand.dao.SubscribedServices;
import com.sogeti.filmLand.rest.model.SubscribedServicesResponse;


@Service
public class RestService {

	@Autowired
	private AvailableServiceController availableServiceController;
	
	@Autowired
	private SubscribedServiceController subscribedServiceController;
	
	public ServiceDetails fetchAvailableServices(String name) {
		List<AvailableServices> availableService = (List<AvailableServices>) availableServiceController.findAvailableServiceByName(name);
		List<SubscribedServices> subscribedService = (List<SubscribedServices>) subscribedServiceController.findSubScriberByName(name);
		return new ServiceDetails(availableService, subscribedService);
	}
	
	public List<SubscribedServices> getSubscribedService() {
		return (List<SubscribedServices>) subscribedServiceController.findAll();
	}
	
	public List<SubscribedServicesResponse> getSubscribedServiceByName(String userName) {
		return (List<SubscribedServicesResponse>) subscribedServiceController.findSubScriberByName(userName);
	}
	
	public void saveSubscribedService(SubscribedServices subscribedServices) {
		subscribedServiceController.save(subscribedServices);
	}
	public void updateSubscribedService(String newSubscriber, String subscribedName, String userName) {
		subscribedServiceController.updateNewSubscriber(newSubscriber, subscribedName, userName);
	}
}
