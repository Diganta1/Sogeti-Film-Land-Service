package com.sogeti.filmLand.rest.model;

public class ShareSubscriptionRequest {

	private String friend;
	private String serviceName;

	/**
	 * @return the friend
	 */
	public String getFriend() {
		return friend;
	}

	/**
	 * @param friend the friend to set
	 */
	public void setFriend(String friend) {
		this.friend = friend;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}
