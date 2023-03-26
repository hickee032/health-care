package views;

import java.util.ArrayList;
import java.util.List;

import dbconnet.DbConnet;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import main.MainApp;

public class GraphController extends MainController {

	@FXML
	private BarChart barChart;

	@FXML
	private Button WeBtn;
	@FXML
	private Button KcBtn;
	@FXML
	private Button ExBtn;

	DbConnet dbc = new DbConnet();
	List<String> listD = new ArrayList<String>();
	List<String> listT = new ArrayList<String>();
	XYChart.Series series = new XYChart.Series();

	@FXML
	private void initialize() {

		series.setName("Weight");
		series.setData(FXCollections.observableArrayList(new XYChart.Data("2015", 70),

				new XYChart.Data("2016", 40),

				new XYChart.Data("2017", 50),

				new XYChart.Data("2018", 30),

				new XYChart.Data("2018", 30)));
		barChart.getData().add(series);
	}

	public void WeBtnAction() {
		listD.addAll(dbc.GraphDate(MainApp.getUid(), "userbmi"));
		listT.addAll(dbc.GraphDate(MainApp.getUid(), "userbmi"));
		series.setName("Weight");
		series.setData(FXCollections.observableArrayList(

		));
	}

	public void KcBtnAction() {
		listD.addAll(dbc.GraphDate(MainApp.getUid(), "userfood"));
		listT.addAll(dbc.GraphDate(MainApp.getUid(), "userfood"));
		series.setName("Kcal");
	}

	public void ExBtnAction() {
		listD.addAll(dbc.GraphDate(MainApp.getUid(), "userex"));
		listT.addAll(dbc.GraphDate(MainApp.getUid(), "userex"));
		series.setName("EX");
	}

}
