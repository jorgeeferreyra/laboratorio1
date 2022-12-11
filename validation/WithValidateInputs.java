package entrega.validation;

import entrega.exceptions.ValidationException;

public interface WithValidateInputs {
	public void validateInputs() throws ValidationException;
}
