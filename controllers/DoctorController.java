package entrega.controllers;

import javax.swing.SwingWorker;

import entrega.FrontController;
import entrega.contracts.Controller;
import entrega.exceptions.generic.ValidationException;
import entrega.exceptions.repositories.DoctorDaoException;
import entrega.models.Doctor;
import entrega.repositories.Repositories;
import entrega.repositories.doctors.DoctorDao;
import entrega.views.doctors.DoctorFormPanel;
import entrega.views.doctors.DoctorListPanel;

public class DoctorController implements Controller {
	private FrontController frontController;
	private DoctorListPanel doctorListPanel;
	private DoctorFormPanel doctorFormPanel;
	
	public DoctorController(FrontController frontController) {
		this.frontController = frontController;
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
					frontController.setLoading(true);
					
					DoctorDao doctorRepository = Repositories.getDoctorRepository();
					
					doctorListPanel.setContent(doctorRepository.getAll(frontController.getUser().getId()));
					
					frontController.showPanel(doctorListPanel);
				} catch (DoctorDaoException e) {
					frontController.handleExceptions(e, "Error mostrando doctores");
				} finally {
					frontController.setLoading(false);
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
				
				frontController.showPanel(doctorFormPanel);
				
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
				
				frontController.showPanel(doctorFormPanel);
					
				return null;
			}
		};
		
		swingWorker.execute();
	}
	
	public void saveDoctor() {
		if (this.frontController.isLoading()) {
			return;
		}
		
		SwingWorker<Void, String> swingWorker = new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				try {					
					doctorFormPanel.validateInputs();
				} catch (ValidationException e) {
					frontController.showWarning(e.getMessage());
					return null;
				}
				
				String firstName = doctorFormPanel.getFirstName();
				String lastName = doctorFormPanel.getLastName();
				String phone = doctorFormPanel.getPhone();
				String email = doctorFormPanel.getEmail();
				
				frontController.setLoading(true);
				
				Doctor doctor = doctorFormPanel.getDoctor();
				
				if (doctor == null) {
					doctor = new Doctor(frontController.getUser().getId(), firstName, lastName, phone, email);
				} else {
					doctor.setFirstName(firstName);
					doctor.setLastName(lastName);
					doctor.setPhone(phone);
					doctor.setEmail(email);
				}

				try {
					DoctorDao doctorRepository = Repositories.getDoctorRepository();
					
					doctorRepository.save(doctor);
					
					showDoctorListPanel();
				} catch (DoctorDaoException e) {
					frontController.handleExceptions(e, "Error mostrando doctores");
				} finally {
					frontController.setLoading(false);
				}
				
				return null;
			}
		};
		
		swingWorker.execute();
	}
	
	public void removeDoctor(Doctor doctor) {
		if (this.frontController.isLoading()) {
			return;
		}
		
		if (!this.frontController.showConfirm("¿Está seguro que desea eliminar a "+ doctor.getFirstName() + " " + doctor.getLastName() +"?")) {
			return;
		}
		
		SwingWorker<Void, String> swingWorker = new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				try {
					DoctorDao doctorRepository = Repositories.getDoctorRepository();
							
					// TODO: chequear que el doctor no tenga turnos asociados
					
					doctorRepository.delete(doctor);
					
					showDoctorListPanel();
				} catch (DoctorDaoException e) {
					frontController.handleExceptions(e, "Error mostrando doctores");
				} finally {
					frontController.setLoading(false);
				}
				
				return null;
			}
		};
		
		swingWorker.execute();
	}
}
