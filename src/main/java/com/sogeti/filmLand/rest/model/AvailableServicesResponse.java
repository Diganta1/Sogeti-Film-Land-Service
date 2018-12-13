package com.sogeti.filmLand.rest.model;

public class AvailableServicesResponse {

	private String name;
	private String availableContent;
	private String price;
	
	public AvailableServicesResponse(String name, String availableContent, String price) {
		super();
		this.name = name;
		this.availableContent = availableContent;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvailableContent() {
		return availableContent;
	}

	public void setAvailableContent(String availablecontent) {
		this.availableContent = availablecontent;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
