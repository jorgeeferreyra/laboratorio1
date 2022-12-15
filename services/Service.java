package entrega.services;

import entrega.FrontService;

abstract public class Service {
	private FrontService frontService;

	protected Service(FrontService frontService) {
		this.frontService = frontService;
	}
	
	public FrontService getFrontService() {
		return frontService;
	}
	
	public abstract void showIndexPanel();
	
}