package entrega.views.auth.login;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import entrega.contracts.WithValidateInputs;
import entrega.controllers.AuthController;
import entrega.exceptions.generic.ValidationException;
import entrega.validation.Validator;
import entrega.validation.rules.CharCountBetweenRule;
import entrega.views.BorderPanelWithTitle;

public class LoginFormPanel extends BorderPanelWithTitle implements WithValidateInputs {
	private JTextField idNumberField;
	private JPasswordField passwordField;
	
	private String idNumberText = "Documento";
	private String passwordText = "Contraseña";
	
	public LoginFormPanel(AuthController controller) {
		super("Login", controller);
		
		this.build();
	}
	
	public String getIdNumber() {
		return this.idNumberField.getText().trim();
	}
	
	@SuppressWarnings("deprecation")
	public String getPassword() {
		return this.passwordField.getText();
	}
	
	public void validateInputs() throws ValidationException {
		this.getIdNumberValidator().validate(this.idNumberText);
	}
	
	
	private Validator getIdNumberValidator() {
		return (new Validator())
			.addRule(new CharCountBetweenRule(this.getIdNumber(), 7, 8));
	}
	
	public void build() {
		this.buildBody();
		this.buildFooter();
	}
	
	private void buildBody() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(2,2));
		
		JLabel idNumberLabel = new JLabel(this.idNumberText);
		JLabel passwordLabel = new JLabel(this.passwordText);
		
		this.idNumberField = new JTextField("");
		this.passwordField = new JPasswordField("");
		
		panel.add(idNumberLabel);
		panel.add(idNumberField);
		
		panel.add(passwordLabel);
		panel.add(passwordField);
		
		this.add(panel, BorderLayout.CENTER);
	 }
	
	private void buildFooter() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton loginBtn = new JButton("Ingresar");
		JButton resetBtn = new JButton("Limpiar campos");
		JButton registerBtn = new JButton("Registrarse");

		loginBtn.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					AuthController controller = (AuthController) getController();
					controller.login();
				}
			}
		);
		
		resetBtn.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					idNumberField.setText("");
					passwordField.setText("");
				}
			}
		);
		
		registerBtn.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					AuthController controller = (AuthController) getController();
					controller.showRegisterFormPanel();
				}
			}
		);
		
		panel.add(loginBtn);
		panel.add(resetBtn);
		panel.add(registerBtn);
		
		this.add(panel, BorderLayout.SOUTH);
	 }
}