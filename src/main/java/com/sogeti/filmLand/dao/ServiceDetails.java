package com.sogeti.filmLand.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceDetails {

	private List<AvailableServices> availableServices;
	private List<SubscribedServices> subscribedServices;

	public ServiceDetails(List<AvailableServices> availableServices, List<SubscribedServices> subscribedServices) {
		super();
		this.availableServices = availableServices;
		this.subscribedServices = subscribedServices;
	}

	/**
	 * @return the availableServices
	 */
	public List<AvailableServices> getAvailableServices() {
		if (this.availableServices == null) {
			this.availableServices = new ArrayList<>();
		}
		return availableServices;
	}

	/**
	 * @return the subscribedServices
	 */
	public List<SubscribedServices> getSubscribedServices() {
		if (this.subscribedServices == null) {
			this.subscribedServices = new ArrayList<>();
		}
		return subscribedServices;
	}

}
