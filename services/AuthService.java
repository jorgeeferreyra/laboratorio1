package entrega.services;

import javax.swing.SwingWorker;

import entrega.FrontService;
import entrega.H2DaoFactory;
import entrega.dao.users.UserDao;
import entrega.exceptions.UserDaoException;
import entrega.exceptions.ValidationException;
import entrega.models.User;
import entrega.validation.LoginValidation;
import entrega.validation.RegisterValidation;
import entrega.views.auth.login.LoginFormPanel;
import entrega.views.auth.register.RegisterFormPanel;

public class AuthService implements Service {
	private FrontService frontService;
	
	private RegisterFormPanel registerFormPanel;
	private LoginFormPanel loginFormPanel;
	
	public AuthService(FrontService frontService) {
		this.frontService = frontService;
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
		this.frontService.showPanel(this.loginFormPanel);
	}
	
	public void showRegisterFormPanel() {
		this.frontService.showPanel(this.registerFormPanel);
	}
	
	public void login() {
		if (this.frontService.isLoading()) {
			return;
		}
		
		SwingWorker<Void, String> swingWorker = new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				String idNumber = loginFormPanel.getIdNumber();
				String password = loginFormPanel.getPassword();
				
				LoginValidation validation = new LoginValidation(idNumber, password);
				
				try {
					validation.validate();
				} catch (ValidationException e) {
					frontService.showWarning(e.getMessage());
					return null;
				}
				
				frontService.setLoading(true);
				
				UserDao userDao = H2DaoFactory.getUserDao();
				
				try {
					User user = userDao.getByIdNumberAndPassword(idNumber, password);
					
					if (user instanceof User) {
						frontService.setUser(user);
						frontService.focusDoctorsService();
					} else {
						frontService.showWarning("El documento o contraseña no coinciden");
					}
					
				} catch (UserDaoException e) {
					frontService.handleExceptions(e, "Error al iniciar sesión");
				} finally {
					frontService.setLoading(false);
				}
				return null;
			}
		};
		
		swingWorker.execute();
	}
	
	public void register() {
		if (this.frontService.isLoading()) {
			return;
		}
		
		SwingWorker<Void, String> swingWorker = new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				String firstName = registerFormPanel.getFirstName();
				String lastName = registerFormPanel.getLastName();
				String idNumber = registerFormPanel.getIdNumber();
				String email = registerFormPanel.getEmail();
				String password = registerFormPanel.getPassword();
				
				RegisterValidation validation = new RegisterValidation(firstName, lastName, idNumber, email, password);
				
				try {
					validation.validate();
				} catch (ValidationException e) {
					frontService.showWarning(e.getMessage());
					return null;
				}
				
				frontService.setLoading(true);
				
				try {
					UserDao userDao = H2DaoFactory.getUserDao();
					
					User user = userDao.getByIdNumber(idNumber);
					
					if (user instanceof User) {
						frontService.showWarning("El documento ingresado ya se encuentra registrado");
					}
					
					user = userDao.getByEmail(email);
					
					if (user instanceof User) {
						frontService.showWarning("El email ingresado ya se encuentra registrado");
					}
					
					user = new User(firstName, lastName, idNumber, email, password);
					
					userDao.save(user);
										
					frontService.setUser(user);
					frontService.focusDoctorsService();
				} catch (UserDaoException e) {
					frontService.handleExceptions(e, "Error al registrar");
				} finally {
					frontService.setLoading(false);
				}
				return null;
			}
		};
		
		swingWorker.execute();
	}
}
