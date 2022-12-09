package entrega.views.doctors;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import entrega.contracts.WithValidateInputs;
import entrega.controllers.DoctorController;
import entrega.exceptions.generic.ValidationException;
import entrega.models.Doctor;
import entrega.validation.Validator;
import entrega.validation.rules.CharCountBetweenRule;
import entrega.validation.rules.EmailRule;
import entrega.views.BorderPanelWithTitle;

public class DoctorFormPanel extends BorderPanelWithTitle implements WithValidateInputs {
	private Doctor doctor;
	
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField phoneField;
	private JTextField emailField;
	
	private String firstNameText = "Nombre";
	private String lastNameText = "Apellido";
	private String phoneText = "Teléfono";
	private String emailText = "Correo Electrónico";
	
	public DoctorFormPanel(DoctorController controller) {
		super("Nuevo Médico", controller);
		
		this.build();
	}
	
	public Doctor getDoctor() {
		return this.doctor;
	}
	
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
		
		boolean isEditing = doctor instanceof Doctor;
		
		this.setTitle(isEditing ? "Editar Médico" : "Nuevo Médico");
		
		this.firstNameField.setText(isEditing ? doctor.getFirstName() : "");
		this.lastNameField.setText(isEditing ? doctor.getLastName() : "");
		this.phoneField.setText(isEditing ? doctor.getPhone() : "");
		this.emailField.setText(isEditing ? doctor.getEmail() : "");
	}
	
	public String getFirstName() {
		return this.firstNameField.getText().trim();
	}
	
	public String getLastName() {
		return this.lastNameField.getText().trim();
	}
	
	public String getPhone() {
		return this.phoneField.getText().trim();
	}
	
	public String getEmail() {
		return this.emailField.getText().trim();
	}
	
	public void build() {
		this.buildBody();
		this.buildFooter();
	}
	
	public void validateInputs() throws ValidationException {
		this.getFirstNameValidator().validate(this.firstNameText);
		this.getLastNameValidator().validate(this.lastNameText);
		this.getPhoneValidator().validate(this.phoneText);
		this.getEmailValidator().validate(this.emailText);
	}
	
	private Validator getFirstNameValidator() {
		return (new Validator())
			.addRule(new CharCountBetweenRule(this.getFirstName(), 3, 15));
	}
	
	private Validator getLastNameValidator() {
		return (new Validator())
			.addRule(new CharCountBetweenRule(this.getLastName(), 3, 15));
	}
	
	private Validator getPhoneValidator() {
		return (new Validator())
			.addRule(new CharCountBetweenRule(this.getPhone(), 7, 8));
	}
	
	private Validator getEmailValidator() {
		return (new Validator())
			.addRule(new EmailRule(this.getEmail()));
	}
	
	private void buildBody() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(5,5));
		
		JLabel firstNameLabel = new JLabel(this.firstNameText);
		JLabel lastNameLabel = new JLabel(this.lastNameText);
		JLabel phoneLabel = new JLabel(this.phoneText);
		JLabel emailLabel = new JLabel(this.emailText);
				
		this.firstNameField = new JTextField("");
		this.lastNameField = new JTextField("");
		this.phoneField = new JTextField("");
		this.emailField = new JTextField("");
		
		panel.add(firstNameLabel);
		panel.add(this.firstNameField);
		
		panel.add(lastNameLabel);
		panel.add(this.lastNameField);
		
		panel.add(phoneLabel);
		panel.add(this.phoneField);
		
		panel.add(emailLabel);
		panel.add(this.emailField);
		
		this.add(panel, BorderLayout.CENTER);
	 }
	
	private void buildFooter() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton saveBtn = new JButton("Guardar");
		JButton resetBtn = new JButton("Limpiar campos");
		JButton cancelBtn = new JButton("Cancelar");

		saveBtn.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					DoctorController controller = (DoctorController) getController();
					controller.saveDoctor();
				}
			}
		);
		
		resetBtn.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					firstNameField.setText("");
					lastNameField.setText("");
					phoneField.setText("");
					emailField.setText("");
				}
			}
		);
		
		cancelBtn.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					DoctorController controller = (DoctorController) getController();
					controller.showDoctorListPanel();
				}
			}
		);

		panel.add(saveBtn);
		panel.add(resetBtn);
		panel.add(cancelBtn);
		
		this.add(panel, BorderLayout.SOUTH);
	 }
}