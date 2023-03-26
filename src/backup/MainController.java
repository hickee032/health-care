package backup;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.stage.Stage;
import main.MainApp;

public class MainController implements Initializable{
	
	@SuppressWarnings("unused")
	private Stage primaryStage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public void AmountExPage() {
		MainApp.app.loadPane("AmountEx");
	}
	
}
