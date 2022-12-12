package entrega.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import entrega.services.Service;

@SuppressWarnings("serial")
abstract public class FormPanel extends BorderPanelWithTitle {
	protected FormPanel(String title, Service service) {
		super(title, service);
		
		this.build();
	}

	private void build() {
		JPanel center = new JPanel();
		JPanel south = new JPanel();
		
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		south.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		this.buildCenter(center);
		this.buildSouth(south);
				
		this.add(center, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);
	}
	
	protected JButton createButton(String text, ActionListener actionListener) {
		JButton button = new JButton(text);

		button.addActionListener(actionListener);
		
		return button;
	}

	protected abstract void buildCenter(JPanel panel);
	protected abstract void buildSouth(JPanel panel);
}
