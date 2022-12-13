package entrega.views.doctors;

import entrega.entities.Doctor;
import entrega.views.TableModel;

@SuppressWarnings("serial")
public class DoctorTableModel extends TableModel<Doctor> {
	private static final int ID_COLUMN = 0;
	private static final int FIRSTNAME_COLUMN = 1;
	private static final int LASTNAME_COLUMN = 2;
	private static final int PHONE_COLUMN = 3;
	private static final int EMAIL_COLUMN = 4;
	
	protected String[] titles = {"ID", "Nombre", "Apellido", "Teléfono", "Email"};
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		Doctor doctor = this.getContent().get(rowIndex);
		
		switch (columnIndex) {
			case DoctorTableModel.ID_COLUMN:
				return doctor.getId();
			case DoctorTableModel.FIRSTNAME_COLUMN:
				return doctor.getFirstName();
			case DoctorTableModel.LASTNAME_COLUMN:
				return doctor.getLastName();
			case DoctorTableModel.PHONE_COLUMN:
				return doctor.getPhone();
			case DoctorTableModel.EMAIL_COLUMN:
				return doctor.getEmail();
			default:
				return new String("");
		}
	}
}
