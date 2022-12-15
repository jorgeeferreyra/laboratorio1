package entrega.validation;

import entrega.exceptions.ValidationException;
import entrega.validation.rules.CharCountBetweenRule;
import entrega.validation.rules.EmailRule;

public class PatientValidation implements Validation {
	public static String FIRST_NAME_LABEL = "Nombre";
	public static String LAST_NAME_LABEL = "Apellido";
	public static String PHONE_LABEL = "Teléfono";
	public static String EMAIL_LABEL = "Correo Electrónico";
	
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	
	public PatientValidation(String firstName, String lastName, String phone, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
	}
	
	@Override
	public void validate() throws ValidationException {
		this.getFirstNameValidator().validate(FIRST_NAME_LABEL);
		this.getLastNameValidator().validate(LAST_NAME_LABEL);
		this.getPhoneValidator().validate(PHONE_LABEL);
		this.getEmailValidator().validate(EMAIL_LABEL);
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
