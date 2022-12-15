package entrega.services;

import javax.swing.SwingWorker;

import entrega.FrontService;
import entrega.H2DaoFactory;
import entrega.dao.users.UserDao;
import entrega.entities.User;
import entrega.exceptions.DaoException;
import entrega.exceptions.ValidationException;
import entrega.validation.LoginValidation;
import entrega.validation.RegisterValidation;
import entrega.views.auth.LoginFormPanel;
import entrega.views.auth.RegisterFormPanel;

public class AuthService extends Service {
	private RegisterFormPanel registerFormPanel;
	private LoginFormPanel loginFormPanel;
	
	public AuthService(FrontService frontService) {
		super(frontService);
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
		this.getFrontService().showPanel(this.loginFormPanel);
	}
	
	public void showRegisterFormPanel() {
		this.getFrontService().showPanel(this.registerFormPanel);
	}
	
	public void login() {
		if (this.getFrontService().isLoading()) {
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
					getFrontService().showWarning(e.getMessage());
					return null;
				}
				
				getFrontService().setLoading(true);
				
				UserDao userDao = H2DaoFactory.getUserDao();
				
				try {
					User user = userDao.getByIdNumberAndPassword(idNumber, password);
					
					if (user instanceof User) {
						getFrontService().setUser(user);
						getFrontService().focusLoggedService();
					} else {
						getFrontService().showWarning("El documento o contraseña no coinciden");
					}
					
				} catch (DaoException e) {
					getFrontService().handleExceptions(e, "Error al iniciar sesión");
				} finally {
					getFrontService().setLoading(false);
				}
				return null;
			}
		};
		
		swingWorker.execute();
	}
	
	public void register() {
		if (this.getFrontService().isLoading()) {
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
					getFrontService().showWarning(e.getMessage());
					return null;
				}
				
				getFrontService().setLoading(true);
				
				try {
					UserDao userDao = H2DaoFactory.getUserDao();
					
					User user = userDao.getByIdNumber(idNumber);
					
					if (user instanceof User) {
						getFrontService().showWarning("El documento ingresado ya se encuentra registrado");
					}
					
					user = userDao.getByEmail(email);
					
					if (user instanceof User) {
						getFrontService().showWarning("El email ingresado ya se encuentra registrado");
					}
					
					user = new User(firstName, lastName, idNumber, email, password);
					
					userDao.save(user);
										
					getFrontService().setUser(user);
					getFrontService().focusLoggedService();
				} catch (DaoException e) {
					getFrontService().handleExceptions(e, "Error al registrar");
				} finally {
					getFrontService().setLoading(false);
				}
				return null;
			}
		};
		
		swingWorker.execute();
	}
}
