package views;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alram.Popup;
import dbconnet.DbConnet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import main.MainApp;

public class CalendarController extends MainController {
	@FXML
	private Button btnPrev;
	@FXML
	private Button btnNext;
	@FXML
	private Label DateText;
	@FXML
	private Label DayText;
	@FXML
	private GridPane gridCalendar;

	private YearMonth currentYM;
	@SuppressWarnings("unused")
	private boolean isFocused = false;

	private List<DayController> dayList;
	private Map<String, String> dayOfWeek = new HashMap<>();

	private List<DayController> dayEXList;
	private List<DayController> dayFOODList;
	DayController day = new DayController();
	DbConnet dbc = new DbConnet();

	@FXML
	private void initialize() {
		dayList = new ArrayList<>();
		// �޷��� ��
		for (int i = 0; i < 5; i++) {
			// �޷��� ��
			for (int j = 0; j < 7; j++) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/views/DayLayout.fxml"));
				try {
					AnchorPane ap = loader.load();
					gridCalendar.add(ap, j, i);
					DayController dc = loader.getController();
					dc.setRoot(ap);
					dayList.add(dc);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.printf("j : %d, i : %d ��° �׸��� �� ���� �߻�\n", j, i);
					Popup.showAlert("����", "�޷�ĭ�� �׸��� �� ������ �߻�", AlertType.ERROR);
				}
			}
		}
		String[] engDay = { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
		String[] korDay = { "�Ͽ���", "������", "ȭ����", "������", "�����", "�ݿ���", "�����" };

		for (int i = 0; i < engDay.length; i++) {
			dayOfWeek.put(engDay[i], korDay[i]);
		}

		loadMonthData(YearMonth.now());
		setToday(LocalDate.now());
	}

	public void setToday(LocalDate date) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		DateText.setText(date.format(dtf));
		DayText.setText(dayOfWeek.get(date.getDayOfWeek().toString()));
	}

	public void loadMonthData(YearMonth ym) {
		LocalDate calendarDate = LocalDate.of(ym.getYear(), ym.getMonthValue(), 1); // �ش� ����� 1���� �����´�.
		while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY")) { // �Ͽ����� �ƴҶ����� �Ϸ羿 ������.
			calendarDate = calendarDate.minusDays(1); // �Ϸ羿 ����
		}
		// ������� ���� �ش��ְ��� ù°���� ���Եȴ�. ���⼭���� Ķ������ �׸��� �����Ѵ�.

		for (DayController day : dayList) {
			// System.out.println(dbc.selectdate(MainApp.getUid(), "userEX"));
			// System.out.println(calendarDate.toString());
			day.setDayLabel(calendarDate);
			calendarDate = calendarDate.plusDays(1); // �Ϸ羿 ����

			List<String> listE = new ArrayList<String>();
			listE.addAll(dbc.selectdate(MainApp.getUid(), "userEX"));
			for (int i = 0; i < listE.size(); i++) {
				if (listE.contains(calendarDate.toString())) {
//					dbc.selectdateAmount("userEX", "Uexercise", calendarDate.toString());
					day.setEXlabeltext(
							String.valueOf(dbc.selectdateAmount("userEX", "Uexercise", calendarDate.toString())));
					// day.setElabelcolor();
				} else {
					day.setEXlabeltext(" ");
				}
			}
			List<String> listF = new ArrayList<String>();
			listF.addAll(dbc.selectdate(MainApp.getUid(), "userFood"));
			for (int i = 0; i < listF.size(); i++) {
				if (listF.contains(calendarDate.toString())) {
					day.setFoodlabeltext(
							String.valueOf(dbc.selectdateAmount("userFood", "Ufood", calendarDate.toString())));
					// day.setFlabelcolor();
				} else {
					day.setFoodlabeltext(" ");
				}

			}
		}
		currentYM = ym;
	}

	public void prevMonth() {
		loadMonthData(currentYM.minusMonths(1)); // �Ѵ� �� �޷��� �ε�
		LocalDate firstDay = LocalDate.of(currentYM.getYear(), currentYM.getMonthValue(), 1);
		setToday(firstDay);
	}

	public void nextMonth() {
		loadMonthData(currentYM.plusMonths(1)); // �Ѵ� �� �޷��� �ε�
		LocalDate firstDay = LocalDate.of(currentYM.getYear(), currentYM.getMonthValue(), 1);
		setToday(firstDay);
	}

}
