package entrega.views.auth;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import entrega.services.AuthService;
import entrega.validation.RegisterValidation;
import entrega.views.FormPanel;
import entrega.views.TextFieldPanel;
import entrega.views.PasswordFieldPanel;

@SuppressWarnings("serial")
public class RegisterFormPanel extends FormPanel {
	private TextFieldPanel firstNameInput;
	private TextFieldPanel lastNameInput;
	private TextFieldPanel idNumberInput;
	private TextFieldPanel emailInput;
	private PasswordFieldPanel passwordInput;
	
	public RegisterFormPanel(AuthService service) {
		super("Registrarse", service);
	}
	
	public String getFirstName() {
		return this.firstNameInput.getTrimmedFieldText();
	}
	
	public String getLastName() {
		return this.lastNameInput.getTrimmedFieldText();
	}
	
	public String getIdNumber() {
		return this.idNumberInput.getTrimmedFieldText();
	}
	
	public String getEmail() {
		return this.emailInput.getTrimmedFieldText();
	}
	
	public String getPassword() {
		return this.passwordInput.getFieldText();
	}
	
	protected void buildCenter(JPanel panel) {
		this.firstNameInput = new TextFieldPanel(RegisterValidation.FIRST_NAME_LABEL);
		this.lastNameInput = new TextFieldPanel(RegisterValidation.LAST_NAME_LABEL);
		this.idNumberInput = new TextFieldPanel(RegisterValidation.ID_NUMBER_LABEL);
		this.emailInput = new TextFieldPanel(RegisterValidation.EMAIL_LABEL);
		this.passwordInput = new PasswordFieldPanel(RegisterValidation.PASSWORD_LABEL);
		
		panel.add(this.firstNameInput);
		panel.add(this.lastNameInput);
		panel.add(this.idNumberInput);
		panel.add(this.emailInput);
		panel.add(this.passwordInput);
	 }
	
	protected void buildSouth(JPanel panel) {
		panel.add(this.createButton("Registrarse", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AuthService service = (AuthService) getService();
				service.register();
			}
		}));
		
		panel.add(this.createButton("Limpiar campos", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				firstNameInput.setFieldText("");
				lastNameInput.setFieldText("");
				idNumberInput.setFieldText("");
				emailInput.setFieldText("");
				passwordInput.setFieldText("");
			}
		}));
		
		panel.add(this.createButton("Ya tengo usuario", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AuthService service = (AuthService) getService();
				service.showLoginFormPanel();
			}
		}));
	 }
}