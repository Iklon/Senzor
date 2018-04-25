package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Controller {
    @FXML
    private ScatterChart<Number, Number> chart;
    @FXML
    private TextField x_bot;
    @FXML
    private  TextField x_top;
    @FXML
    private NumberAxis x_axis;
    @FXML
    private CheckBox scale;
    @FXML
    private Slider slider;
    @FXML
    private Label label;

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
                int counter = 0, g = 0, delay;
                @Override
                public void handle(long now) {
                    counter++;
                    delay = (int)slider.getValue();
                    label.setText(String.valueOf((int)slider.getValue()));
                    if(counter > delay) {
                        counter = 0;
                        g++;
                        draw(g);
                        if(scale.isSelected()) x_axis.setAutoRanging(true);
                        else {
                            x_axis.setAutoRanging(false);
                            if(x_bot.getText() != null) {x_axis.setLowerBound((double)Double.parseDouble(x_bot.getText()));}
                            else {
                                x_axis.setLowerBound((double)-20);
                            }
                            if(x_top.getText() != null) {x_axis.setUpperBound((double)Double.parseDouble(x_top.getText()));}
                            else {
                                x_axis.setUpperBound((double)20);
                            }
                        }
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
