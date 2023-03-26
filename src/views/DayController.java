package views;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class DayController extends MainController {
	@FXML
	private Label lblDay;
	@FXML
	private Label EXText;
	@FXML
	private Label FOODText;

	@SuppressWarnings("unused")
	private LocalDate date;

	@SuppressWarnings("unused")
	private boolean isFocused = false;

	public void setDayLabel(LocalDate date) {
		this.date = date;
		lblDay.setText(String.valueOf(date.getDayOfMonth()));
	}

	public void setEXlabeltext(String ex) {
		EXText.setText(ex);
	}

	public void setElabelcolor() {
		EXText.setBackground(new Background(new BackgroundFill(Color.DEEPSKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
	}

	public void setFoodlabeltext(String ex) {
		FOODText.setText(ex);
	}

	public void setFlabelcolor() {
		FOODText.setBackground(new Background(new BackgroundFill(Color.BROWN, CornerRadii.EMPTY, Insets.EMPTY)));
	}

}
