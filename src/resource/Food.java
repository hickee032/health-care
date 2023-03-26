package resource;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Food {

	private SimpleStringProperty foodName;
	private SimpleDoubleProperty foodKcal;

	public Food() {
		this.foodName = new SimpleStringProperty();
		this.foodKcal = new SimpleDoubleProperty();
	}

	public Food(String foodName, Double foodKcal) {
		this.foodName = new SimpleStringProperty(foodName);
		this.foodKcal = new SimpleDoubleProperty(foodKcal);
	}

	public String getFoodName() {
		return foodName.get();
	}

	public Double getFoodKcal() {
		return foodKcal.get();
	}

	public void setFoodName(String foodName) {
		this.foodName.set(foodName);
	}

	public void setFoodKcal(Double foodKcal) {
		this.foodKcal.set(foodKcal);
	}

}
