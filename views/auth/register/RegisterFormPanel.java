package entrega.views.auth.register;

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

import entrega.exceptions.ValidationException;
import entrega.services.AuthService;
import entrega.validation.Validator;
import entrega.validation.WithValidateInputs;
import entrega.validation.rules.CharCountBetweenRule;
import entrega.validation.rules.EmailRule;
import entrega.views.BorderPanelWithTitle;

public class RegisterFormPanel extends BorderPanelWithTitle implements WithValidateInputs {
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField idNumberField;
	private JTextField emailField;
	private JPasswordField passwordField;
	
	private String firstNameText = "Nombre";
	private String lastNameText = "Apellido";
	private String idNumberText = "Documento";
	private String emailText = "Correo Electrónico";
	private String passwordText = "Contraseña";
	
	public RegisterFormPanel(AuthService service) {
		super("Registrarse", service);
		
		this.build();
	}
	
	public String getFirstName() {
		return this.firstNameField.getText().trim();
	}
	
	public String getLastName() {
		return this.lastNameField.getText().trim();
	}
	
	public String getIdNumber() {
		return this.idNumberField.getText().trim();
	}
	
	public String getEmail() {
		return this.emailField.getText().trim();
	}
	
	@SuppressWarnings("deprecation")
	public String getPassword() {
		return this.passwordField.getText();
	}
	
	public void build() {
		this.buildBody();
		this.buildFooter();
	}
	
	public void validateInputs() throws ValidationException {
		this.getFirstNameValidator().validate(this.firstNameText);
		this.getLastNameValidator().validate(this.lastNameText);
		this.getIdNumberValidator().validate(this.idNumberText);
		this.getEmailValidator().validate(this.emailText);
		this.getPasswordValidator().validate(this.passwordText);
	}
	
	private Validator getFirstNameValidator() {
		return (new Validator())
			.addRule(new CharCountBetweenRule(this.getFirstName(), 3, 15));
	}
	
	private Validator getLastNameValidator() {
		return (new Validator())
			.addRule(new CharCountBetweenRule(this.getLastName(), 3, 15));
	}
	
	private Validator getIdNumberValidator() {
		return (new Validator())
			.addRule(new CharCountBetweenRule(this.getIdNumber(), 7, 8));
	}
	
	private Validator getEmailValidator() {
		return (new Validator())
			.addRule(new EmailRule(this.getEmail()));
	}
	
	private Validator getPasswordValidator() {
		return (new Validator())
			.addRule(new CharCountBetweenRule(this.getPassword(), 8, 12));
	}
	
	private void buildBody() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(5,5));
		
		JLabel firstNameLabel = new JLabel(this.firstNameText);
		JLabel lastNameLabel = new JLabel(this.lastNameText);
		JLabel idNumberLabel = new JLabel(this.idNumberText);
		JLabel emailLabel = new JLabel(this.emailText);
		JLabel passwordLabel = new JLabel(this.passwordText);
		
		this.firstNameField = new JTextField("");
		this.lastNameField = new JTextField("");
		this.idNumberField = new JTextField("");
		this.emailField = new JTextField("");
		this.passwordField = new JPasswordField("");
		
		panel.add(firstNameLabel);
		panel.add(this.firstNameField);
		
		panel.add(lastNameLabel);
		panel.add(this.lastNameField);
		
		panel.add(idNumberLabel);
		panel.add(this.idNumberField);
		
		panel.add(emailLabel);
		panel.add(this.emailField);
		
		panel.add(passwordLabel);
		panel.add(this.passwordField);
		
		this.add(panel, BorderLayout.CENTER);
	 }
	
	private void buildFooter() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton registerBtn = new JButton("Registrarse");
		JButton resetBtn = new JButton("Limpiar campos");
		JButton loginBtn = new JButton("Ya tengo usuario");

		registerBtn.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					AuthService service = (AuthService) getService();
					service.register();
				}
			}
		);
		
		resetBtn.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					firstNameField.setText("");
					lastNameField.setText("");
					idNumberField.setText("");
					emailField.setText("");
					passwordField.setText("");
				}
			}
		);
		
		loginBtn.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					AuthService service = (AuthService) getService();
					service.showLoginFormPanel();
				}
			}
		);

		panel.add(registerBtn);
		panel.add(resetBtn);
		panel.add(loginBtn);
		
		this.add(panel, BorderLayout.SOUTH);
	 }
}