package entrega.validation;

import entrega.exceptions.ValidationException;
import entrega.validation.rules.CharCountBetweenRule;

public class LoginValidation implements Validation {
	public static String ID_NUMBER_LABEL = "Documento";
	public static String PASSWORD_LABEL = "Contraseña";
	
	private String idNumber;
	private String password;
	
	public LoginValidation(String idNumber, String password) {
		this.idNumber = idNumber;
		this.password = password;
	}
	
	@Override
	public void validate() throws ValidationException {
		this.getIdNumberValidator().validate(ID_NUMBER_LABEL);
		this.getPasswordValidator().validate(PASSWORD_LABEL);
	}
	
	private Validator getIdNumberValidator() {
		return (new Validator())
			.addRule(new CharCountBetweenRule(this.idNumber, 7, 8));
	}
	
	private Validator getPasswordValidator() {
		return (new Validator())
			.addRule(new CharCountBetweenRule(this.password, 8, 12));
	}
}
