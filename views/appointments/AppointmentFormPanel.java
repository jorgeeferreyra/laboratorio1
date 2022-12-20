package entrega.views.appointments;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import entrega.entities.Appointment;
import entrega.services.AppointmentService;
import entrega.validation.AppointmentValidation;
import entrega.views.EntityFormPanel;
import entrega.views.InputPanel;

@SuppressWarnings("serial")
public class AppointmentFormPanel extends EntityFormPanel<Appointment> {
	private InputPanel doctorIdInput;
	private InputPanel patientIdInput;
	private InputPanel startsAtInput;
	private InputPanel durationInput;
	
	public AppointmentFormPanel(AppointmentService service) {
		super("Nuevo Turno", service);
	}
		
	public void setEntity(Appointment appointment) {
		this.entity = appointment;
		
		boolean isEditing = appointment instanceof Appointment;
		
		this.setTitle(isEditing ? "Editar Turno" : "Nuevo Turno");
		
		this.doctorIdInput.setFieldText(isEditing ? appointment.getDoctorId().toString() : "");
		this.patientIdInput.setFieldText(isEditing ? appointment.getPatientId().toString() : "");
		this.startsAtInput.setFieldText(isEditing ? appointment.getStartsAt() : "");
		this.durationInput.setFieldText(isEditing ? appointment.getDuration().toString() : "");
	}
	
	public String getDoctorId() {
		return this.doctorIdInput.getTrimmedFieldText();
	}
	
	public String getPatientId() {
		return this.patientIdInput.getTrimmedFieldText();
	}
	
	public String getStartsAt() {
		return this.startsAtInput.getTrimmedFieldText();
	}
	
	public String getDuration() {
		return this.durationInput.getTrimmedFieldText();
	}
		
	protected void buildCenter(JPanel panel) {
		this.doctorIdInput = new InputPanel(AppointmentValidation.DOCTOR_ID_LABEL);
		this.patientIdInput = new InputPanel(AppointmentValidation.PATIENT_ID_LABEL);
		this.startsAtInput = new InputPanel(AppointmentValidation.STARTS_AT_LABEL);
		this.durationInput = new InputPanel(AppointmentValidation.DURATION_LABEL);
		
		panel.add(this.doctorIdInput);
		panel.add(this.patientIdInput);
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
				doctorIdInput.setFieldText("");
				patientIdInput.setFieldText("");
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