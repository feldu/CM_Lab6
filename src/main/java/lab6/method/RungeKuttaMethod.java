package lab6.method;

import lab6.equation.Equations;
import lab6.table.Table;
import lombok.extern.slf4j.Slf4j;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.BiFunction;

@Slf4j
public class RungeKuttaMethod implements SolvingMethod {
    @Override
    public Table solve(Equations equations) {
        SortedMap<Double, Double> methodSolution = new TreeMap<>();
        BiFunction<Double, Double, Double> function = equations.getFunction();
        double lastY = equations.getInitPointValue();
        double y, x = equations.getInitPointArg(), h = equations.getH();
        int i = 0;
        while (true) {
            y = getY(function, x, lastY, h);
            log.info("{}: x={}, y={}, f(x, y)={}, R={}", i, x, lastY, function.apply(x, lastY), getR(function, x, lastY, h));
            methodSolution.put(x, lastY);
            x += h;
            if (x >= equations.getRight()) break;
            lastY = y;
            i++;
        }
        log.info("{}: x={}, y={}, R={}", ++i, x, y, getR(function, x, y, h));
        methodSolution.put(x, y);
        return new Table(methodSolution);
    }

    private double getY(BiFunction<Double, Double, Double> function, double lastX, double lastY, double h) {
        double k1 = h * function.apply(lastX, lastY),
                k2 = h * function.apply(lastX + h / 2, lastY + k1 / 2),
                k3 = h * function.apply(lastX + h / 2, lastY + k2 / 2),
                k4 = h * function.apply(lastX + h, lastY + k3);
        return lastY + 1d / 6 * (k1 + 2 * k2 + 2 * k3 + k4);
    }

    private double getR(BiFunction<Double, Double, Double> function, double x, double y, double h) {
        int ACCURACY = 4;
        double y1 = getY(function, x, y, h);
        double y2 = getY(function, x, y, h /2);
        y2 = getY(function, x + h /2, y2, h/2);
        return Math.abs(y1 - y2) / (Math.pow(2, ACCURACY) - 1);
    }
}
