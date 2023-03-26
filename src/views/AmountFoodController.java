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

	private ObservableList<String> comBolist = FXCollections.observableArrayList("����", "��", "��");
	ObservableList<Food> FoodList;
	ObservableList<Food> FoodAddedList = FXCollections.observableArrayList();

	String FoodName;
	double Foodkcal;
	double TMot;

	@FXML
	private void initialize() {
		foodCB.setItems(comBolist);
		foodCB.getSelectionModel().select(0);
		foodAF.setPlaceholder(new Label("���� ������ �߰����ּ���"));

	}

	public void GukListadd() {
		FoodList = FXCollections.observableArrayList(new Food("�̿���", 204.0), new Food("���ߵ��屹", 73.0),
				new Food("���ⱹ", 234.0), new Food("�Ͼ", 272.0), new Food("���⹫��", 110.0), new Food("�ñ�ġ���屹", 73.0),
				new Food("�÷�����屹", 67.0), new Food("�ƿ�", 84.0), new Food("���", 97.0), new Food("�������", 203.0),
				new Food("�ᳪ����", 43.0), new Food("������", 569.0), new Food("������", 75.0), new Food("������", 240.0));

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
					System.out.println("�� : " + model.getFoodName());
					System.out.println("Į�θ� : " + model.getFoodKcal());
					FoodName = model.getFoodName();
					Foodkcal = model.getFoodKcal();
				}
			}
		});
	}

	public void BabListadd() {
		FoodList = FXCollections.observableArrayList(new Food("�̱�", 204.0), new Food("���ߵ��屹", 73.0),
				new Food("���ⱹ", 234.0), new Food("�Ͼ", 272.0), new Food("���⹫��", 110.0), new Food("�ñ�ġ���屹", 73.0),
				new Food("�÷�����屹", 67.0), new Food("�ƿ�", 84.0), new Food("���", 97.0), new Food("�������", 203.0),
				new Food("�ᳪ����", 43.0), new Food("������", 569.0), new Food("������", 75.0), new Food("������", 240.0));

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
					System.out.println("�� : " + model.getFoodName());
					System.out.println("Į�θ� : " + model.getFoodKcal());
					FoodName = model.getFoodName();
					Foodkcal = model.getFoodKcal();
				}
			}
		});
	}

	public void handlefoodCB(ActionEvent event) {
		if (foodCB.getValue().equals("��")) {
			BabListadd();
		} else if (foodCB.getValue().equals("��")) {
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
			Popup.showAlert("����", "�׸��� �����ϴ�.", AlertType.ERROR);
		}
	}
}
