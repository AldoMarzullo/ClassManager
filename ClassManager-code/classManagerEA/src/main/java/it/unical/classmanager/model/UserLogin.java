package it.unical.classmanager.model;


import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Class used to handle login request
 * @author edge33
 *
 */
public class UserLogin {


	@Size(min=4,max=20)
	@Pattern(regexp = "^[A-Za-z0-9]+$")
	String username;
	
	@Size(min=6,max=50)
	String password;
	
	public UserLogin() {
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
	
	@Override
	public String toString() {
		return "[" + getUsername() + " " + getPassword() +"]";
	}
}
