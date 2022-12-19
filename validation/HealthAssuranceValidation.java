package entrega.validation;

import entrega.exceptions.ValidationException;
import entrega.validation.rules.CharCountBetweenRule;

public class HealthAssuranceValidation implements Validation {
	public static String NAME_LABEL = "Nombre";
	
	private String name;
	
	public HealthAssuranceValidation(String name) {
		this.name = name;
	}
	
	@Override
	public void validate() throws ValidationException {
		this.getNameValidator().validate(NAME_LABEL);
	}
	
	private Validator getNameValidator() {
		return (new Validator()).addRule(new CharCountBetweenRule(this.name, 3, 15));
	}
}
