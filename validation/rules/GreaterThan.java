package entrega.validation.rules;

import entrega.exceptions.ValidationException;

public class GreaterThan implements Rule {
	private String target;
	private int threshold;
	
	public GreaterThan(String target, int threshold) {
		this.target = target;
		this.threshold = threshold;
	}

	@Override
	public boolean valid() {
		try {
			return Integer.valueOf(this.target) > this.threshold;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public void validate(String attribute) throws ValidationException {
		if (!this.valid()) {
			throw new ValidationException("El campo " + attribute + " debe ser mayor a " + Integer.valueOf(this.threshold).toString());
		}
	}
}
