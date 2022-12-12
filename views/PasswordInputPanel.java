package entrega.views;

import javax.swing.JLabel;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class PasswordInputPanel extends InputPanel {
	public PasswordInputPanel(String text) {
		this.field = new JPasswordField();
		this.label = new JLabel(text);
		
		this.add(this.label);
		this.add(this.field);
	}
}
