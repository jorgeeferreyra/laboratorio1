package entrega.validation;

import entrega.exceptions.ValidationException;

public interface Validation {
	public void validate() throws ValidationException;
}
