package entrega.contracts;

public abstract class Model {
	private boolean isNew;
	private int id;
	
	public Integer getId() {
		return Integer.valueOf(this.id);
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public boolean getIsNew() {
		return this.isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
}
