package entrega.entities;

/*
 	CREATE TABLE APPOINTMENTS(
 		ID INT PRIMARY KEY,
 		USER_ID INT,
 		DOCTOR_ID INT,
 		PATIENT_ID INT,
 		STARTS_AT VARCHAR(255),
 		DURATION INT
 	);
 */

public class Appointment extends Entity {
	private int userId;
	private int doctorId;
	private int patientId;
	private String startsAt;
	private int duration;
	
	public Appointment() {
		this.setIsNew(true);
		
		this.userId = 0;
		this.setDoctorId(0);
		this.setPatientId(0);
		this.setStartsAt("");
		this.setDuration(0);
	}
	
	public Appointment(int userId, int doctorId, int patientId, String startsAt, int duration) {
		this.setIsNew(true);
		
		this.userId = userId;
		this.setDoctorId(doctorId);
		this.setPatientId(patientId);
		this.setStartsAt(startsAt);
		this.setDuration(duration);
	}
	
	public Appointment(int id, int userId, int doctorId, int patientId, String startsAt, int duration) {
		this.setIsNew(false);
		this.setId(id);

		this.userId = userId;
		this.setDoctorId(doctorId);
		this.setPatientId(patientId);
		this.setStartsAt(startsAt);
		this.setDuration(duration);
	}

	public Integer getUserId() {
		return Integer.valueOf(this.userId);
	}

	public Integer getDoctorId() {
		return Integer.valueOf(doctorId);
	}

	public Integer getPatientId() {
		return Integer.valueOf(patientId);
	}

	public String getStartsAt() {
		return startsAt;
	}

	public Integer getDuration() {
		return Integer.valueOf(duration);
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public void setStartsAt(String starts_at) {
		this.startsAt = starts_at;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
 