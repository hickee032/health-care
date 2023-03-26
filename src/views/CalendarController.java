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
		// 달력의 주
		for (int i = 0; i < 5; i++) {
			// 달력의 일
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
					System.out.printf("j : %d, i : %d 번째 그리는 중 오류 발생\n", j, i);
					Popup.showAlert("에러", "달력칸을 그리는 중 오류가 발생", AlertType.ERROR);
				}
			}
		}
		String[] engDay = { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
		String[] korDay = { "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일" };

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
		LocalDate calendarDate = LocalDate.of(ym.getYear(), ym.getMonthValue(), 1); // 해당 년월의 1일을 가져온다.
		while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY")) { // 일요일이 아닐때까지 하루씩 빼간다.
			calendarDate = calendarDate.minusDays(1); // 하루씩 감소
		}
		// 여기까지 오면 해당주간의 첫째날이 오게된다. 여기서부터 캘린더를 그리기 시작한다.

		for (DayController day : dayList) {
			// System.out.println(dbc.selectdate(MainApp.getUid(), "userEX"));
			// System.out.println(calendarDate.toString());
			day.setDayLabel(calendarDate);
			calendarDate = calendarDate.plusDays(1); // 하루씩 증가

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
		loadMonthData(currentYM.minusMonths(1)); // 한달 뺀 달력을 로드
		LocalDate firstDay = LocalDate.of(currentYM.getYear(), currentYM.getMonthValue(), 1);
		setToday(firstDay);
	}

	public void nextMonth() {
		loadMonthData(currentYM.plusMonths(1)); // 한달 뺀 달력을 로드
		LocalDate firstDay = LocalDate.of(currentYM.getYear(), currentYM.getMonthValue(), 1);
		setToday(firstDay);
	}

}
