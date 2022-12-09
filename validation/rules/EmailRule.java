package entrega.validation.rules;

import java.util.regex.Pattern;

import entrega.exceptions.generic.ValidationException;
import entrega.validation.rules.interfaces.Rule;

public class EmailRule implements Rule {
	private String target;
	
	public EmailRule(String target) {
		this.target = target;
	}

	@Override
	public boolean valid() {
        if (this.target == null) {
        	return false;
        }
        
        String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
                  
        Pattern pattern = Pattern.compile(emailRegex);
        
        return pattern.matcher(this.target).matches();
	}

	public void validate(String attribute) throws ValidationException {
		if (!this.valid()) {
			throw new ValidationException("El campo " + attribute + " debe ser un email valido");
		}
	}
}
