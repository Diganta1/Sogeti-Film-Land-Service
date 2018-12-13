package com.sogeti.filmLand.dao;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SubscribedServices {

	@Id
	@GeneratedValue
	private int id;
	private String user_name;
	private String name;
	private String subscribedContent;
	private String price;
	private Date subscribedDate;
	private Date startDate;
	
	public SubscribedServices() {
		super();
	}

	public SubscribedServices(String user_name, String name, String subscribedContent, String price,
			Date subscribedDate, Date startDate) {
		super();
		this.user_name = user_name;
		this.name = name;
		this.subscribedContent = subscribedContent;
		this.price = price;
		this.subscribedDate = subscribedDate;
		this.startDate = startDate;
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

	public String getSubscribedContent() {
		return subscribedContent;
	}

	public void setSubscribedContent(String subscribedContent) {
		this.subscribedContent = subscribedContent;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return the subscribedDate
	 */
	public Date getSubscribedDate() {
		return subscribedDate;
	}

	/**
	 * @param subscribedDate the subscribedDate to set
	 */
	public void setSubscribedDate(Date subscribedDate) {
		this.subscribedDate = subscribedDate;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
