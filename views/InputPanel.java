package entrega.views;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class InputPanel extends JPanel {
	protected JTextField field;
	protected JLabel label;
	
	protected InputPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public InputPanel(String text) {
		this.field = new JTextField("");
		this.label = new JLabel(text);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(this.label);
		this.add(this.field);
	}
	
	public void setFieldText(String fieldText) {
		this.field.setText(fieldText);
	}
	
	public String getFieldText() {
		return this.field.getText();
	}
	
	public String getTrimmedFieldText() {
		return this.getFieldText().trim();
	}
}