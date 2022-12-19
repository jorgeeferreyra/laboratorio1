package entrega.validation;

import entrega.exceptions.ValidationException;
import entrega.validation.rules.CharCountBetweenRule;
import entrega.validation.rules.EmailRule;
import entrega.validation.rules.IsInt;
import entrega.validation.rules.ValidHealthAssurance;

public class PatientValidation implements Validation {
	public static String HEALTH_ASSURANCE_ID_LABEL = "ID Obra Social";
	public static String FIRST_NAME_LABEL = "Nombre";
	public static String LAST_NAME_LABEL = "Apellido";
	public static String PHONE_LABEL = "Teléfono";
	public static String EMAIL_LABEL = "Correo Electrónico";
	
	private String healthAssuranceId;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	
	public PatientValidation(String healthAssuranceId, String firstName, String lastName, String phone, String email) {
		this.healthAssuranceId = healthAssuranceId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
	}
	
	@Override
	public void validate() throws ValidationException {
		this.getHealthAssuranceIdValidator().validate(HEALTH_ASSURANCE_ID_LABEL);
		this.getFirstNameValidator().validate(FIRST_NAME_LABEL);
		this.getLastNameValidator().validate(LAST_NAME_LABEL);
		this.getPhoneValidator().validate(PHONE_LABEL);
		this.getEmailValidator().validate(EMAIL_LABEL);
	}
	
	private Validator getHealthAssuranceIdValidator() {
		return (new Validator())
				.addRule(new IsInt(this.healthAssuranceId))
				.addRule(new ValidHealthAssurance(this.healthAssuranceId));
	}

	private Validator getFirstNameValidator() {
		return (new Validator()).addRule(new CharCountBetweenRule(this.firstName, 3, 15));
	}
	
	private Validator getLastNameValidator() {
		return (new Validator()).addRule(new CharCountBetweenRule(this.lastName, 3, 15));
	}
	
	private Validator getPhoneValidator() {
		return (new Validator()).addRule(new CharCountBetweenRule(this.phone, 7, 8));
	}
	
	private Validator getEmailValidator() {
		return (new Validator()).addRule(new EmailRule(this.email));
	}
}
