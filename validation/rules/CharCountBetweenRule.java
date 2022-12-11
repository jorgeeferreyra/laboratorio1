package entrega.validation.rules;

import entrega.exceptions.ValidationException;
import entrega.validation.rules.interfaces.Rule;

public class CharCountBetweenRule implements Rule {
	private String target;
	private int min;
	private int max;
	
	public CharCountBetweenRule(String target, int min, int max) {
		this.target = target;
		this.min = min;
		this.max = max;
	}

	@Override
	public boolean valid() {
		int charCount = this.target.length();
		return charCount >= this.min && charCount <= max;
	}

	public void validate(String attribute) throws ValidationException {
		if (!this.valid()) {
			throw new ValidationException("La cantidad de caracteres de " + attribute + " debe ser entre " + String.valueOf(this.min) + " y " + String.valueOf(this.max));
		}
	}
}
