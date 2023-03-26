package resource;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Workout {

	private SimpleStringProperty exName;
	private SimpleDoubleProperty exCapacity;
	private SimpleDoubleProperty exMinute;

	public Workout() {
		this.exName = new SimpleStringProperty();
		this.exCapacity = new SimpleDoubleProperty();
		this.exMinute = new SimpleDoubleProperty();
	}

	public Workout(String exName, Double exCapacity) {
		this.exName = new SimpleStringProperty(exName);
		this.exCapacity = new SimpleDoubleProperty(exCapacity);
	}

	public Workout(String exName, Double exMinute, Double exCapacity) {
		this.exName = new SimpleStringProperty(exName);
		this.exMinute = new SimpleDoubleProperty(exMinute);
		this.exCapacity = new SimpleDoubleProperty(exCapacity);

	}

	public String getExName() {
		return exName.get();
	}

	public Double getExMl() {
		return exCapacity.get();
	}

	public Double getExBun() {
		return exMinute.get();
	}

	public void setExName(String exName) {
		this.exName.set(exName);
	}

	public void setExMl(Double exMl) {
		this.exCapacity.set(exMl);
	}

	public void setExBun(Double exBun) {
		this.exMinute.set(exBun);
	}
}
