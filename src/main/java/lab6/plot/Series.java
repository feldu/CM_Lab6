package lab6.plot;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
public class Series {
    private String seriesName;
    private List<Double> xData;
    private List<Double> yData;
    public static final int STEP_CNT = 200;
    private boolean hidePoints;
    private boolean hideLines;


    public Series(String seriesName) {
        this.seriesName = seriesName;
    }

    public Series(String seriesName, Function<Double, Double> function, double left, double right) {
        this.seriesName = seriesName;
        xData = new ArrayList<>();
        yData = new ArrayList<>();
        double step = (right - left) / STEP_CNT;
        //add padding step*5
        for (double i = left - step * 5; i <= right + step * 5; i += step) {
            xData.add(i);
            yData.add(function.apply(i));
        }
    }
}
