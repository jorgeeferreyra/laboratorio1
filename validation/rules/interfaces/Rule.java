package entrega.validation.rules.interfaces;

import entrega.exceptions.ValidationException;

public interface Rule {
	public boolean valid();
	public void validate(String attribute) throws ValidationException;
}
