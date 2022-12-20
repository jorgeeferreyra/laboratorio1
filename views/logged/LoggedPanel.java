package entrega.views.logged;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import entrega.services.LoggedService;
import entrega.views.BorderPanelWithTitle;

@SuppressWarnings("serial")
public class LoggedPanel extends BorderPanelWithTitle {
			
	public LoggedPanel(LoggedService service) {
		super("Bienvenidx", service);
		this.build();
	}
	
	private void build() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		
		panel.add(this.createButton("Administrar obras sociales", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoggedService service = (LoggedService) getService();
				service.getFrontService().focusHealthAssurancesService();;
			}
		}));
		
		panel.add(this.createButton("Administrar médicos", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoggedService service = (LoggedService) getService();
				service.getFrontService().focusDoctorsService();;
			}
		}));
		
		panel.add(this.createButton("Administrar pacientes", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoggedService service = (LoggedService) getService();
				service.getFrontService().focusPatientsService();;
			}
		}));
		
		panel.add(this.createButton("Administrar turnos", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoggedService service = (LoggedService) getService();
				service.getFrontService().focusAppointmentsService();;
			}
		}));
				
		this.add(panel, BorderLayout.CENTER);
	}
}