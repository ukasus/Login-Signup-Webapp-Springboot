package com.ujjawal.ListingUsersWithSecurity.Dto;

import javax.validation.constraints.Email;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserAddObject {


	@NotEmpty(message="Enter Valid Username")
	private String firstName;
	private String LastName;
	@NotEmpty(message="Invalid Email")
	@Email(message="Invalid Email")
	private String email;
	@Size(message="Invalid PhoneNumber",min=10,max=10)
	private String phoneNumber;
	@NotEmpty(message="Password field can't be blank")
	private String password;
	@NotEmpty(message="Password should match")
	private String confirmPassword;
	
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	

}
