package entrega.entities;

/*
 	CREATE TABLE DOCTORS(
 		ID INT PRIMARY KEY,
 		USER_ID INT,
 		FIRSTNAME VARCHAR(255),
 		LASTNAME VARCHAR(255),
 		PHONE VARCHAR(255),
 		EMAIL VARCHAR(255)
 	);
 */

public class Doctor extends Entity {
	private int userId;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	
	public Doctor() {
		this.setIsNew(true);
		
		this.userId = 0;
		this.firstName = "";
		this.lastName = "";
		this.phone = "";
		this.email = "";
	}
	
	public Doctor(int userId, String firstName, String lastName, String phone, String email) {
		this.setIsNew(true);
		
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
	}
	
	public Doctor(int id, int userId, String firstName, String lastName, String phone, String email) {
		this.setIsNew(false);
		this.setId(id);

		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
	}

	public Integer getUserId() {
		return Integer.valueOf(this.userId);
	}
		
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPhone() {
		return this.phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
 