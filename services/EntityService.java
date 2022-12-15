package entrega.services;

import java.util.List;

import javax.swing.SwingWorker;

import entrega.FrontService;
import entrega.exceptions.DaoException;
import entrega.exceptions.ValidationException;
import entrega.views.EntityFormPanel;
import entrega.views.EntityListPanel;

abstract public class EntityService<T> extends Service {
	private EntityListPanel<T> listPanel;
	private EntityFormPanel<T> formPanel;

	public EntityService(FrontService frontService) {
		super(frontService);
	}
	
	public void showListPanel() {
		SwingWorker<Void, String> swingWorker = new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				try {
					getFrontService().setLoading(true);
					
					listPanel.setContent(getListPanelData());
					
					getFrontService().showPanel(listPanel);
				} catch (DaoException e) {
					getFrontService().handleExceptions(e, "Error cargando el listado");
				} finally {
					getFrontService().setLoading(false);
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
				
				getFrontService().showPanel(formPanel);
				
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
				
				getFrontService().showPanel(formPanel);
				
				return null;
			}
		};
		
		swingWorker.execute();
	}
	
	public void saveEntity() {
		if (this.getFrontService().isLoading()) {
			return;
		}
		
		this.getFrontService().setLoading(true);
		
		SwingWorker<Void, String> swingWorker = new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {			
				try {
					persistEntity();
				} catch (DaoException|ValidationException e) {
					getFrontService().handleExceptions(e, "Error guardando la entidad");
				} finally {
					getFrontService().setLoading(false);
				}
				
				return null;
			}
		};
		
		swingWorker.execute();
	}
	
	public void removeEntity(T entity) {
		if (this.getFrontService().isLoading()) {
			return;
		}

		getFrontService().setLoading(true);
		
		SwingWorker<Void, String> swingWorker = new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				try {
					deleteEntity(entity);
				} catch (DaoException e) {
					getFrontService().handleExceptions(e, "Eliminando la entidad");
				} finally {
					getFrontService().setLoading(false);
				}
				
				showListPanel();
				
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

	public abstract void showIndexPanel();
	protected abstract List<T> getListPanelData() throws DaoException;
	protected abstract void persistEntity() throws DaoException, ValidationException;
	protected abstract void deleteEntity(T entity) throws DaoException;
}
