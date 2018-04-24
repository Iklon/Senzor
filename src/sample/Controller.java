package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import javax.jws.HandlerChain;
import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;


public class Controller {
    @FXML
    private ScatterChart<Number, Number> chart;

    private String sep[];
    private FileReader reader;
    private BufferedReader bufferedreader;
    private XYChart.Series series = new XYChart.Series();


    @FXML
    private void load() {
        String line=null;
        String buffer[][];
        buffer = new String[180][];
        FileChooser chooser = new FileChooser();
        //chooser.setInitialDirectory(new File("D:\\Users\\Iklon\\Desktop\\Staz\\SICK"));
        File file = chooser.showOpenDialog(null);
        try {
            reader = new FileReader(file);
            bufferedreader = new BufferedReader(reader);
        } catch (FileNotFoundException exc) {exc.printStackTrace();}
        for (int a=0; a<180; a++){
            try {
                line = bufferedreader.readLine();
            } catch (IOException exc) {exc.printStackTrace();}
            sep = line.split("( )+");
            //System.out.println("Řadek číslo: " + a + " má: " + sep.length + " znaků");
            buffer[a] = new String[sep.length];
            for (int b=0; b<sep.length; b++) {
                buffer[a][b] = sep[b]; //proměná b se počítá od 1 namísto od 0...nevim proč
            }
        }
        chart.getData().add(series);
        compute(buffer, 5, 100);
    }



    private void compute(String data[][], int cas, int count) {
        float vysledek[];
        vysledek = new float[180];

            //System.out.println("Cyklus g: " + g);
            AnimationTimer timer = new AnimationTimer() {
                int i = 0;
                int g = 0;
                @Override
                public void handle(long now) {
                    i++;
                    if(i > 40) {
                        i = 0;
                        g++;
                        if(g >= count)
                            this.stop();
                        int cyklus = 0;
                        while(cyklus<180) {
                            //System.out.println("Cyklus c: " + cyklus);

                            if(data[cyklus][g] != "") {
                                if (data[cyklus][g].indexOf("Inf")>=0) {
                                    //System.out.println("Inf");
                                    vysledek[cyklus] = 0;
                                    cyklus++;
                                }
                                else {
                                    if (data[cyklus][g].indexOf("NaN")>=0) {
                                        //System.out.println("NaN");
                                        vysledek[cyklus] = 0;
                                        cyklus++;
                                    }
                                    else {
                                        //System.out.println("Normalni cislo");
                                        BigDecimal cislo = new BigDecimal(data[cyklus][g]);
                                        vysledek[cyklus] = cislo.floatValue();
                                        cyklus++;
                                    }
                                }
                            }
                        }
                        draw(vysledek);

                    }
                }
            };
            timer.start();


    }

    private void draw(float data[]) {
        series.getData().clear();
        for (int a=0; a<180; a++) {
            series.getData().add(new XYChart.Data(-cos(Math.toRadians(a))*data[a],sin(Math.toRadians(a))*data[a]));
        }

    }
}
