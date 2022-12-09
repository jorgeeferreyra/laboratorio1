package entrega.contracts;

import java.util.List;

public interface DaoModel {
	abstract public boolean getIsNew();
	abstract public void setIsNew(boolean isNew);
	abstract public Integer getId();
	abstract public void setId(int id);
	abstract public String getTable();
	abstract public List<String> getValues();
	abstract public List<String> getFields();
}