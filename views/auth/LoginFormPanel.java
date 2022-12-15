package entrega.views.auth;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import entrega.services.AuthService;
import entrega.validation.LoginValidation;
import entrega.views.FormPanel;
import entrega.views.InputPanel;
import entrega.views.PasswordInputPanel;

@SuppressWarnings("serial")
public class LoginFormPanel extends FormPanel {
	private InputPanel idNumberInput;
	private PasswordInputPanel passwordInput;
			
	public LoginFormPanel(AuthService service) {
		super("Ingresar", service);
	}
	
	public String getIdNumber() {
		return this.idNumberInput.getTrimmedFieldText();
	}
	
	public String getPassword() {
		return this.passwordInput.getFieldText();
	}
	
	protected void buildCenter(JPanel panel) {
		this.idNumberInput = new InputPanel(LoginValidation.ID_NUMBER_LABEL);
		this.passwordInput = new PasswordInputPanel(LoginValidation.PASSWORD_LABEL);
		
		panel.add(this.idNumberInput);
		panel.add(this.passwordInput);
	 }
	
	protected void buildSouth(JPanel panel) {
		panel.add(this.createButton("Ingresar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AuthService service = (AuthService) getService();
				service.login();
			}
		}));
		
		panel.add(this.createButton("Limpiar campos", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				idNumberInput.setFieldText("");
				passwordInput.setFieldText("");
			}
		}));
		
		panel.add(this.createButton("Registrarse", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AuthService service = (AuthService) getService();
				service.showRegisterFormPanel();
			}
		}));
	 }
}