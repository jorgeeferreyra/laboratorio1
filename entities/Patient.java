package entrega.entities;

/*
 	CREATE TABLE PATIENTS(
 		ID INT PRIMARY KEY,
 		USER_ID INT,
 		HEALTH_ASSURANCE_ID INT,
 		FIRSTNAME VARCHAR(255),
 		LASTNAME VARCHAR(255),
 		PHONE VARCHAR(255),
 		EMAIL VARCHAR(255)
 	);
 */

public class Patient extends Entity {
	private int userId;
	private int healthAssuranceId;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	
	public Patient() {
		this.setIsNew(true);
		
		this.userId = 0;
		this.healthAssuranceId = 0;
		this.firstName = "";
		this.lastName = "";
		this.phone = "";
		this.email = "";
	}
	
	public Patient(int userId, int healthAssuranceId, String firstName, String lastName, String phone, String email) {
		this.setIsNew(true);
		
		this.userId = userId;
		this.healthAssuranceId = healthAssuranceId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
	}
	
	public Patient(int id, int userId, int healthAssuranceId, String firstName, String lastName, String phone, String email) {
		this.setIsNew(false);
		this.setId(id);

		this.userId = userId;
		this.healthAssuranceId = healthAssuranceId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
	}

	public Integer getUserId() {
		return Integer.valueOf(this.userId);
	}

	public Integer getHealthAssuranceId() {
		return Integer.valueOf(this.healthAssuranceId);
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

	public void setHealthAssuranceId(int healthAssuranceId) {
		this.healthAssuranceId = healthAssuranceId;
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
	
    @Override
    public String toString() {
        return this.getFirstName() + " " + this.getLastName();
    }
}
 