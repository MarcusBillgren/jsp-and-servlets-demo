package com.billgren.beans;

import java.sql.Date;

public class Player {
	
	private int id;
	private Date dob;
	private String firstName;
	private String lastName;
	private String primaryPos;
	
	
	
	public Player(String firstName, String lastName, String primaryPos) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.primaryPos = primaryPos;
	}
	
	public Player(int id, Date dob, String firstName, String lastName, String primaryPos) {
		this.id = id;
		this.dob = dob;
		this.firstName = firstName;
		this.lastName = lastName;
		this.primaryPos = primaryPos;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPrimaryPos() {
		return primaryPos;
	}
	public void setPrimaryPos(String primaryPos) {
		this.primaryPos = primaryPos;
	}
	
	
	

}
