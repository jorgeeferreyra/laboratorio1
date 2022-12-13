package entrega.entities;

/*
 	CREATE TABLE USERS(
 		ID INT PRIMARY KEY,
 		FIRSTNAME VARCHAR(255),
 		LASTNAME VARCHAR(255),
 		IDNUMBER VARCHAR(255),
 		EMAIL VARCHAR(255),
 		PASSWORD VARCHAR(255)
 	);
 */

public class User extends Entity {
	private String firstName;
	private String lastName;
	private String idNumber;
	private String email;
	private String password;
	
	public User() {
		this.setIsNew(true);
		
		this.firstName = "";
		this.lastName = "";
		this.idNumber = "";
		this.email = "";
		this.password = "";
	}
	
	public User(String firstName, String lastName, String idNumber, String email, String password) {
		this.setIsNew(true);
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNumber = idNumber;
		this.email = email;
		this.password = password;
	}
	
	public User(int id, String firstName, String lastName, String idNumber, String email, String password) {
		this.setIsNew(false);
		this.setId(id);
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNumber = idNumber;
		this.email = email;
		this.password = password;
	}
	
	public String toString() {
		return "User {" + System.lineSeparator() +
				" firstName="+ this.firstName + System.lineSeparator() +
				" lastName="+ this.lastName + System.lineSeparator() +
				" idNumber="+ this.idNumber + System.lineSeparator() +
				" email="+ this.email + System.lineSeparator() +
				"}";
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
	
	public String getIdNumber() {
		return this.idNumber;
	}
	
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getHashedPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
 