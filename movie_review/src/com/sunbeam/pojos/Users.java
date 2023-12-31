package com.sunbeam.pojos;
import java.util.Date;
public class Users {
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String mobile;
	private java.util.Date birth;
	private String password;
	public Users(int id, String firstName, String lastName, String email, String mobile, java.util.Date birth, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobile = mobile;
		this.birth = birth;
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getEmail() {
		return email;

	}

	public void setEmail(String email) {

		this.email = email;

	}

	public String getMobile() {

		return mobile;

	}

	public void setMobile(String mobile) {

		this.mobile = mobile;

	}

	public java.util.Date getBirth() {

		return birth;

	}

	public void setBirth(java.util.Date birth) {

		this.birth = birth;

	}

	public String getPassword() {

		return password;

	}

	public void setPassword(String password) {

		this.password = password;

	}

	@Override

	public String toString() {

		return "Users [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email

				+ ", mobile=" + mobile + ", birth=" + birth + ", password=" + password + "]";

	}

}
