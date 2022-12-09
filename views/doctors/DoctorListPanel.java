package entrega.views.doctors;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import entrega.controllers.DoctorController;
import entrega.models.Doctor;
import entrega.views.BorderPanelWithTitle;

public class DoctorListPanel extends BorderPanelWithTitle {

	private JTable table;
	private DoctorTableModel model;
	
	private JScrollPane scrollPane;
	
	private JButton addButton;
	private JButton editButton;
	private JButton removeButton;
		
	public DoctorListPanel(DoctorController controller) {
		super("Médicos", controller);
		
		this.build();
	}
	
	private void build() {
		this.addTable();
		this.addButtons();
	}

	public void setContent(List<Doctor> content) {
		this.model.setContent(content);
		this.model.fireTableDataChanged();
	}
	
	private void addTable() {
		this.model = new DoctorTableModel();
		this.table = new JTable(this.model);
		this.scrollPane = new JScrollPane(this.table);
		this.table.setRowSelectionAllowed(false);
		this.add(this.scrollPane, BorderLayout.CENTER);
	}
	
	private void addButtons() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		this.addButton = new JButton("Agregar");
		this.addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DoctorController controller = (DoctorController) getController();
				controller.showDoctorFormPanel();
			}
		});
		buttonPanel.add(this.addButton);
		
		this.editButton = new JButton("Editar");
		this.editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int doctorIndex = table.getSelectedRow();
				
				if (doctorIndex > -1) {
					Doctor doctor = model.getContent().get(doctorIndex);
					DoctorController controller = (DoctorController) getController();
					controller.showDoctorFormPanel(doctor);
				}
			}
		});
		buttonPanel.add(this.editButton);
		
		this.removeButton = new JButton("Quitar");
		this.removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int doctorIndex = table.getSelectedRow();
				
				if (doctorIndex > -1) {
					Doctor doctor = model.getContent().get(doctorIndex);
					DoctorController controller = (DoctorController) getController();
					controller.removeDoctor(doctor);
				}
			}
		});
		buttonPanel.add(this.removeButton);
		
		this.add(buttonPanel, BorderLayout.WEST);
	}

}
