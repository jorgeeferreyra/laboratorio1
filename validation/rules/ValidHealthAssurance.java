package entrega.validation.rules;

import entrega.H2DaoFactory;
import entrega.exceptions.DaoException;
import entrega.exceptions.ValidationException;

public class ValidHealthAssurance implements Rule {
	private String target;
	
	public ValidHealthAssurance(String target) {
		this.target = target;
	}

	@Override
	public boolean valid() {
		try {
			int healthAssuranceId = Integer.valueOf(this.target);
			
			if (H2DaoFactory.getHealthAssuranceDao().getById(healthAssuranceId) == null) {
				return false;
			}
			
			return true;
		} catch (NumberFormatException|DaoException e) {
			return false;
		}
	}

	public void validate(String attribute) throws ValidationException {
		if (!this.valid()) {
			throw new ValidationException("El campo " + attribute + " debe contener el identificador de una Obra Social existente");
		}
	}
}
