package entrega.views.healthAssurances;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import entrega.entities.HealthAssurance;
import entrega.services.HealthAssuranceService;
import entrega.views.EntityListPanel;

@SuppressWarnings("serial")
public class HealthAssuranceListPanel extends EntityListPanel<HealthAssurance> {
	public HealthAssuranceListPanel(HealthAssuranceService service) {
		super("Obras sociales", service, new HealthAssuranceTableModel());
	}
	
	protected void buildSidebar(JPanel panel) {
		panel.add(this.createButton("Agregar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HealthAssuranceService service = (HealthAssuranceService) getService();
				service.showFormPanel();
			}
		}));
		
		panel.add(this.createButton("Editar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int healthAssuranceIndex = getTable().getSelectedRow();
				
				if (healthAssuranceIndex > -1) {
					HealthAssurance healthAssurance = getModel().getContent().get(healthAssuranceIndex);
					HealthAssuranceService service = (HealthAssuranceService) getService();
					service.showFormPanel(healthAssurance);
				}
			}
		}));
		
		panel.add(this.createButton("Quitar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int healthAssuranceIndex = getTable().getSelectedRow();
				
				if (healthAssuranceIndex > -1) {
					HealthAssurance healthAssurance = getModel().getContent().get(healthAssuranceIndex);
					HealthAssuranceService service = (HealthAssuranceService) getService();
					service.removeEntity(healthAssurance);
				}
			}
		}));
	}

}
