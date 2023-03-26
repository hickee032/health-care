package views;

import alram.Popup;
import dbconnet.DbConnet;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import main.MainApp;

public class RegisterController extends MainController {

	@FXML
	private TextField txtId;
	@FXML
	private TextField txtName;
	@FXML
	private Button idChack;
	@FXML
	private PasswordField pass;
	@FXML
	private PasswordField passConfirm;
	@FXML
	private Label idclabel;
	@FXML
	private Button RegisterBtn;
	@FXML
	private AnchorPane registerPage;

	DbConnet dbc = new DbConnet();
	private boolean registpermit = false;

	String idText = null;

	public void register() {
		String id = null;
		String name = null;
		String pw = null;

		if (txtId.getText().isEmpty()) {
			Popup.showAlert("����", "���̵� �Է��ϼ���.", AlertType.ERROR);
		} else if (txtName.getText().isEmpty()) {
			Popup.showAlert("����", "�̸� �Է��ϼ���.", AlertType.ERROR);
		} else if (pass.getText().isEmpty()) {
			Popup.showAlert("����", "�е���带 �Է��ϼ���.", AlertType.ERROR);
		} else if (passConfirm.getText().isEmpty()) {
			Popup.showAlert("����", "�е���� Ȯ���� �Է��ϼ���.", AlertType.ERROR);
		} else if (!(pass.getText().equals(passConfirm.getText()))) {
			Popup.showAlert("����", "�н����� �� �е���� Ȯ���� ����ġ.", AlertType.ERROR);
		} else {

			id = txtId.getText();
			name = txtName.getText();
			pw = pass.getText();

			try {
				dbc.insertUserTbl(id, name, pw);
				Popup.showAlert("����", "���ԵǾ����ϴ�.", AlertType.INFORMATION);
			} catch (Exception e) {
				Popup.showAlert("����", "������ �߻��Ͽ����ϴ�.", AlertType.ERROR);
			}
		}
	}

	public void idChackBtn() {
		idText = txtId.getText();
		dbc.selectUserTbl(idText, idclabel, registpermit, RegisterBtn);
		System.out.println(registpermit + " = idChackBtn");
	}

	public void cancel() {
		MainApp.app.slide(getRoot());
	}
}
