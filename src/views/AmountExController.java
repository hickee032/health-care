package views;

import java.io.IOException;

import alram.Popup;
import dbconnet.DbConnet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import resource.Workout;

public class AmountExController extends MainController {

	@FXML
	private AnchorPane AmountExPage;
	@FXML
	private TextField HeightTF;
	@FXML
	private TextField WeightTF;
	@FXML
	private TextField BMiresultTF;
	@FXML
	private TextField inputMin;
	@FXML
	private TextField ToKcal;

	@FXML
	private Button BMIBtn;
	@FXML
	private Button SaveBtn;
	@FXML
	private Button ResetBtn;

	@FXML
	private Label ResultLA;

	@FXML
	private TableView<Workout> tableViewBE;
	@FXML
	private TableView<Workout> tableViewAF;

	ObservableList<Workout> ExList;
	ObservableList<Workout> AddedList;

	double height = 0.0;
	double weight = 0.0;

	String ExName;
	double Exmot;
	double ExMin;
	double TMot;

	DbConnet dbc = new DbConnet();

	@FXML
	private void initialize() {
		tableViewAF.setPlaceholder(new Label("운동종류를 추가해주세요"));
		ExListadd();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void ExListadd() {
		AddedList = FXCollections.observableArrayList();

		ExList = FXCollections.observableArrayList(new Workout("수면", 0.9), new Workout("휴식", 1.0),
				new Workout("TV 시청", 1.0), new Workout("독서", 1.2), new Workout("글쓰기&타이핑", 1.8),
				new Workout("걷기3km", 2.0), new Workout("페달 밟기 운동기구", 3.0), new Workout("사교 춤(느린)", 3.0),
				new Workout("카트 타고 치는 골프", 3.0), new Workout("볼링", 3.0), new Workout("걷기 3 마일(4.8 km) 속도", 3.3),
				new Workout("집에서 하는 운동", 3.5), new Workout("역도", 3.5), new Workout("자전거 타기", 4.0),
				new Workout("계단 오르기", 4.0), new Workout("골프", 4.5), new Workout("수영 천천히", 4.5),
				new Workout("빠른 걸음", 5.0), new Workout("사교춤(빠른)", 5.0), new Workout("스케이팅", 6.0),
				new Workout("등산", 7.0), new Workout("천천히 뛰기", 7.0), new Workout("제자리 뛰기", 8.0), new Workout("스키", 8.0),
				new Workout("빠르게 뛰기", 10.0));

		TableColumn tcWork = tableViewBE.getColumns().get(0);
		tcWork.setCellValueFactory(new PropertyValueFactory("exName"));
		TableColumn tcTkal = tableViewBE.getColumns().get(1);
		tcTkal.setCellValueFactory(new PropertyValueFactory("exMl"));
		tableViewBE.setItems(ExList);

		tableViewBE.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Workout>() {
			@Override
			public void changed(ObservableValue<? extends Workout> observable, Workout oldValue, Workout newValue) {
				if (newValue != null) {
					Workout model = tableViewBE.getSelectionModel().getSelectedItem();
					System.out.println("운동 : " + model.getExName());
					System.out.println("운동량 : " + model.getExMl());
					ExName = model.getExName();
					Exmot = model.getExMl();
				}
			}
		});
	}

	public void BMIBtnPress() {
		try {

			height = Double.parseDouble(HeightTF.getText());
			weight = Double.parseDouble(WeightTF.getText());
			double result = Math.round(weight / ((height / 100) * (height / 100)) * 100) / 100;
			BMiresultTF.setText(String.valueOf(result));

			if (result <= 18.5) {
				ResultLA.setText("저체중");
			} else if ((18.5 < result) && (result <= 25)) {
				ResultLA.setText("정상");
			} else if ((25 < result) && (result <= 30)) {
				ResultLA.setText("과체중");
			} else if (30 < result) {
				ResultLA.setText("고도비만");
			}

		} catch (Exception e) {
			Popup.showAlert("에러", "숫자를 입력하세요", AlertType.ERROR);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void pressAddList() {
		try {

			ExMin = Double.parseDouble(inputMin.getText());

			tableViewAF.refresh();
			double totalMot = Exmot * ExMin;
			AddedList.add(new Workout(ExName, ExMin, totalMot));

			TableColumn tcName = tableViewAF.getColumns().get(0);
			tcName.setCellValueFactory(new PropertyValueFactory("ExName"));
			TableColumn tcBun = tableViewAF.getColumns().get(1);
			tcBun.setCellValueFactory(new PropertyValueFactory("ExBun"));
			TableColumn tcMot = tableViewAF.getColumns().get(2);
			tcMot.setCellValueFactory(new PropertyValueFactory("exMl"));

			tableViewAF.setItems(AddedList);

			TMot += totalMot;

			ToKcal.setText(Double.toString(TMot));

			inputMin.setText(null);

		} catch (Exception e) {
			Popup.showAlert("에러", "숫자를 입력하세요", AlertType.ERROR);
		}
	}

	public void pressDELList() throws IOException {
		try {
			Workout selecteditem = tableViewAF.getSelectionModel().getSelectedItem();
			tableViewAF.getItems().remove(selecteditem);

			double subMot = Double.parseDouble(ToKcal.getText()) - selecteditem.getExMl();
			ToKcal.setText(Double.toString(subMot));

		} catch (Exception e) {
			Popup.showAlert("에러", "항목이 없습니다.", AlertType.ERROR);
		}
	}

	public void SaveBtnPress() {

		if (main.MainApp.getUid().equals("Guest")) {
			Popup.showAlert("에러", "GuestID는 저장할수 없습니다. ", AlertType.ERROR);

		} else {
			try {
				double weight = Double.parseDouble(WeightTF.getText());
				double height = Double.parseDouble(HeightTF.getText());
				double bmi = Double.parseDouble(BMiresultTF.getText());
				dbc.update(main.MainApp.getUid(), weight, height, bmi);

			} catch (Exception e) {
				Popup.showAlert("에러", "빈칸이 있습니다.", AlertType.ERROR);
			}
		}
	}

	public void ResetBtnPress() {
		HeightTF.setText(null);
		WeightTF.setText(null);
	}

}
