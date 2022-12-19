package entrega.validation.rules;

import entrega.exceptions.ValidationException;

public class IsInt implements Rule {
	private String target;
	
	public IsInt(String target) {
		this.target = target;
	}

	@Override
	public boolean valid() {
		try {
			Integer.valueOf(this.target);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public void validate(String attribute) throws ValidationException {
		if (!this.valid()) {
			throw new ValidationException("El campo " + attribute + " debe ser un número");
		}
	}
}
