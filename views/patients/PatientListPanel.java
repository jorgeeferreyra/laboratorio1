package entrega.views.patients;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import entrega.entities.Patient;
import entrega.services.PatientService;
import entrega.views.EntityListPanel;

@SuppressWarnings("serial")
public class PatientListPanel extends EntityListPanel<Patient> {
	public PatientListPanel(PatientService patientService) {
		super("Pacientes", patientService, new PatientTableModel());
	}
	
	protected void buildSidebar(JPanel panel) {
		panel.add(this.createButton("Agregar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PatientService service = (PatientService) getService();
				service.showFormPanel();
			}
		}));
		
		panel.add(this.createButton("Editar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int patientIndex = getTable().getSelectedRow();
				
				if (patientIndex > -1) {
					Patient patient = getModel().getContent().get(patientIndex);
					PatientService service = (PatientService) getService();
					service.showFormPanel(patient);
				}
			}
		}));
		
		panel.add(this.createButton("Quitar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int patientIndex = getTable().getSelectedRow();
				
				if (patientIndex > -1) {
					Patient patient = getModel().getContent().get(patientIndex);
					PatientService service = (PatientService) getService();
					service.removeEntity(patient);
				}
			}
		}));
	}

}
