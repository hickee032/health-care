package views;

import alram.Popup;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MemoController extends MainController {

	@FXML
	private TextField memoID;
	@FXML
	private TextField memoName;
	@FXML
	private TextField memoinidate;
	@FXML
	private TextField memoWeight;
	@FXML
	private TextField memoHeight;
	@FXML
	private TextField memoBmi;

	@FXML
	private TextField CurrPw;
	@FXML
	private TextField ChangePw;

	@FXML
	private Button PWChangeBtn;
	@FXML
	private Button PwResetBtn;

	@FXML
	private void initialize() {
		memoID.setText(main.MainApp.getUid());
		memoName.setText(main.MainApp.getUname());
		memoinidate.setText(main.MainApp.getUinidate().toString());
		memoWeight.setText(Double.toString(main.MainApp.getUweight()));
		memoHeight.setText(Double.toString(main.MainApp.getUheight()));
		memoBmi.setText(Double.toString(main.MainApp.getUbmi()));
	}

	public void ChangePW() {
		if (CurrPw.getText().length() >= 4) {
			if (CurrPw.getText().equals(main.MainApp.getUpw())) {
				// 패스워드를 바꾸는 곳
			} else {
				Popup.showAlert("에러", "비밀번호가 맞지 않습니다.", AlertType.ERROR);
			}
		} else {
			Popup.showAlert("에러", "비밀번호는 최소 4글자 이상이어야 합니다.", AlertType.ERROR);
		}
	}

	public void ChangeReset() {
		CurrPw.setText(null);
		ChangePw.setText(null);
	}

}
