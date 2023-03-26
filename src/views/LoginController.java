package views;

import java.io.IOException;

import alram.Popup;
import dbconnet.DbConnet;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import main.MainApp;

public class LoginController extends MainController {

	@FXML
	private TextField IdText;
	@FXML
	private PasswordField PassText;
	@FXML
	private AnchorPane loginPage;

	DbConnet dbc = new DbConnet();

	// �α��� ��ư
	public void loginProcess() throws IOException {
		String id = IdText.getText();
		String pw = PassText.getText();

		if (id.isEmpty() || pw.isEmpty()) {
			Popup.showAlert("����", "���̵� ��й�ȣ�� ������� �� �����ϴ�", AlertType.ERROR);
			return;

		} else {
			dbc.LoginDbUserTbl(id, pw);
			if (!(dbc.LoginDbUserTbl(id, pw))) {
				Popup.showAlert("����", "�������� �ʴ� ���̵��̰ų� Ʋ�� ��й�ȣ�Դϴ�.", AlertType.ERROR);
			} else {
				MainApp.setloginCheck(true);
				System.out.println(main.MainApp.isloginCheck());
				MainApp.app.slide(loginPage);
			}
		}
	}

	// �Խ�Ʈ ��ư
	public void GuestLogin() {
		dbc.LoginDbUserTbl("Guest", "Guest");
		MainApp.setloginCheck(false);
		System.out.println(main.MainApp.isloginCheck());
		MainApp.app.slide(loginPage);
	}

	// ȸ������ ��ư
	public void registerPage() {
		MainApp.app.loadPane("register");
	}

	public AnchorPane getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(AnchorPane loginPage) {
		this.loginPage = loginPage;
	}

}
