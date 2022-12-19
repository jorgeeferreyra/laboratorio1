package entrega.views.healthAssurances;

import entrega.entities.HealthAssurance;
import entrega.views.TableModel;

@SuppressWarnings("serial")
public class HealthAssuranceTableModel extends TableModel<HealthAssurance> {
	private static final int ID_COLUMN = 0;
	private static final int NAME_COLUMN = 1;
	
	public HealthAssuranceTableModel() {
		super(new String[] {"ID", "Nombre"});
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		HealthAssurance healthAssurance = this.getContent().get(rowIndex);
		
		switch (columnIndex) {
			case HealthAssuranceTableModel.ID_COLUMN:
				return healthAssurance.getId();
			case HealthAssuranceTableModel.NAME_COLUMN:
				return healthAssurance.getName();
			default:
				return new String("");
		}
	}
}
