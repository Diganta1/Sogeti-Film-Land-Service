package com.sogeti.filmLand.dao;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AvailableServices {

	private String user_name;
	@Id
	private String name;
	private String availableContent;
	private String price;
	
	public AvailableServices(String user_name, String name, String availableContent, String price) {
		super();
		this.user_name = user_name;
		this.name = name;
		this.availableContent = availableContent;
		this.price = price;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
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
