package entrega.views.appointments;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;

import entrega.entities.Appointment;
import entrega.entities.Doctor;
import entrega.entities.Patient;
import entrega.services.AppointmentService;
import entrega.validation.AppointmentValidation;
import entrega.views.EntityComboBoxPanel;
import entrega.views.EntityFormPanel;
import entrega.views.TextFieldPanel;

@SuppressWarnings("serial")
public class AppointmentFormPanel extends EntityFormPanel<Appointment> {
	private EntityComboBoxPanel<Doctor> doctorInput;
	private EntityComboBoxPanel<Patient> patientInput;
	private TextFieldPanel startsAtInput;
	private TextFieldPanel durationInput;
	
	public AppointmentFormPanel(AppointmentService service) {
		super("Nuevo Turno", service);
	}
	
	public void setDoctors(List<Doctor> doctors) {
		this.doctorInput.setEntities(doctors);
	}
	
	public void setPatients(List<Patient> patients) {
		this.patientInput.setEntities(patients);
	}
		
	public void setEntity(Appointment appointment) {
		this.entity = appointment;
		
		boolean isEditing = appointment instanceof Appointment;
		
		this.setTitle(isEditing ? "Editar Turno" : "Nuevo Turno");
		
		this.doctorInput.setSelectedEntityId(isEditing ? appointment.getDoctorId() : 0);
		this.patientInput.setSelectedEntityId(isEditing ? appointment.getPatientId() : 0);
		this.startsAtInput.setFieldText(isEditing ? appointment.getStartsAt() : "");
		this.durationInput.setFieldText(isEditing ? appointment.getDuration().toString() : "");
	}
	
	public String getDoctorId() {
		return this.doctorInput.getSelectedEntityId().toString();
	}
	
	public String getPatientId() {
		return this.patientInput.getSelectedEntityId().toString();
	}
	
	public String getStartsAt() {
		return this.startsAtInput.getTrimmedFieldText();
	}
	
	public String getDuration() {
		return this.durationInput.getTrimmedFieldText();
	}
		
	protected void buildCenter(JPanel panel) {
		this.doctorInput = new EntityComboBoxPanel<Doctor>(AppointmentValidation.DOCTOR_ID_LABEL);
		this.patientInput = new EntityComboBoxPanel<Patient>(AppointmentValidation.PATIENT_ID_LABEL);
		this.startsAtInput = new TextFieldPanel(AppointmentValidation.STARTS_AT_LABEL);
		this.durationInput = new TextFieldPanel(AppointmentValidation.DURATION_LABEL);
		
		panel.add(this.doctorInput);
		panel.add(this.patientInput);
		panel.add(this.startsAtInput);
		panel.add(this.durationInput);
	 }
	
	protected void buildSouth(JPanel panel) {
		panel.add(this.createButton("Guardar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppointmentService service = (AppointmentService) getService();
				service.saveEntity();
			}
		}));
		
		panel.add(this.createButton("Limpiar campos", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doctorInput.setSelectedEntityId(0);
				patientInput.setSelectedEntityId(0);
				startsAtInput.setFieldText("");
				durationInput.setFieldText("");
			}
		}));
		
		panel.add(this.createButton("Cancelar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppointmentService service = (AppointmentService) getService();
				service.showListPanel();
			}
		}));
	 }
}