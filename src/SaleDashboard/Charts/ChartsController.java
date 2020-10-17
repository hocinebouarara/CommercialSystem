package SaleDashboard.Charts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ChartsController implements Initializable {

    @FXML
    private LineChart linechart;

    @FXML
    private javafx.scene.chart.PieChart PieChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        PieChart.setTitle("Sale by month");
        ObservableList<PieChart.Data> list= FXCollections.observableArrayList(
                new PieChart.Data("January",80),
                new PieChart.Data("February",100),
                new PieChart.Data("March",120),
                new PieChart.Data("April",60)
        );
        PieChart.setData(list);

        XYChart.Series Months = new XYChart.Series();
        Months.setName("abdelhamid");
        Months.getData().add(new XYChart.Data("January",5000));
        Months.getData().add(new XYChart.Data("February",7000));
        Months.getData().add(new XYChart.Data("March",9000));
        Months.getData().add(new XYChart.Data("April",8000));

        XYChart.Series Monthss = new XYChart.Series();
        Monthss.setName("Hocine");
        Monthss.getData().add(new XYChart.Data("January",4000));
        Monthss.getData().add(new XYChart.Data("February",500));
        Monthss.getData().add(new XYChart.Data("March",6000));
        Monthss.getData().add(new XYChart.Data("April",10000));

        linechart.getData().addAll(Months,Monthss);

    }
}
