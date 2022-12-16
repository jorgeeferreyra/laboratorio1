package entrega.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import entrega.services.Service;

@SuppressWarnings("serial")
public abstract class EntityListPanel<T> extends BorderPanelWithTitle {
	private JTable table;
	private TableModel<T> model;
	
	private JScrollPane scrollPane;
	
	public EntityListPanel(String title, Service service, TableModel<T> model) {
		super(title, service);
		this.model = model;
		this.build();
	}
	
	public void setContent(List<T> content) {
		this.model.setContent(content);
		this.model.fireTableDataChanged();
	}
	
	public TableModel<T> getModel() {
		return model;
	}
	
	protected JTable getTable() {
		return table;
	}
	
	private void build() {
		this.addTable();
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		this.buildSidebar(panel);
		
		panel.add(this.createButton("Volver", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getService().getFrontService().focusLoggedService();
			}
		}));

		this.add(panel, BorderLayout.WEST);
	}
	
	private void addTable() {
		this.table = new JTable(this.model);
		this.table.setRowSelectionAllowed(false);
		this.scrollPane = new JScrollPane(this.table);
		this.add(this.scrollPane, BorderLayout.CENTER);
	}
	
	protected abstract void buildSidebar(JPanel panel);
}