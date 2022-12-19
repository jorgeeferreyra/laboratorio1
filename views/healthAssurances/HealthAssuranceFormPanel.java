package entrega.views.healthAssurances;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import entrega.entities.HealthAssurance;
import entrega.services.HealthAssuranceService;
import entrega.validation.HealthAssuranceValidation;
import entrega.views.EntityFormPanel;
import entrega.views.InputPanel;

@SuppressWarnings("serial")
public class HealthAssuranceFormPanel extends EntityFormPanel<HealthAssurance> {
	private InputPanel nameInput;
	
	public HealthAssuranceFormPanel(HealthAssuranceService service) {
		super("Nueva Obra Social", service);
	}
		
	public void setEntity(HealthAssurance healthAssurance) {
		this.entity = healthAssurance;
		
		boolean isEditing = healthAssurance instanceof HealthAssurance;
		
		this.setTitle(isEditing ? "Editar Obra Social" : "Nueva Obra Social");
		
		this.nameInput.setFieldText(isEditing ? healthAssurance.getName() : "");
	}
	
	public String getName() {
		return this.nameInput.getTrimmedFieldText();
	}
		
	protected void buildCenter(JPanel panel) {
		this.nameInput = new InputPanel(HealthAssuranceValidation.NAME_LABEL);
		
		panel.add(this.nameInput);
	 }
	
	protected void buildSouth(JPanel panel) {
		panel.add(this.createButton("Guardar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HealthAssuranceService service = (HealthAssuranceService) getService();
				service.saveEntity();
			}
		}));
		
		panel.add(this.createButton("Limpiar campos", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nameInput.setFieldText("");
			}
		}));
		
		panel.add(this.createButton("Cancelar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HealthAssuranceService service = (HealthAssuranceService) getService();
				service.showListPanel();
			}
		}));
	 }
}