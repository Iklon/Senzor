package sample;

import javafx.animation.AnimationTimer;
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
        chart.getData().add(series);
        try {
            cameraData = readCameraData(file);
            new AnimationTimer() {
                int couter = 0;
                int g = 0;
                @Override
                public void handle(long now) {
                    couter++;
                    if(couter > 2) {
                        couter = 0;
                        g++;
                        System.out.println(g);
                        draw(g);

                    }
                }
            }.start();

        } catch (IOException ioexc) {
            ioexc.printStackTrace();
        }
    }

    private void draw(int position) {
        series.getData().clear();
        series.getData().addAll(cameraData.getXYChartData(position));
    }

    private CameraData readCameraData(File file) throws IOException {
        BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder builder = new StringBuilder();
        while((line = bufferedreader.readLine()) != null) {
            builder.append(line + ";");
        }
        builder.deleteCharAt(builder.length() - 1);
        return new CameraData(builder.toString());
    }
}
