package entrega.contracts;

import entrega.exceptions.generic.ValidationException;

public interface WithValidateInputs {
	public void validateInputs() throws ValidationException;
}
