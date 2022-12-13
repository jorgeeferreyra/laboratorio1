package entrega.views;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public abstract class TableModel<T> extends AbstractTableModel {
	protected String[] titles = {};
	private List<T> content;
	
	public TableModel() {
		this.content = new ArrayList<T>();
	}
	
	public TableModel(List<T> content) {
		this.content = content;
	}
	
	public int getRowCount() {
		return this.content.size();
	}

	public int getColumnCount() {
		return this.titles.length;
	}
	
	public String getColumnName(int column) {
		return this.titles[column];
	}

	
	public List<T> getContent() {
		return this.content;
	}
	
	public void setContent(List<T> content) {
		this.content = content;
	}
}
