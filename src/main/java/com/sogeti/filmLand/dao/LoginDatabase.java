package com.sogeti.filmLand.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LoginDatabase {

	@Id
	@GeneratedValue
	private int id;
	private String email_id;
	private String name;
	private String password;
	private String autoizationKey;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the autoizationKey
	 */
	public String getAutoizationKey() {
		return autoizationKey;
	}

	/**
	 * @param autoizationKey the autoizationKey to set
	 */
	public void setAutoizationKey(String autoizationKey) {
		this.autoizationKey = autoizationKey;
	}

}
