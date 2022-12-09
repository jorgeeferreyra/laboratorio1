package entrega.views;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entrega.contracts.Controller;

public class BorderPanelWithTitle extends JPanel {
	private Controller controller;
	private JLabel titleLabel;
	
	protected BorderPanelWithTitle(String title, Controller controller) {
		this.controller = controller;
		
		this.setLayout(new BorderLayout());
		
		this.titleLabel = this.generateLabelWithBorder(title);
		titleLabel.setFont(new Font("Serif", Font.PLAIN, 30));

		this.add(titleLabel, BorderLayout.NORTH);
	}
	
	protected Controller getController() {
		return controller;
	}
	
	protected void setTitle(String title) {
		this.titleLabel.setText(title);
	}
		
	protected JLabel generateLabelWithBorder(String text) {
		JLabel label = new JLabel(text);
		label.setBorder(new EmptyBorder(5, 5, 5, 5));
		return label;
	}
}