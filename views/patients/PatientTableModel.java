package entrega.views.patients;

import entrega.entities.Patient;
import entrega.views.TableModel;

@SuppressWarnings("serial")
public class PatientTableModel extends TableModel<Patient> {
	private static final int ID_COLUMN = 0;
	private static final int FIRSTNAME_COLUMN = 1;
	private static final int LASTNAME_COLUMN = 2;
	private static final int PHONE_COLUMN = 3;
	private static final int EMAIL_COLUMN = 4;
	
	protected String[] titles = {"ID", "Nombre", "Apellido", "Teléfono", "Email"};
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		Patient patient = this.getContent().get(rowIndex);
		
		switch (columnIndex) {
			case PatientTableModel.ID_COLUMN:
				return patient.getId();
			case PatientTableModel.FIRSTNAME_COLUMN:
				return patient.getFirstName();
			case PatientTableModel.LASTNAME_COLUMN:
				return patient.getLastName();
			case PatientTableModel.PHONE_COLUMN:
				return patient.getPhone();
			case PatientTableModel.EMAIL_COLUMN:
				return patient.getEmail();
			default:
				return new String("");
		}
	}
}