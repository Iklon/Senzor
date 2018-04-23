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

    private String sep[];
    private FileReader reader;
    private BufferedReader bufferedreader;


    @FXML
    private void load() {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("D:\\Users\\Iklon\\Desktop\\Staz\\Programy\\SICK"));
        File data = chooser.showOpenDialog(null);
        try {
            reader = new FileReader(data);
            bufferedreader = new BufferedReader(reader);
        } catch (FileNotFoundException exc) {exc.printStackTrace();}
        String line=null;
        try {
            line = bufferedreader.readLine();
        } catch (IOException exc) {exc.printStackTrace();}
        sep = line.split("\\  ");
        System.out.println("Soubor nacten");
        draw(5, 10);
    }


    private void draw(int cas, int count) {
        XYChart.Series series = new XYChart.Series();
        float buffer[]=null;
        int multiple=1;
        for (int b=0; b<count; b++){
            System.out.println("Cyklus b: " + b);
            for (int c=0; c<180; c++) {
                System.out.println("Cyklus c: " + c);
                System.out.println("|"+sep[b * 180 + c]+"|");
                if(sep[b*180+c] != null) {
                    if (sep[b * 180 + c].compareTo("          Inf") == 1) {
                        System.out.println("Inf");
                        buffer[c] = 0;
                    } else {
                        if (sep[b * 180 + c].compareTo("          NaN") == 1) {
                            System.out.println("NaN");
                            buffer[c] = 0;
                        } else {
                            System.out.println("Normalni cislo");
                            System.out.println("Nasobek: " + Integer.parseInt(sep[b * 180 + c].substring(12, 13)));
                            System.out.println("Cislo: " + Float.parseFloat(sep[b * 180 + c].substring(0, 9)));
                            multiple = Integer.parseInt(sep[b * 180 + c].substring(12, 13))+1;                        //Tady to nějak hapruje
                            buffer[c] = Float.parseFloat(sep[b * 180 + c].substring(0, 9)) * multiple;              //Tady to nějak hapruje
                            System.out.println("A jeho vysledek: " + buffer[c]);
                        }
                    }
                }
            }
            for (int a=0; a<180; a++) {
              series.getData().add(new XYChart.Data(a, buffer[a]));
            }
        }
        System.out.println("Vycisteni bufferu");
        buffer=null;
        chart.getData().add(series);
    }
}
