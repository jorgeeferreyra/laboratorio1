package entrega.services;

import java.util.List;

import entrega.FrontService;
import entrega.H2DaoFactory;
import entrega.dao.healthAssurances.HealthAssuranceDao;
import entrega.entities.HealthAssurance;
import entrega.exceptions.DaoException;
import entrega.exceptions.ValidationException;
import entrega.validation.HealthAssuranceValidation;
import entrega.views.healthAssurances.HealthAssuranceFormPanel;
import entrega.views.healthAssurances.HealthAssuranceListPanel;

public class HealthAssuranceService extends EntityService<HealthAssurance> {	
	public HealthAssuranceService(FrontService frontService) {
		super(frontService);
		
		this.setListPanel(new HealthAssuranceListPanel(this));
		this.setFormPanel(new HealthAssuranceFormPanel(this));
	}
	
	@Override
	public void showIndexPanel() {
		this.showListPanel();
	}
		
	protected List<HealthAssurance> getListPanelData() throws DaoException {
		HealthAssuranceDao healthAssuranceDao = H2DaoFactory.getHealthAssuranceDao();
		
		return healthAssuranceDao.getAll(this.getFrontService().getUser().getId());
	}
	
	protected void persistEntity() throws DaoException, ValidationException {
		HealthAssuranceFormPanel formPanel = (HealthAssuranceFormPanel) getFormPanel();
		
		String name = formPanel.getName();
		
		HealthAssuranceValidation validation = new HealthAssuranceValidation(
			name
		);

		validation.validate();
						
		HealthAssurance healthAssurance = formPanel.getEntity();
		
		if (healthAssurance == null) {
			healthAssurance = new HealthAssurance(getFrontService().getUser().getId(), name);
		} else {
			healthAssurance.setName(name);
		}

		HealthAssuranceDao healthAssuranceDao = H2DaoFactory.getHealthAssuranceDao();
		
		healthAssuranceDao.save(healthAssurance);
	}
	
	protected void deleteEntity(HealthAssurance healthAssurance) throws DaoException {
		if (!this.getFrontService().showConfirm("¿Está seguro que desea eliminar a "+ healthAssurance.getName() + "?")) {
			return;
		}
		
		HealthAssuranceDao healthAssuranceDao = H2DaoFactory.getHealthAssuranceDao();
		
		// TODO: chequear que el seguro no tenga pacientes asociados
		
		healthAssuranceDao.delete(healthAssurance);
	}
}
