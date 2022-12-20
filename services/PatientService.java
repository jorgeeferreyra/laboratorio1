package entrega.services;

import java.util.List;

import entrega.FrontService;
import entrega.H2DaoFactory;
import entrega.dao.appointments.AppointmentDao;
import entrega.dao.healthAssurances.HealthAssuranceDao;
import entrega.dao.patients.PatientDao;
import entrega.entities.HealthAssurance;
import entrega.entities.Patient;
import entrega.exceptions.DaoException;
import entrega.exceptions.ValidationException;
import entrega.validation.PatientValidation;
import entrega.views.patients.PatientFormPanel;
import entrega.views.patients.PatientListPanel;

public class PatientService extends EntityService<Patient> {	
	public PatientService(FrontService frontService) {
		super(frontService);
		
		this.setListPanel(new PatientListPanel(this));
		this.setFormPanel(new PatientFormPanel(this));
	}
	
	@Override
	public void showIndexPanel() {
		this.showListPanel();
	}
	
	public String getHealthAssuranceName(Patient patient) {
		try {
			HealthAssuranceDao healthAssuranceDao = H2DaoFactory.getHealthAssuranceDao();
			
			HealthAssurance healthAssurance = healthAssuranceDao.getById(patient.getHealthAssuranceId());
			
			if (healthAssurance == null) {
				return "Obra Social no encontrado";
			}
			
			return healthAssurance.getName();
		} catch (DaoException e) {
			return "Obra Social no encontrado";
		}
	}
		
	protected List<Patient> getListPanelData() throws DaoException {
		PatientDao patientDao = H2DaoFactory.getPatientDao();
		
		return patientDao.getAll(this.getFrontService().getUser().getId());
	}
	
	protected void persistEntity() throws DaoException, ValidationException {
		PatientFormPanel formPanel = (PatientFormPanel) getFormPanel();
		
		String healthAssuranceId = formPanel.getHealthAssuranceId();
		String firstName = formPanel.getFirstName();
		String lastName = formPanel.getLastName();
		String phone = formPanel.getPhone();
		String email = formPanel.getEmail();
		
		PatientValidation validation = new PatientValidation(
			healthAssuranceId,
			firstName,
			lastName,
			phone,
			email
		);

		validation.validate();
						
		Patient patient = formPanel.getEntity();
		
		int healthAssuranceIdAsInt = Integer.valueOf(healthAssuranceId);
		
		if (patient == null) {
			patient = new Patient(getFrontService().getUser().getId(), healthAssuranceIdAsInt, firstName, lastName, phone, email);
		} else {
			patient.setHealthAssuranceId(healthAssuranceIdAsInt);
			patient.setFirstName(firstName);
			patient.setLastName(lastName);
			patient.setPhone(phone);
			patient.setEmail(email);
		}

		PatientDao patientDao = H2DaoFactory.getPatientDao();
		
		patientDao.save(patient);
	}
	
	protected void deleteEntity(Patient patient) throws DaoException {
		if (!this.getFrontService().showConfirm("¿Está seguro que desea eliminar a "+ patient.getFirstName() + " " + patient.getLastName() +"?")) {
			return;
		}
		
		AppointmentDao appointmentDao = H2DaoFactory.getAppointmentDao();
		
		if (appointmentDao.getByPatientId(patient.getId()).size() > 0) {
			this.getFrontService().showWarning("El paciente seleccionado no puede ser eliminado por tener turnos asociados");
			return;
		}
		
		PatientDao patientDao = H2DaoFactory.getPatientDao();
		
		patientDao.delete(patient);
	}
}
