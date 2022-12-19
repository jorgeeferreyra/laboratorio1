package entrega.entities;

/*
 	CREATE TABLE HEALTH_ASSURANCES(
 		ID INT PRIMARY KEY,
 		USER_ID INT,
 		NAME VARCHAR(255)
 	);
 */

public class HealthAssurance extends Entity {
	private int userId;
	private String name;
	
	public HealthAssurance() {
		this.setIsNew(true);
		
		this.userId = 0;
		this.name = "";
	}
	
	public HealthAssurance(int userId, String name) {
		this.setIsNew(true);
		
		this.userId = userId;
		this.name = name;
	}
	
	public HealthAssurance(int id, int userId, String name) {
		this.setIsNew(false);
		this.setId(id);

		this.userId = userId;
		this.name = name;
	}

	public Integer getUserId() {
		return Integer.valueOf(this.userId);
	}
		
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
 