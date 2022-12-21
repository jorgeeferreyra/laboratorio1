package entrega;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import entrega.entities.User;
import entrega.services.AppointmentService;
import entrega.services.AuthService;
import entrega.services.DoctorService;
import entrega.services.HealthAssuranceService;
import entrega.services.LoggedService;
import entrega.services.PatientService;
import entrega.services.Service;

/*
 * Antes de comenzar asegúrese de crear las tablas necesarias
 * en el paquete entrega.models se encuentran todos las entidades
 * en cada entidad se encuentra la sentencia para crear su tabla
 */

public class FrontService {
	private JFrame frame;
	private boolean loading;
	
	private AuthService authService;
	private LoggedService loggedService;
	private DoctorService doctorService;
	private PatientService patientService;
	private HealthAssuranceService healthAssuranceService;
	private AppointmentService appointmentService;
	
	private User user;

	public static void main(String[] args) {
		FrontService frontService = new FrontService();

		frontService.bootstrap();
	}
	
	public FrontService() {
		this.setLoading(true);
		
		this.authService = new AuthService(this);
		this.loggedService = new LoggedService(this);
		this.doctorService = new DoctorService(this);
		this.patientService = new PatientService(this);
		this.healthAssuranceService = new HealthAssuranceService(this);
		this.appointmentService = new AppointmentService(this);
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		this.setLoading(false);
	}

	public void bootstrap() {
		this.loadRememberedUser();
		if (this.getUser() instanceof User) {
			this.focusLoggedService();
		} else {
			this.focusAuthService();;
		}
	}
	
	public void focusAuthService() {
		this.focusService(this.authService);
	}
	
	public void focusLoggedService() {
		this.focusService(this.loggedService);
	}
	
	public void focusDoctorsService() {
		this.focusService(this.doctorService);
	}
	
	public void focusPatientsService() {
		this.focusService(this.patientService);
	}

	public void focusHealthAssurancesService() {
		this.focusService(this.healthAssuranceService);
	}

	public void focusAppointmentsService() {
		this.focusService(this.appointmentService);
	}

	public void showPanel(JPanel panel) {
		this.frame.getContentPane().removeAll();
		this.frame.getContentPane().add(panel);
		this.frame.getContentPane().validate();
		this.frame.getContentPane().repaint();
		this.frame.pack();
		this.frame.setVisible(true);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isLoading() {
		return loading;
	}

	public void setLoading(boolean loading) {
		this.loading = loading;
	}
	
	public void handleExceptions(Exception e, String title) {
		this.showError(title, e.getMessage());
		e.printStackTrace();
	}
	
	public void showInformation(String message) {
		this.showInformation("Información", message);
	}
	
	public void showInformation(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void showWarning(String message) {
		this.showWarning("Espere", message);
	}
	
	public void showWarning(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
	}
	
	public void showError(String message) {
		this.showError("Error", message);
	}
	
	public void showError(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public boolean showConfirm(String message) {
		return this.showConfirm("Espere", message);
	}
	
	public boolean showConfirm(String title, String message) {
		return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}
	
	private void loadRememberedUser() {
		// TODO: traer el usuario logueado/recordado desde algun medio: archivo o base de datos
	}
	
	private void focusService(Service service) {
		service.showIndexPanel();
	}
}