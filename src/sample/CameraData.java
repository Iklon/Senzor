package sample;

import javafx.scene.chart.XYChart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;

public class CameraData {
    private List<Float> numbers;
    private Integer from = 0; //ve výchozím nastavení od 0 do 180
    private Integer to = 180;

    public CameraData(String rawText) {
        numbers = new ArrayList<>();
        String[] textNumbers = rawText.split("( )+");//není potřeba první nahradit mezery ";" a poté rozsekat. Zjistil jsem, že je možné rozsekat rovnou podle těch mezer
        for (int i = 0; i < textNumbers.length; i++) { //projdu všechny ty Stringy a převedu na čísla
            if (textNumbers[i].equals("Inf")) { //odchytím Inf a NaN -> místo nich dám 0. Ostatní převedu na floaty.
                numbers.add(0f);
            } else if (textNumbers[i].equals("NaN")) {
                numbers.add(0f);
            } else {
                numbers.add(new BigDecimal(textNumbers[i]).floatValue());
            }
        }
    }

    public List<XYChart.Data> getXYChartData(int time) {
        List<XYChart.Data> data = new ArrayList<>();
        for (int i = from; i < to; i++) {
            double x = -cos(Math.toRadians(i)) * (numbers.get(time * 180 + i));
            double y = sin(Math.toRadians(i)) * (numbers.get(time * 180 + i));
            data.add(new XYChart.Data(x, y));
        }
        return data;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }
}
