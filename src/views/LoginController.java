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

	// 로그인 버튼
	public void loginProcess() throws IOException {
		String id = IdText.getText();
		String pw = PassText.getText();

		if (id.isEmpty() || pw.isEmpty()) {
			Popup.showAlert("에러", "아이디나 비밀번호는 비어있을 수 없습니다", AlertType.ERROR);
			return;

		} else {
			dbc.LoginDbUserTbl(id, pw);
			if (!(dbc.LoginDbUserTbl(id, pw))) {
				Popup.showAlert("에러", "존재하지 않는 아이디이거나 틀린 비밀번호입니다.", AlertType.ERROR);
			} else {
				MainApp.setloginCheck(true);
				System.out.println(main.MainApp.isloginCheck());
				MainApp.app.slide(loginPage);
			}
		}
	}

	// 게스트 버튼
	public void GuestLogin() {
		dbc.LoginDbUserTbl("Guest", "Guest");
		MainApp.setloginCheck(false);
		System.out.println(main.MainApp.isloginCheck());
		MainApp.app.slide(loginPage);
	}

	// 회원가입 버튼
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
