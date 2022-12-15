package entrega.views.patients;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import entrega.entities.Patient;
import entrega.services.PatientService;
import entrega.validation.PatientValidation;
import entrega.views.EntityFormPanel;
import entrega.views.InputPanel;

@SuppressWarnings("serial")
public class PatientFormPanel extends EntityFormPanel<Patient> {
	private InputPanel firstNameInput;
	private InputPanel lastNameInput;
	private InputPanel phoneInput;
	private InputPanel emailInput;
	
	public PatientFormPanel(PatientService service) {
		super("Nuevo Paciente", service);
	}
		
	public void setEntity(Patient patient) {
		this.entity = patient;
		
		boolean isEditing = patient instanceof Patient;
		
		this.setTitle(isEditing ? "Editar Paciente" : "Nuevo Paciente");
		
		this.firstNameInput.setFieldText(isEditing ? patient.getFirstName() : "");
		this.lastNameInput.setFieldText(isEditing ? patient.getLastName() : "");
		this.phoneInput.setFieldText(isEditing ? patient.getPhone() : "");
		this.emailInput.setFieldText(isEditing ? patient.getEmail() : "");
	}
	
	public String getFirstName() {
		return this.firstNameInput.getTrimmedFieldText();
	}
	
	public String getLastName() {
		return this.lastNameInput.getTrimmedFieldText();
	}
	
	public String getPhone() {
		return this.phoneInput.getTrimmedFieldText();
	}
	
	public String getEmail() {
		return this.emailInput.getTrimmedFieldText();
	}
		
	protected void buildCenter(JPanel panel) {
		this.firstNameInput = new InputPanel(PatientValidation.FIRST_NAME_LABEL);
		this.lastNameInput = new InputPanel(PatientValidation.LAST_NAME_LABEL);
		this.phoneInput = new InputPanel(PatientValidation.PHONE_LABEL);
		this.emailInput = new InputPanel(PatientValidation.EMAIL_LABEL);
		
		panel.add(this.firstNameInput);
		panel.add(this.lastNameInput);
		panel.add(this.phoneInput);
		panel.add(this.emailInput);
	 }
	
	protected void buildSouth(JPanel panel) {
		panel.add(this.createButton("Guardar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PatientService service = (PatientService) getService();
				service.saveEntity();
			}
		}));
		
		panel.add(this.createButton("Limpiar campos", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				firstNameInput.setFieldText("");
				lastNameInput.setFieldText("");
				phoneInput.setFieldText("");
				emailInput.setFieldText("");
			}
		}));
		
		panel.add(this.createButton("Cancelar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PatientService service = (PatientService) getService();
				service.showListPanel();
			}
		}));
	 }
}