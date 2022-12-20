package entrega.validation.rules;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import entrega.exceptions.ValidationException;

public class DateRule implements Rule {
	private String target;
	
	public DateRule(String target) {
		this.target = target;
	}
	
	@Override
	public boolean valid() {
        if (this.target == null) {
        	return false;
        }
        
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
        
        try {
			formato.parse(this.target);
			
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public void validate(String attribute) throws ValidationException {
		if (!this.valid()) {
			throw new ValidationException("El campo " + attribute + " debe ser una fecha con formato dd/mm/aaaa");
		}
	}
}
