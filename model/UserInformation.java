package com.neosoft.jdbc.model;

public class UserInformation {

	private String name, address, mobile, username, password, transaction;
	private Double deposit;

	public UserInformation(String name, String address, String mobile, String username, String password, Double deposit,
			String transaction) {
		this.name = name;
		this.address = address;
		this.mobile = mobile;
		this.username = username;
		this.password = password;
		this.deposit = deposit;
		this.transaction = transaction;
	}

	public UserInformation(String name, String address, String mobile) {
		this.name = name;
		this.address = address;
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

}
