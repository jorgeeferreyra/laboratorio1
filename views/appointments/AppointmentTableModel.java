package entrega.views.appointments;

import entrega.entities.Appointment;
import entrega.services.AppointmentService;
import entrega.views.TableModel;

@SuppressWarnings("serial")
public class AppointmentTableModel extends TableModel<Appointment> {
	private AppointmentService service;
	private static final int ID_COLUMN = 0;
	private static final int DOCTOR_ID_COLUMN = 1;
	private static final int PATIENT_ID_COLUMN = 2;
	private static final int STARTS_AT_COLUMN = 3;
	private static final int DURATION_COLUMN = 4;
	
	public AppointmentTableModel(AppointmentService service) {
		super(new String[] {"ID", "Médico", "Paciente", "Comienzo", "Duración en minutos"});
		this.service = service;
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		Appointment appointment = this.getContent().get(rowIndex);
		
		switch (columnIndex) {
			case AppointmentTableModel.ID_COLUMN:
				return appointment.getId();
			case AppointmentTableModel.DOCTOR_ID_COLUMN:
				return service.getDoctorFullName(appointment);
			case AppointmentTableModel.PATIENT_ID_COLUMN:
				return service.getPatientFullName(appointment);
			case AppointmentTableModel.STARTS_AT_COLUMN:
				return appointment.getStartsAt();
			case AppointmentTableModel.DURATION_COLUMN:
				return appointment.getDuration().toString();
			default:
				return new String("");
		}
	}
}
