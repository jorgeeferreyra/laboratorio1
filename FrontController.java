package entrega;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import entrega.contracts.Controller;
import entrega.contracts.Database;
import entrega.controllers.AuthController;
import entrega.controllers.DoctorController;
import entrega.models.User;

/*
 * Antes de comenzar asegurese de crear las tablas necesarias
 * en el paquete entrega.models se encuentran todos los modelos
 * en cada modelo se encuentra la sentencia para crear su tabla
 */

public class FrontController implements Controller {
	private Database database;
	private JFrame frame;
	private boolean loading;
	
	private AuthController authController;
	private DoctorController loggedController;
	
	private User user;

	public static void main(String[] args) {
		FrontController frontController = new FrontController();

		frontController.showIndexPanel();
	}
	
	public FrontController() {
		this.setLoading(true);
		this.build();
		this.setLoading(false);
	}

	@Override
	public void build() {
		this.authController = new AuthController(this);
		this.loggedController = new DoctorController(this);
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}

	@Override
	public void showIndexPanel() {
		/* 
		 * TODO: guardar el usuario logueado en un archivo
		 */
		if (this.getUser() instanceof User) {
			this.focusDoctorsController();
		} else {
			this.focusAuthController();
		}
	}
	
	public void focusAuthController() {
		this.authController.showIndexPanel();
	}
	
	public void focusDoctorsController() {
		this.loggedController.showIndexPanel();
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
}