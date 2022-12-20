package entrega.services;

import java.util.List;

import entrega.FrontService;
import entrega.H2DaoFactory;
import entrega.dao.appointments.AppointmentDao;
import entrega.entities.Appointment;
import entrega.exceptions.DaoException;
import entrega.exceptions.ValidationException;
import entrega.validation.AppointmentValidation;
import entrega.views.appointments.AppointmentFormPanel;
import entrega.views.appointments.AppointmentListPanel;

public class AppointmentService extends EntityService<Appointment> {	
	public AppointmentService(FrontService frontService) {
		super(frontService);
		
		this.setListPanel(new AppointmentListPanel(this));
		this.setFormPanel(new AppointmentFormPanel(this));
	}
	
	@Override
	public void showIndexPanel() {
		this.showListPanel();
	}
		
	protected List<Appointment> getListPanelData() throws DaoException {
		AppointmentDao appointmentDao = H2DaoFactory.getAppointmentDao();
		
		return appointmentDao.getAll(this.getFrontService().getUser().getId());
	}
	
	protected void persistEntity() throws DaoException, ValidationException {
		AppointmentFormPanel formPanel = (AppointmentFormPanel) getFormPanel();
		
		String doctorId = formPanel.getDoctorId();
		String patientId = formPanel.getPatientId();
		String startsAt = formPanel.getStartsAt();
		String duration = formPanel.getDuration();
		
		AppointmentValidation validation = new AppointmentValidation(
			doctorId,
			patientId,
			startsAt,
			duration
		);

		validation.validate();
		
		int doctorIdAsInt = Integer.valueOf(doctorId);
		int patientIdAsInt = Integer.valueOf(patientId);
		int durationAsInt = Integer.valueOf(duration);
		
		Appointment appointment = formPanel.getEntity();
		
		if (appointment == null) {
			appointment = new Appointment(getFrontService().getUser().getId(), doctorIdAsInt, patientIdAsInt, startsAt, durationAsInt);
		} else {
			appointment.setDoctorId(doctorIdAsInt);
			appointment.setPatientId(patientIdAsInt);
			appointment.setStartsAt(startsAt);
			appointment.setDuration(durationAsInt);
		}

		AppointmentDao appointmentDao = H2DaoFactory.getAppointmentDao();
		
		appointmentDao.save(appointment);
	}
	
	protected void deleteEntity(Appointment appointment) throws DaoException {
		if (!this.getFrontService().showConfirm("¿Está seguro que desea eliminar el turno de "+ appointment.getStartsAt() +"?")) {
			return;
		}
		
		AppointmentDao appointmentDao = H2DaoFactory.getAppointmentDao();
				
		appointmentDao.delete(appointment);
	}
}
