package com.sogeti.autotest.model;

public class User {
	private String username;
	private String password;
	private String repeatPassword;
	private String firstName;
	private String lastName;
	private String title;
	private String gender;
	private String[] birthdate;
	private String phoneCountry;
	private String phoneNumber;
	
	public User withUserName(String username){
		this.username = username;
		return this;
	}
	
	public String getUsername(){
		return username;
	}
	
	public User withPassword(String password){
		this.password = password;
		return this;
	}
	
	public String getPassword(){
		return password;
	}
	
	public User withRepeatPassword(String repeatPassword){
		this.repeatPassword = repeatPassword;
		return this;
	}
	
	public String getRepeatPassword(){
		return repeatPassword;
	}
	
	public User withFirstName(String firstName){
		this.firstName = firstName;
		return this;
	}
	
	public String getFirstName(){
		return firstName;
	}
	public User withLastName(String lastName){
		this.lastName = lastName;
		return this;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public User withTitle(String title){
		this.title = title;
		return this;
	}
	
	public String getTitle(){
		return title;
	}

	public String getGender() {
		return gender;
	}

	public User withGender(String gender) {
		this.gender = gender;
		return this;
	}

	public String[] getBirthdate() {
		return birthdate;
	}

	public User withBirthdate(String[] birthdate) {
		this.birthdate = birthdate;
		return this;
	}

	public String getPhoneCountry() {
		return phoneCountry;
	}

	public User withPhoneCountry(String phoneCountry) {
		this.phoneCountry = phoneCountry;
		return this;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public User withPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}	
}
