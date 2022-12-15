package entrega.services;

import entrega.FrontService;
import entrega.views.logged.LoggedPanel;

public class LoggedService extends Service {
	private LoggedPanel loggedPanel;
	
	public LoggedService(FrontService frontService) {
		super(frontService);
		this.build();
	}
	
	public void build() {
		this.loggedPanel = new LoggedPanel(this);
	}
	
	public void showIndexPanel() {
		this.showLoggedPanel();
	}
	
	public void showLoggedPanel() {
		this.getFrontService().showPanel(loggedPanel);
	}
}
