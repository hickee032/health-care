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
		tableViewAF.setPlaceholder(new Label("������� �߰����ּ���"));
		ExListadd();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void ExListadd() {
		AddedList = FXCollections.observableArrayList();

		ExList = FXCollections.observableArrayList(new Workout("����", 0.9), new Workout("�޽�", 1.0),
				new Workout("TV ��û", 1.0), new Workout("����", 1.2), new Workout("�۾���&Ÿ����", 1.8),
				new Workout("�ȱ�3km", 2.0), new Workout("��� ��� ��ⱸ", 3.0), new Workout("�米 ��(����)", 3.0),
				new Workout("īƮ Ÿ�� ġ�� ����", 3.0), new Workout("����", 3.0), new Workout("�ȱ� 3 ����(4.8 km) �ӵ�", 3.3),
				new Workout("������ �ϴ� �", 3.5), new Workout("����", 3.5), new Workout("������ Ÿ��", 4.0),
				new Workout("��� ������", 4.0), new Workout("����", 4.5), new Workout("���� õõ��", 4.5),
				new Workout("���� ����", 5.0), new Workout("�米��(����)", 5.0), new Workout("��������", 6.0),
				new Workout("���", 7.0), new Workout("õõ�� �ٱ�", 7.0), new Workout("���ڸ� �ٱ�", 8.0), new Workout("��Ű", 8.0),
				new Workout("������ �ٱ�", 10.0));

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
					System.out.println("� : " + model.getExName());
					System.out.println("��� : " + model.getExMl());
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
				ResultLA.setText("��ü��");
			} else if ((18.5 < result) && (result <= 25)) {
				ResultLA.setText("����");
			} else if ((25 < result) && (result <= 30)) {
				ResultLA.setText("��ü��");
			} else if (30 < result) {
				ResultLA.setText("����");
			}

		} catch (Exception e) {
			Popup.showAlert("����", "���ڸ� �Է��ϼ���", AlertType.ERROR);
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
			Popup.showAlert("����", "���ڸ� �Է��ϼ���", AlertType.ERROR);
		}
	}

	public void pressDELList() throws IOException {
		try {
			Workout selecteditem = tableViewAF.getSelectionModel().getSelectedItem();
			tableViewAF.getItems().remove(selecteditem);

			double subMot = Double.parseDouble(ToKcal.getText()) - selecteditem.getExMl();
			ToKcal.setText(Double.toString(subMot));

		} catch (Exception e) {
			Popup.showAlert("����", "�׸��� �����ϴ�.", AlertType.ERROR);
		}
	}

	public void SaveBtnPress() {

		if (main.MainApp.getUid().equals("Guest")) {
			Popup.showAlert("����", "GuestID�� �����Ҽ� �����ϴ�. ", AlertType.ERROR);

		} else {
			try {
				double weight = Double.parseDouble(WeightTF.getText());
				double height = Double.parseDouble(HeightTF.getText());
				double bmi = Double.parseDouble(BMiresultTF.getText());
				dbc.update(main.MainApp.getUid(), weight, height, bmi);

			} catch (Exception e) {
				Popup.showAlert("����", "��ĭ�� �ֽ��ϴ�.", AlertType.ERROR);
			}
		}
	}

	public void ResetBtnPress() {
		HeightTF.setText(null);
		WeightTF.setText(null);
	}

}
