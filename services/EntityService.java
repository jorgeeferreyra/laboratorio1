package entrega.services;

import java.util.List;

import javax.swing.SwingWorker;

import entrega.FrontService;
import entrega.exceptions.DaoException;
import entrega.views.EntityFormPanel;
import entrega.views.EntityListPanel;

abstract public class EntityService<T> implements Service {
	private FrontService frontService;
	private EntityListPanel<T> listPanel;
	private EntityFormPanel<T> formPanel;

	public EntityService(FrontService frontService) {
		this.frontService = frontService;
	}
	
	public void showListPanel() {
		SwingWorker<Void, String> swingWorker = new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				try {
					frontService.setLoading(true);
					
					listPanel.setContent(getListPanelData());
					
					frontService.showPanel(listPanel);
				} catch (DaoException e) {
					frontService.handleExceptions(e, "Error cargando el listado");
				} finally {
					frontService.setLoading(false);
				}
				
				return null;
			}
		};
		
		swingWorker.execute();
	}
	
	public void showFormPanel() {
		SwingWorker<Void, String> swingWorker = new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				formPanel.setEntity(null);
				
				frontService.showPanel(formPanel);
				
				return null;
			}
		};
		
		swingWorker.execute();
	}
	
	public void showFormPanel(T entity) {
		SwingWorker<Void, String> swingWorker = new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				formPanel.setEntity(entity);
				
				frontService.showPanel(formPanel);
				
				return null;
			}
		};
		
		swingWorker.execute();
	}
	
	protected EntityListPanel<T> getListPanel() {
		return listPanel;
	}

	protected void setListPanel(EntityListPanel<T> listPanel) {
		this.listPanel = listPanel;
	}

	protected EntityFormPanel<T> getFormPanel() {
		return formPanel;
	}

	protected void setFormPanel(EntityFormPanel<T> formPanel) {
		this.formPanel = formPanel;
	}

	protected FrontService getFrontService() {
		return frontService;
	}

	public abstract void showIndexPanel();
	protected abstract List<T> getListPanelData() throws DaoException;
}
