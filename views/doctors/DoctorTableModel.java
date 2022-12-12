package entrega.views.doctors;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entrega.models.Doctor;

@SuppressWarnings("serial")
public class DoctorTableModel extends AbstractTableModel {
	private static final int ID_COLUMN = 0;
	private static final int FIRSTNAME_COLUMN = 1;
	private static final int LASTNAME_COLUMN = 2;
	private static final int PHONE_COLUMN = 3;
	private static final int EMAIL_COLUMN = 4;
	
	private String[] titles = {"ID", "Nombre", "Apellido", "Teléfono", "Email"};
	private List<Doctor> content;
	
	public DoctorTableModel() {
		this.content = new ArrayList<Doctor>();
	}
	
	public DoctorTableModel(List<Doctor> content) {
		this.content = content;
	}

	public int getRowCount() {
		return this.content.size();
	}

	public int getColumnCount() {
		return this.titles.length;
	}
	
	public String getColumnName(int column) {
		return this.titles[column];
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Doctor doctor = content.get(rowIndex);
		
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
	
	public List<Doctor> getContent() {
		return this.content;
	}
	
	public void setContent(List<Doctor> content) {
		this.content = content;
	}
}
