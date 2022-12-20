package entrega.services;

import java.util.List;

import entrega.FrontService;
import entrega.H2DaoFactory;
import entrega.dao.appointments.AppointmentDao;
import entrega.dao.doctors.DoctorDao;
import entrega.entities.Doctor;
import entrega.exceptions.DaoException;
import entrega.exceptions.ValidationException;
import entrega.validation.DoctorValidation;
import entrega.views.doctors.DoctorFormPanel;
import entrega.views.doctors.DoctorListPanel;

public class DoctorService extends EntityService<Doctor> {	
	public DoctorService(FrontService frontService) {
		super(frontService);
		
		this.setListPanel(new DoctorListPanel(this));
		this.setFormPanel(new DoctorFormPanel(this));
	}
	
	@Override
	public void showIndexPanel() {
		this.showListPanel();
	}
		
	protected List<Doctor> getListPanelData() throws DaoException {
		DoctorDao doctorDao = H2DaoFactory.getDoctorDao();
		
		return doctorDao.getAll(this.getFrontService().getUser().getId());
	}
	
	protected void persistEntity() throws DaoException, ValidationException {
		DoctorFormPanel formPanel = (DoctorFormPanel) getFormPanel();
		
		String firstName = formPanel.getFirstName();
		String lastName = formPanel.getLastName();
		String phone = formPanel.getPhone();
		String email = formPanel.getEmail();
		
		DoctorValidation validation = new DoctorValidation(
			firstName,
			lastName,
			phone,
			email
		);

		validation.validate();
						
		Doctor doctor = formPanel.getEntity();
		
		if (doctor == null) {
			doctor = new Doctor(getFrontService().getUser().getId(), firstName, lastName, phone, email);
		} else {
			doctor.setFirstName(firstName);
			doctor.setLastName(lastName);
			doctor.setPhone(phone);
			doctor.setEmail(email);
		}

		DoctorDao doctorDao = H2DaoFactory.getDoctorDao();
		
		doctorDao.save(doctor);
	}
	
	protected void deleteEntity(Doctor doctor) throws DaoException {
		if (!this.getFrontService().showConfirm("¿Está seguro que desea eliminar a "+ doctor.getFirstName() + " " + doctor.getLastName() +"?")) {
			return;
		}
		
		AppointmentDao appointmentDao = H2DaoFactory.getAppointmentDao();
		
		if (appointmentDao.getByDoctorId(doctor.getId()).size() > 0) {
			this.getFrontService().showWarning("El doctor seleccionado no puede ser eliminado por tener turnos asociados");
			return;
		}
		
		DoctorDao doctorDao = H2DaoFactory.getDoctorDao();
				
		doctorDao.delete(doctor);
	}
}
