package entrega.validation.rules;

import entrega.H2DaoFactory;
import entrega.exceptions.DaoException;
import entrega.exceptions.ValidationException;

public class ValidDoctor implements Rule {
	private String target;
	
	public ValidDoctor(String target) {
		this.target = target;
	}

	@Override
	public boolean valid() {
		try {
			int doctorId = Integer.valueOf(this.target);
			
			if (H2DaoFactory.getDoctorDao().getById(doctorId) == null) {
				return false;
			}
			
			return true;
		} catch (NumberFormatException|DaoException e) {
			return false;
		}
	}

	public void validate(String attribute) throws ValidationException {
		if (!this.valid()) {
			throw new ValidationException("El campo " + attribute + " debe contener el identificador de un Médico existente");
		}
	}
}
