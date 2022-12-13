package entrega.views.patients;

import entrega.entities.Doctor;
import entrega.views.TableModel;

@SuppressWarnings("serial")
public class PatientTableModel extends TableModel<Doctor> {
	private static final int ID_COLUMN = 0;
	private static final int FIRSTNAME_COLUMN = 1;
	private static final int LASTNAME_COLUMN = 2;
	private static final int PHONE_COLUMN = 3;
	private static final int EMAIL_COLUMN = 4;
	
	protected String[] titles = {"ID", "Nombre", "Apellido", "Teléfono", "Email"};
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		Doctor doctor = this.getContent().get(rowIndex);
		
		switch (columnIndex) {
			case PatientTableModel.ID_COLUMN:
				return doctor.getId();
			case PatientTableModel.FIRSTNAME_COLUMN:
				return doctor.getFirstName();
			case PatientTableModel.LASTNAME_COLUMN:
				return doctor.getLastName();
			case PatientTableModel.PHONE_COLUMN:
				return doctor.getPhone();
			case PatientTableModel.EMAIL_COLUMN:
				return doctor.getEmail();
			default:
				return new String("");
		}
	}
}
