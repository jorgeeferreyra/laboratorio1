package entrega.views;

import entrega.services.Service;

@SuppressWarnings("serial")
abstract public class EntityFormPanel<T> extends FormPanel {
	protected EntityFormPanel(String title, Service service) {
		super(title, service);
	}

	protected T entity;
	
	public T getEntity() {
		return entity;
	}
	

	public abstract void setEntity(T entity);
}
