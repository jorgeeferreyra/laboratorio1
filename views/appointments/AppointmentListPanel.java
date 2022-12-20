package entrega.views.appointments;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import entrega.entities.Appointment;
import entrega.services.AppointmentService;
import entrega.views.EntityListPanel;

@SuppressWarnings("serial")
public class AppointmentListPanel extends EntityListPanel<Appointment> {
	public AppointmentListPanel(AppointmentService service) {
		super("Turnos", service, new AppointmentTableModel());
	}
	
	protected void buildSidebar(JPanel panel) {
		panel.add(this.createButton("Agregar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppointmentService service = (AppointmentService) getService();
				service.showFormPanel();
			}
		}));
		
		panel.add(this.createButton("Editar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int appointmentIndex = getTable().getSelectedRow();
				
				if (appointmentIndex > -1) {
					Appointment appointment = getModel().getContent().get(appointmentIndex);
					AppointmentService service = (AppointmentService) getService();
					service.showFormPanel(appointment);
				}
			}
		}));
		
		panel.add(this.createButton("Quitar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int appointmentIndex = getTable().getSelectedRow();
				
				if (appointmentIndex > -1) {
					Appointment appointment = getModel().getContent().get(appointmentIndex);
					AppointmentService service = (AppointmentService) getService();
					service.removeEntity(appointment);
				}
			}
		}));
	}

}
