package entrega.models;

import java.util.Arrays;
import java.util.List;

import entrega.contracts.DaoModel;
import entrega.contracts.Model;

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

public class User extends Model implements DaoModel {
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

	public String getTable() {
		return "users";
	}
	
	public List<String> getValues() {
		return Arrays.asList(new String[] {
			this.getId().toString(),
			this.betweenSingleQuotes(this.getFirstName()), 
			this.betweenSingleQuotes(this.getLastName()), 
			this.betweenSingleQuotes(this.getIdNumber()), 
			this.betweenSingleQuotes(this.getEmail()),
			this.betweenSingleQuotes(this.getHashedPassword())
		});
	}

	public List<String> getFields() {
		return Arrays.asList(new String[] {"id", "firstName", "lastName", "idNumber", "email", "password"});
	}
}
 