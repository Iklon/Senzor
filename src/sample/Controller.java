package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Controller {
    @FXML
    private ScatterChart<Number, Number> chart;
    @FXML
    private TextField bot;
    @FXML
    private TextField top;

    private CameraData cameraData;

    private XYChart.Series series = new XYChart.Series();


    @FXML
    private void load() {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("D:\\Users\\Iklon\\Desktop\\Staz\\Programy\\Senzor"));
        File file = chooser.showOpenDialog(null);
        try {
            cameraData = readCameraData(file);
            draw(1);
        } catch (IOException ioexc) {
            ioexc.printStackTrace();
        }
    }

    private void draw(int time) {
        series.getData().removeAll();
        series.getData().addAll(cameraData.getXYChartData(time));
        chart.getData().add(series);
    }

    private CameraData readCameraData(File file) throws IOException {
        FileReader reader = new FileReader(file);
        BufferedReader bufferedreader = new BufferedReader(reader);
        String data = bufferedreader.readLine();
        return new CameraData(data);
    }
}
