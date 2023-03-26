package views;

import java.io.IOException;

import alram.Popup;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import resource.Food;

public class AmountFoodController extends MainController {

	@FXML
	private ComboBox foodCB;
	@FXML
	private TableView<Food> foodBF;
	@FXML
	private TableView<Food> foodAF;
	@FXML
	private TextField TfoodKcal;

	private ObservableList<String> comBolist = FXCollections.observableArrayList("선택", "밥", "국");
	ObservableList<Food> FoodList;
	ObservableList<Food> FoodAddedList = FXCollections.observableArrayList();

	String FoodName;
	double Foodkcal;
	double TMot;

	@FXML
	private void initialize() {
		foodCB.setItems(comBolist);
		foodCB.getSelectionModel().select(0);
		foodAF.setPlaceholder(new Label("음식 종류를 추가해주세요"));

	}

	public void GukListadd() {
		FoodList = FXCollections.observableArrayList(new Food("미역국", 204.0), new Food("배추된장국", 73.0),
				new Food("쇠고기국", 234.0), new Food("북어국", 272.0), new Food("쇠고기무국", 110.0), new Food("시금치된장국", 73.0),
				new Food("시래기된장국", 67.0), new Food("아욱국", 84.0), new Food("어묵국", 97.0), new Food("우거지국", 203.0),
				new Food("콩나물국", 43.0), new Food("우족탕", 569.0), new Food("조개국", 75.0), new Food("육개장", 240.0));

		TableColumn tcFood = foodBF.getColumns().get(0);
		tcFood.setCellValueFactory(new PropertyValueFactory("FoodName"));
		TableColumn tcFkal = foodBF.getColumns().get(1);
		tcFkal.setCellValueFactory(new PropertyValueFactory("FoodKcal"));

		foodBF.setItems(FoodList);

		foodBF.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Food>() {
			@Override
			public void changed(ObservableValue<? extends Food> observable, Food oldValue, Food newValue) {
				if (newValue != null) {
					Food model = foodBF.getSelectionModel().getSelectedItem();
					System.out.println("국 : " + model.getFoodName());
					System.out.println("칼로리 : " + model.getFoodKcal());
					FoodName = model.getFoodName();
					Foodkcal = model.getFoodKcal();
				}
			}
		});
	}

	public void BabListadd() {
		FoodList = FXCollections.observableArrayList(new Food("미국", 204.0), new Food("배추된장국", 73.0),
				new Food("쇠고기국", 234.0), new Food("북어국", 272.0), new Food("쇠고기무국", 110.0), new Food("시금치된장국", 73.0),
				new Food("시래기된장국", 67.0), new Food("아욱국", 84.0), new Food("어묵국", 97.0), new Food("우거지국", 203.0),
				new Food("콩나물국", 43.0), new Food("우족탕", 569.0), new Food("조개국", 75.0), new Food("육개장", 240.0));

		TableColumn tcFood = foodBF.getColumns().get(0);
		tcFood.setCellValueFactory(new PropertyValueFactory("FoodName"));
		TableColumn tcFkal = foodBF.getColumns().get(1);
		tcFkal.setCellValueFactory(new PropertyValueFactory("FoodKcal"));

		foodBF.setItems(FoodList);

		foodBF.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Food>() {
			@Override
			public void changed(ObservableValue<? extends Food> observable, Food oldValue, Food newValue) {
				if (newValue != null) {
					Food model = foodBF.getSelectionModel().getSelectedItem();
					System.out.println("밥 : " + model.getFoodName());
					System.out.println("칼로리 : " + model.getFoodKcal());
					FoodName = model.getFoodName();
					Foodkcal = model.getFoodKcal();
				}
			}
		});
	}

	public void handlefoodCB(ActionEvent event) {
		if (foodCB.getValue().equals("밥")) {
			BabListadd();
		} else if (foodCB.getValue().equals("국")) {
			GukListadd();
		}
	}

	public void AddfoodAF() {

		FoodAddedList.add(new Food(FoodName, Foodkcal));
		foodAF.refresh();

		TableColumn foodName = foodAF.getColumns().get(0);
		foodName.setCellValueFactory(new PropertyValueFactory("FoodName"));
		TableColumn foodKcal = foodAF.getColumns().get(1);
		foodKcal.setCellValueFactory(new PropertyValueFactory("FoodKcal"));

		foodAF.setItems(FoodAddedList);
		TMot += Foodkcal;
		TfoodKcal.setText(Double.toString(TMot));

	}

	public void pressDELList() throws IOException {
		try {
			Food selecteditem = foodAF.getSelectionModel().getSelectedItem();
			System.out.println(selecteditem);
			foodAF.getItems().remove(selecteditem);

			double subMot = Double.parseDouble(TfoodKcal.getText()) - selecteditem.getFoodKcal();
			TfoodKcal.setText(Double.toString(subMot));

		} catch (Exception e) {
			Popup.showAlert("에러", "항목이 없습니다.", AlertType.ERROR);
		}
	}
}
