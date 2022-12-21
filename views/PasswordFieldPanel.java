package entrega.views;

import javax.swing.JLabel;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class PasswordFieldPanel extends TextFieldPanel {
	public PasswordFieldPanel(String text) {
		this.field = new JPasswordField();
		this.label = new JLabel(text);
		
		this.add(this.label);
		this.add(this.field);
	}
}
