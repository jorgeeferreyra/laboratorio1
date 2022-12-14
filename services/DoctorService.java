package entrega.services;

import java.util.List;

import javax.swing.SwingWorker;

import entrega.FrontService;
import entrega.H2DaoFactory;
import entrega.dao.doctors.DoctorDao;
import entrega.entities.Doctor;
import entrega.exceptions.DaoException;
import entrega.exceptions.ValidationException;
import entrega.validation.DoctorValidation;
import entrega.views.doctors.DoctorFormPanel;
import entrega.views.doctors.DoctorListPanel;

public class DoctorService extends EntityService<Doctor> implements Service {	
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
	
	public void saveDoctor() {
		if (this.getFrontService().isLoading()) {
			return;
		}
		
		SwingWorker<Void, String> swingWorker = new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
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
				
				try {
					validation.validate();
				} catch (ValidationException e) {
					getFrontService().showWarning(e.getMessage());
					return null;
				}
				
				getFrontService().setLoading(true);
				
				Doctor doctor = formPanel.getEntity();
				
				if (doctor == null) {
					doctor = new Doctor(getFrontService().getUser().getId(), firstName, lastName, phone, email);
				} else {
					doctor.setFirstName(firstName);
					doctor.setLastName(lastName);
					doctor.setPhone(phone);
					doctor.setEmail(email);
				}

				try {
					DoctorDao doctorDao = H2DaoFactory.getDoctorDao();
					
					doctorDao.save(doctor);
					
					showListPanel();
				} catch (DaoException e) {
					getFrontService().handleExceptions(e, "Error mostrando doctores");
				} finally {
					getFrontService().setLoading(false);
				}
				
				return null;
			}
		};
		
		swingWorker.execute();
	}
	
	public void removeDoctor(Doctor doctor) {
		if (this.getFrontService().isLoading()) {
			return;
		}
		
		if (!this.getFrontService().showConfirm("¿Está seguro que desea eliminar a "+ doctor.getFirstName() + " " + doctor.getLastName() +"?")) {
			return;
		}
		
		SwingWorker<Void, String> swingWorker = new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				try {
					DoctorDao doctorDao = H2DaoFactory.getDoctorDao();
							
					// TODO: chequear que el doctor no tenga turnos asociados
					
					doctorDao.delete(doctor);
					
					showListPanel();
				} catch (DaoException e) {
					getFrontService().handleExceptions(e, "Error mostrando doctores");
				} finally {
					getFrontService().setLoading(false);
				}
				
				return null;
			}
		};
		
		swingWorker.execute();
	}
}
