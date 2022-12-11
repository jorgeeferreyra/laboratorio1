package entrega.validation;

import java.util.ArrayList;

import entrega.exceptions.ValidationException;
import entrega.validation.rules.interfaces.Rule;

public class Validator {
	private ArrayList<Rule> rules = new ArrayList<Rule>();
	
	public Validator addRule(Rule rule) {
		this.rules.add(rule);
		return this;
	}
	
	public void validate(String attribute) throws ValidationException {
		for(int i = 0; i < this.rules.size(); i++) {
			this.rules.get(i).validate(attribute);
		}
	}
}
