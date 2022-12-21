package entrega.views;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entrega.entities.Entity;

@SuppressWarnings("serial")
public class EntityComboBoxPanel<T extends Entity> extends JPanel {
	private JComboBox<T> comboBox;
	private JLabel label;
	private List<T> entities;
	
	public EntityComboBoxPanel(String text) {
		this.comboBox = new JComboBox<T>();
		this.label = new JLabel(text);
		this.entities = new ArrayList<T>();
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(this.label);
		this.add(this.comboBox);
	}
	
	public void setEntities(List<T> entities) {
		this.comboBox.removeAllItems();
		this.entities = entities;
		
		for (T entity : entities) {
			this.comboBox.addItem(entity);
		}
	}
		
	public void setSelectedEntityId(int entityId) {
		for (T entity : this.entities) {
			if (entity.getId() == entityId) {
				this.comboBox.setSelectedItem(entity);
			}
		}
	}
	
	public Integer getSelectedEntityId() {
		Entity entity = (Entity) this.comboBox.getSelectedItem();
		
		return entity == null
			? 0
			: entity.getId();
	}
}