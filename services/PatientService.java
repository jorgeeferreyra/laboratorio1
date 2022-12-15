package entrega.services;

import java.util.List;

import entrega.FrontService;
import entrega.H2DaoFactory;
import entrega.dao.patients.PatientDao;
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
		
	protected List<Patient> getListPanelData() throws DaoException {
		PatientDao patientDao = H2DaoFactory.getPatientDao();
		
		return patientDao.getAll(this.getFrontService().getUser().getId());
	}
	
	protected void persistEntity() throws DaoException, ValidationException {
		PatientFormPanel formPanel = (PatientFormPanel) getFormPanel();
		
		String firstName = formPanel.getFirstName();
		String lastName = formPanel.getLastName();
		String phone = formPanel.getPhone();
		String email = formPanel.getEmail();
		
		PatientValidation validation = new PatientValidation(
			firstName,
			lastName,
			phone,
			email
		);

		validation.validate();
						
		Patient patient = formPanel.getEntity();
		
		if (patient == null) {
			patient = new Patient(getFrontService().getUser().getId(), firstName, lastName, phone, email);
		} else {
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
		
		PatientDao patientDao = H2DaoFactory.getPatientDao();
		
		// TODO: chequear que el paciente no tenga turnos asociados
		
		patientDao.delete(patient);
	}
}
