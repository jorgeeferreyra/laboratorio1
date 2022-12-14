package entrega.views.doctors;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import entrega.entities.Doctor;
import entrega.services.DoctorService;
import entrega.views.EntityListPanel;

@SuppressWarnings("serial")
public class DoctorListPanel extends EntityListPanel<Doctor> {
	public DoctorListPanel(DoctorService service) {
		super("Médicos", service, new DoctorTableModel());
	}
	
	protected void buildSidebar(JPanel panel) {
		panel.add(this.createButton("Agregar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DoctorService service = (DoctorService) getService();
				service.showFormPanel();
			}
		}));
		
		panel.add(this.createButton("Editar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int doctorIndex = getTable().getSelectedRow();
				
				if (doctorIndex > -1) {
					Doctor doctor = getModel().getContent().get(doctorIndex);
					DoctorService service = (DoctorService) getService();
					service.showFormPanel(doctor);
				}
			}
		}));
		
		panel.add(this.createButton("Quitar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int doctorIndex = getTable().getSelectedRow();
				
				if (doctorIndex > -1) {
					Doctor doctor = getModel().getContent().get(doctorIndex);
					DoctorService service = (DoctorService) getService();
					service.removeDoctor(doctor);
				}
			}
		}));
	}

}
