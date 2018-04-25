package sample;

import javafx.scene.chart.XYChart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;

public class CameraData {
    private List<List<Float>> numbers;

    public CameraData(String rawText) {
        numbers = new ArrayList<>();
        String[] rows = rawText.split(";");
        for (int i = 0; i < rows.length; i++) {
            rows[i] = rows[i].replaceFirst("\\   ", "");
        }
        Integer length = rows[0].split("( )+").length;
        for (int i = 0; i < length; i++) {
            numbers.add(new ArrayList<>());
        }
        for (int i = 0; i < rows.length; i++) {
            String[] data = rows[i].split("( )+");
            for (int j = 0; j < length; j++) {
                numbers.get(j).add(getNumber(data[j]));
                System.out.println("I:" + i + " J:" + j);
            }
        }
        //numbers[i] numbers.get(i); numbers.add(prvek)
    }

    public List<XYChart.Data> getXYChartData(int set) {
        List<XYChart.Data> data = new ArrayList<>();
        for (int i = 0; i < 180; i++) {
            double x = -cos(Math.toRadians(i)) * (numbers.get(set).get(i));
            double y = sin(Math.toRadians(i)) * (numbers.get(set).get(i));
            data.add(new XYChart.Data(x, y));
        }
        return data;
    }

    private Float getNumber(String number) {
        if (number.equals("Inf")) { //odchytím Inf a NaN -> místo nich dám 0. Ostatní převedu na floaty.
            return 50f;
        } else if (number.equals("NaN")) {
            return 5f;
        } else {
            return new BigDecimal(number).floatValue();
        }
    }
}
