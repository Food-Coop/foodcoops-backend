package de.dhbw.foodcoop.warehouse.plugins.helpObjects;

public class Einkaufsmanagement {
	
	private String Email;
	private String Username;
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public Einkaufsmanagement(String email, String username) {
		super();
		Email = email;
		Username = username;
	}
	
	
}
