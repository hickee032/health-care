package alram;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Popup {

	public static void showAlert(String title, String msg, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(msg);

		alert.show();
	}
}
