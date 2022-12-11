package entrega.services;

import javax.swing.SwingWorker;

import entrega.FrontService;
import entrega.H2DaoFactory;
import entrega.dao.doctors.DoctorDao;
import entrega.exceptions.DoctorDaoException;
import entrega.exceptions.ValidationException;
import entrega.models.Doctor;
import entrega.views.doctors.DoctorFormPanel;
import entrega.views.doctors.DoctorListPanel;

public class DoctorService implements Service {
	private FrontService frontService;
	private DoctorListPanel doctorListPanel;
	private DoctorFormPanel doctorFormPanel;
	
	public DoctorService(FrontService frontService) {
		this.frontService = frontService;
		this.build();
	}
	
	public void build() {
		this.doctorListPanel = new DoctorListPanel(this);
		this.doctorFormPanel = new DoctorFormPanel(this);
	}

	@Override
	public void showIndexPanel() {
		this.showDoctorListPanel();
	}
	
	public void showDoctorListPanel() {
		SwingWorker<Void, String> swingWorker = new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				try {
					frontService.setLoading(true);
					
					DoctorDao doctorDao = H2DaoFactory.getDoctorDao();
					
					doctorListPanel.setContent(doctorDao.getAll(frontService.getUser().getId()));
					
					frontService.showPanel(doctorListPanel);
				} catch (DoctorDaoException e) {
					frontService.handleExceptions(e, "Error mostrando doctores");
				} finally {
					frontService.setLoading(false);
				}
				
				return null;
			}
		};
		
		swingWorker.execute();
	}
	
	public void showDoctorFormPanel() {	
		SwingWorker<Void, String> swingWorker = new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				doctorFormPanel.setDoctor(null);
				
				frontService.showPanel(doctorFormPanel);
				
				return null;
			}
		};
		
		swingWorker.execute();
	}
	
	public void showDoctorFormPanel(Doctor doctor) {	
		SwingWorker<Void, String> swingWorker = new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				doctorFormPanel.setDoctor(doctor);
				
				frontService.showPanel(doctorFormPanel);
					
				return null;
			}
		};
		
		swingWorker.execute();
	}
	
	public void saveDoctor() {
		if (this.frontService.isLoading()) {
			return;
		}
		
		SwingWorker<Void, String> swingWorker = new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				try {					
					doctorFormPanel.validateInputs();
				} catch (ValidationException e) {
					frontService.showWarning(e.getMessage());
					return null;
				}
				
				String firstName = doctorFormPanel.getFirstName();
				String lastName = doctorFormPanel.getLastName();
				String phone = doctorFormPanel.getPhone();
				String email = doctorFormPanel.getEmail();
				
				frontService.setLoading(true);
				
				Doctor doctor = doctorFormPanel.getDoctor();
				
				if (doctor == null) {
					doctor = new Doctor(frontService.getUser().getId(), firstName, lastName, phone, email);
				} else {
					doctor.setFirstName(firstName);
					doctor.setLastName(lastName);
					doctor.setPhone(phone);
					doctor.setEmail(email);
				}

				try {
					DoctorDao doctorDao = H2DaoFactory.getDoctorDao();
					
					doctorDao.save(doctor);
					
					showDoctorListPanel();
				} catch (DoctorDaoException e) {
					frontService.handleExceptions(e, "Error mostrando doctores");
				} finally {
					frontService.setLoading(false);
				}
				
				return null;
			}
		};
		
		swingWorker.execute();
	}
	
	public void removeDoctor(Doctor doctor) {
		if (this.frontService.isLoading()) {
			return;
		}
		
		if (!this.frontService.showConfirm("¿Está seguro que desea eliminar a "+ doctor.getFirstName() + " " + doctor.getLastName() +"?")) {
			return;
		}
		
		SwingWorker<Void, String> swingWorker = new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				try {
					DoctorDao doctorDao = H2DaoFactory.getDoctorDao();
							
					// TODO: chequear que el doctor no tenga turnos asociados
					
					doctorDao.delete(doctor);
					
					showDoctorListPanel();
				} catch (DoctorDaoException e) {
					frontService.handleExceptions(e, "Error mostrando doctores");
				} finally {
					frontService.setLoading(false);
				}
				
				return null;
			}
		};
		
		swingWorker.execute();
	}
}
