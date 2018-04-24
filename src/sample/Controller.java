package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;


public class Controller {
    @FXML
    private ScatterChart<Number, Number> chart;
    @FXML
    private TextField bot;
    @FXML
    private  TextField top;

    private String line[];
    private FileReader reader;
    private BufferedReader bufferedreader;
    private XYChart.Series series = new XYChart.Series();


    @FXML
    private void load() {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("D:\\Users\\Iklon\\Desktop\\Staz\\Programy\\Senzor"));
        File file = chooser.showOpenDialog(null);
        try {
            reader = new FileReader(file);
            bufferedreader = new BufferedReader(reader);
        } catch (FileNotFoundException exc) {exc.printStackTrace();}
        String data=null;
        try {
            data = bufferedreader.readLine();
        } catch (IOException exc) {exc.printStackTrace();}
        data = data.replaceAll("( )+", ";");
        line = data.split(";");
        compute(5, 1);
    }


    private void compute(int cas, int count) {
        int cyklus=0;
        float buffer[];
        buffer = new float[180];

        for (int b=0; b<count; b++){
            System.out.println("Cyklus b: " + b);

            while(cyklus<=179) {
                System.out.println("Cyklus c: " + cyklus);

                if(line[b*180+cyklus] != "") {
                    if (line[b * 180 + cyklus].indexOf("Inf")>=0) {
                        System.out.println("Inf");
                        buffer[cyklus] = 0;
                        cyklus++;
                    }
                    else {
                        if (line[b * 180 + cyklus].indexOf("NaN")>=0) {
                            System.out.println("NaN");
                            buffer[cyklus] = 0;
                            cyklus++;
                        }
                        else {
                            System.out.println("Normalni cislo");
                            BigDecimal cislo = new BigDecimal(line[b * 180 + cyklus]);
                            buffer[cyklus] = cislo.floatValue();
                            cyklus++;
                        }
                    }
                }
            }
            draw(buffer);
        }
    }

    private void draw(float data[]) {
        series.getData().removeAll();
        for (int a=Integer.parseInt(bot.getText()); a<Integer.parseInt(top.getText()); a++) {
            series.getData().add(new XYChart.Data(-cos(Math.toRadians(a))*data[a],sin(Math.toRadians(a))*data[a]));
            System.out.println("Cislo: " + data[a] + "   X: " + -cos(Math.toRadians(a))*data[a] + "   Y: " + sin(Math.toRadians(a))*data[a]);
        }
        chart.getData().add(series);
    }
}
