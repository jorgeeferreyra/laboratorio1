package entrega.validation;

import entrega.exceptions.ValidationException;
import entrega.validation.rules.DateRule;
import entrega.validation.rules.GreaterThan;
import entrega.validation.rules.IsInt;
import entrega.validation.rules.ValidDoctor;
import entrega.validation.rules.ValidPatient;

public class AppointmentValidation implements Validation {
	public static String DOCTOR_ID_LABEL = "Médico";
	public static String PATIENT_ID_LABEL = "Paciente";
	public static String STARTS_AT_LABEL = "Comienzo";
	public static String DURATION_LABEL = "Duración en minutos";
	
	private String doctorId;
	private String patientId;
	private String startsAt;
	private String duration;
	
	public AppointmentValidation(String doctorId, String patientId, String startsAt, String duration) {
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.startsAt = startsAt;
		this.duration = duration;
	}
	
	@Override
	public void validate() throws ValidationException {
		this.getDoctorIdValidator().validate(DOCTOR_ID_LABEL);
		this.getPatientIdValidator().validate(PATIENT_ID_LABEL);
		this.getStartsAtValidator().validate(STARTS_AT_LABEL);
		this.getDurationValidator().validate(DURATION_LABEL);
	}
	
	private Validator getDoctorIdValidator() {
		return (new Validator())
				.addRule(new IsInt(this.doctorId))
				.addRule(new ValidDoctor(this.doctorId));
	}
	
	private Validator getPatientIdValidator() {
		return (new Validator())
				.addRule(new IsInt(this.patientId))
				.addRule(new ValidPatient(this.patientId));
	}
	
	private Validator getStartsAtValidator() {
		return (new Validator()).addRule(new DateRule(this.startsAt));
	}
	
	private Validator getDurationValidator() {
		return (new Validator())
				.addRule(new IsInt(this.duration))
				.addRule(new GreaterThan(this.duration, 15));
	}
}
