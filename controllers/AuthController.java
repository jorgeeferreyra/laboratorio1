package entrega.controllers;

import javax.swing.SwingWorker;

import entrega.FrontController;
import entrega.contracts.Controller;
import entrega.exceptions.generic.ValidationException;
import entrega.exceptions.repositories.UserDaoException;
import entrega.models.User;
import entrega.repositories.H2DaoFactory;
import entrega.repositories.users.UserDao;
import entrega.views.auth.login.LoginFormPanel;
import entrega.views.auth.register.RegisterFormPanel;

public class AuthController implements Controller {
	private FrontController frontController;
	
	private RegisterFormPanel registerFormPanel;
	private LoginFormPanel loginFormPanel;
	
	public AuthController(FrontController frontController) {
		this.frontController = frontController;
		this.build();
	}
	
	public void build() {
		this.registerFormPanel = new RegisterFormPanel(this);
		this.loginFormPanel = new LoginFormPanel(this);
	}
	
	public void showIndexPanel() {
		this.showLoginFormPanel();
	}
	
	public void showLoginFormPanel() {
		this.frontController.showPanel(this.loginFormPanel);
	}
	
	public void showRegisterFormPanel() {
		this.frontController.showPanel(this.registerFormPanel);
	}
	
	public void login() {
		if (this.frontController.isLoading()) {
			return;
		}
		
		SwingWorker<Void, String> swingWorker = new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				try {
					loginFormPanel.validateInputs();
				} catch (ValidationException e) {
					frontController.showWarning(e.getMessage());
					return null;
				}
				
				String idNumber = loginFormPanel.getIdNumber();
				String password = loginFormPanel.getPassword();
				
				frontController.setLoading(true);
				
				UserDao userDao = H2DaoFactory.getUserDao();
				
				try {
					User user = userDao.getByIdNumberAndPassword(idNumber, password);
					
					if (user instanceof User) {
						frontController.setUser(user);
						frontController.focusDoctorsController();
					} else {
						frontController.showWarning("El documento o contraseña no coinciden");
					}
					
				} catch (UserDaoException e) {
					frontController.handleExceptions(e, "Error al iniciar sesión");
				} finally {
					frontController.setLoading(false);
				}
				return null;
			}
		};
		
		swingWorker.execute();
	}
	
	public void register() {
		if (this.frontController.isLoading()) {
			return;
		}
		
		SwingWorker<Void, String> swingWorker = new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				try {
					registerFormPanel.validateInputs();
				} catch (ValidationException e) {
					frontController.showWarning(e.getMessage());
					return null;
				}
				
				String firstName = registerFormPanel.getFirstName();
				String lastName = registerFormPanel.getLastName();
				String idNumber = registerFormPanel.getIdNumber();
				String email = registerFormPanel.getEmail();
				String password = registerFormPanel.getPassword();
				
				frontController.setLoading(true);
				
				try {
					UserDao userDao = H2DaoFactory.getUserDao();
					
					User user = userDao.getByIdNumber(idNumber);
					
					if (user instanceof User) {
						frontController.showWarning("El documento ingresado ya se encuentra registrado");
					}
					
					user = userDao.getByEmail(email);
					
					if (user instanceof User) {
						frontController.showWarning("El email ingresado ya se encuentra registrado");
					}
					
					user = new User(firstName, lastName, idNumber, email, password);
					
					userDao.save(user);
										
					frontController.setUser(user);
					frontController.focusDoctorsController();
				} catch (UserDaoException e) {
					frontController.handleExceptions(e, "Error al registrar");
				} finally {
					frontController.setLoading(false);
				}
				return null;
			}
		};
		
		swingWorker.execute();
	}
}
