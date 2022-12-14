package entrega.views.doctors;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import entrega.entities.Doctor;
import entrega.services.DoctorService;
import entrega.validation.DoctorValidation;
import entrega.views.EntityFormPanel;
import entrega.views.TextFieldPanel;

@SuppressWarnings("serial")
public class DoctorFormPanel extends EntityFormPanel<Doctor> {
	private TextFieldPanel firstNameInput;
	private TextFieldPanel lastNameInput;
	private TextFieldPanel phoneInput;
	private TextFieldPanel emailInput;
	
	public DoctorFormPanel(DoctorService service) {
		super("Nuevo Médico", service);
	}
		
	public void setEntity(Doctor doctor) {
		this.entity = doctor;
		
		boolean isEditing = doctor instanceof Doctor;
		
		this.setTitle(isEditing ? "Editar Médico" : "Nuevo Médico");
		
		this.firstNameInput.setFieldText(isEditing ? doctor.getFirstName() : "");
		this.lastNameInput.setFieldText(isEditing ? doctor.getLastName() : "");
		this.phoneInput.setFieldText(isEditing ? doctor.getPhone() : "");
		this.emailInput.setFieldText(isEditing ? doctor.getEmail() : "");
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
		this.firstNameInput = new TextFieldPanel(DoctorValidation.FIRST_NAME_LABEL);
		this.lastNameInput = new TextFieldPanel(DoctorValidation.LAST_NAME_LABEL);
		this.phoneInput = new TextFieldPanel(DoctorValidation.PHONE_LABEL);
		this.emailInput = new TextFieldPanel(DoctorValidation.EMAIL_LABEL);
		
		panel.add(this.firstNameInput);
		panel.add(this.lastNameInput);
		panel.add(this.phoneInput);
		panel.add(this.emailInput);
	 }
	
	protected void buildSouth(JPanel panel) {
		panel.add(this.createButton("Guardar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DoctorService service = (DoctorService) getService();
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
				DoctorService service = (DoctorService) getService();
				service.showListPanel();
			}
		}));
	 }
}