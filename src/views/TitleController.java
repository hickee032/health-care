package views;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

public class TitleController extends MainController {

	@FXML
	AnchorPane Titlepage;
	@FXML
	WebView webview;

	@FXML
	private void initialize() {
		webview.getEngine().load("https://terms.naver.com/list.nhn?cid=51001&categoryId=51001");

	}

}
