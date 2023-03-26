package views;

import java.io.IOException;

import alram.Popup;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import main.MainApp;

public class MainController {

	static int Logincheck = 2;

	private Pane root;
	@FXML
	private AnchorPane AmountExPage;
	@FXML
	private Button AmountExBTn;
	@FXML
	private StackPane mainContent;
	@FXML
	Label TextLabel;
	@FXML
	Button LogBtn;
	@FXML
	WebView webview;

	public Pane getRoot() {
		return root;
	}

	public void setRoot(Pane root) {
		this.root = root;
	}

	public void titlePage1() throws IOException {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/TitleLayout.fxml"));
		Pane TitlePane = (Pane) fxmlLoader.load();
		try {
			mainContent.getChildren().add(TitlePane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void titlePage() throws IOException {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/TitleLayout.fxml"));
		Pane TitlePane = (Pane) fxmlLoader.load();
		try {
			mainContent.getChildren().clear();
			mainContent.getChildren().add(TitlePane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void AmountExPage() throws IOException {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/AmountExLayout.fxml"));
		Pane AmountExPane = (Pane) fxmlLoader.load();
		try {
			mainContent.getChildren().clear();
			mainContent.getChildren().add(AmountExPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void AmountFoodPage() throws IOException {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/AmountFoodLayout.fxml"));
		Pane AmountFoodPane = (Pane) fxmlLoader.load();
		try {
			mainContent.getChildren().clear();
			mainContent.getChildren().add(AmountFoodPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	public void CalendarPage() throws IOException {
		if (MainApp.app.getUid().equals("Guest")) {
			Popup.showAlert("����", "�α����� �ʿ��� �����Դϴ�.", AlertType.ERROR);
		} else {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/CalendarLayout.fxml"));
			Pane CalendarPane = (Pane) fxmlLoader.load();
			try {
				mainContent.getChildren().clear();
				mainContent.getChildren().add(CalendarPane);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("static-access")
	public void GraphPage() throws IOException {
		if (MainApp.app.getUid().equals("Guest")) {
			Popup.showAlert("����", "�α����� �ʿ��� �����Դϴ�.", AlertType.ERROR);
		} else {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/GraphLayout.fxml"));
			Pane GraphPane = (Pane) fxmlLoader.load();
			try {
				mainContent.getChildren().clear();
				mainContent.getChildren().add(GraphPane);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("static-access")
	public void MemoPage() throws IOException {

		if (MainApp.app.getUid().equals("Guest")) {
			Popup.showAlert("����", "�α����� �ʿ��� �����Դϴ�.", AlertType.ERROR);
		} else {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/MemoLayout.fxml"));
			Pane AmountExPane = (Pane) fxmlLoader.load();
			try {
				mainContent.getChildren().clear();
				mainContent.getChildren().add(AmountExPane);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void Exit() {
		System.exit(0);
	}

	public void LogbtnTextChange() {
		if (Logincheck == 0) {
			LogBtn.setText("Logout");
		} else if (Logincheck == 1) {
			LogBtn.setText("Login");
		} else {
			LogBtn.setText("Register");
		}
	}

	public void AmountExEntered() {
		TextLabel.setText("����� ����մϴ�");
	}

	public void AmountFoodEntered() {
		TextLabel.setText("Į�θ��� ����մϴ�.");
	}

	public void CalendarEntered() {
		TextLabel.setText("���ں� ���뷮 ����� Ȯ���մϴ�.");
	}

	public void GraphEntered() {
		TextLabel.setText("�׷����� ǥ�õ˴ϴ�.");
	}

	public void MemoEntered() {
		TextLabel.setText("������ ������ ǥ���մϴ�.");
	}

	public void MainbtnEntered() {
		TextLabel.setText("�������� ���ư���");
	}

	public void EndBtnEntered() {
		TextLabel.setText("�����մϴ�.");
	}

	public void BtnExited() {
		TextLabel.setText("�������");
	}
}
