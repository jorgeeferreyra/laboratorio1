package entrega.validation;

import entrega.exceptions.ValidationException;
import entrega.validation.rules.CharCountBetweenRule;
import entrega.validation.rules.EmailRule;

public class RegisterValidation implements Validation {
	public static String FIRST_NAME_LABEL = "Nombre";
	public static String LAST_NAME_LABEL = "Apellido";
	public static String ID_NUMBER_LABEL = "Documento";
	public static String EMAIL_LABEL = "Correo Electrónico";
	public static String PASSWORD_LABEL = "Contraseña";
	
	private String firstName;
	private String lastName;
	private String idNumber;
	private String email;
	private String password;
	
	public RegisterValidation(String firstName, String lastName, String idNumber, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNumber = idNumber;
		this.email = email;
		this.password = password;
	}
	
	@Override
	public void validate() throws ValidationException {
		this.getFirstNameValidator().validate(FIRST_NAME_LABEL);
		this.getLastNameValidator().validate(LAST_NAME_LABEL);
		this.getIdNumberValidator().validate(ID_NUMBER_LABEL);
		this.getEmailValidator().validate(EMAIL_LABEL);
		this.getPasswordValidator().validate(PASSWORD_LABEL);
	}
	
	private Validator getFirstNameValidator() {
		return (new Validator())
			.addRule(new CharCountBetweenRule(this.firstName, 3, 15));
	}
	
	private Validator getLastNameValidator() {
		return (new Validator())
			.addRule(new CharCountBetweenRule(this.lastName, 3, 15));
	}
	
	private Validator getIdNumberValidator() {
		return (new Validator())
			.addRule(new CharCountBetweenRule(this.idNumber, 7, 8));
	}
	
	private Validator getEmailValidator() {
		return (new Validator())
			.addRule(new EmailRule(this.email));
	}
	
	private Validator getPasswordValidator() {
		return (new Validator())
			.addRule(new CharCountBetweenRule(this.password, 8, 12));
	}
}
