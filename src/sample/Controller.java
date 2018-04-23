package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Controller {
    @FXML
    private ScatterChart<Number, Number> chart;

    private String line[];
    private String sep[];
    private FileReader reader;
    private BufferedReader bufferedreader;


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
        //System.out.println(data);
        data = data.replaceAll("\\   ", ";").replaceAll("\\       ", ";");
        System.out.println(data);
        line = data.split(";");
        System.out.println("Soubor nacten");
        compute(5, 1);
    }


    private void compute(int cas, int count) {
        int multiple, cyklus=0;
        float buffer[];
        buffer = new float[180];

        for (int b=0; b<count; b++){
            System.out.println("Cyklus b: " + b);

            while(cyklus<=180) {
                System.out.println("Cyklus c: " + cyklus);
                System.out.println("|"+line[b * 180 + cyklus]+"|");

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
                            multiple = Integer.parseInt(line[b * 180 + cyklus].substring(12, 13))+1;
                            buffer[cyklus] = Float.parseFloat(line[b * 180 + cyklus].substring(0, 9)) * multiple;
                            System.out.println("Nasobek: " + multiple);
                            System.out.println("Cislo: " + Float.parseFloat(line[b * 180 + cyklus].substring(0, 9)));
                            System.out.println("A jeho vysledek: " + buffer[cyklus]);
                            cyklus++;
                        }
                    }
                }
            }
        }
        draw(buffer);
    }

    private void draw(float data[]) {
        XYChart.Series series = new XYChart.Series();
        for (int a=0; a<180; a++) {
            series.getData().add(new XYChart.Data(a, data[a]));
        }
        chart.getData().add(series);
    }
}
