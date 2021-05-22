package lab6.method;

import lab6.equation.Equations;
import lab6.table.Table;
import lombok.extern.slf4j.Slf4j;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.BiFunction;

@Slf4j
public class ImprovedEulerMethod implements SolvingMethod {
    @Override
    public Table solve(Equations equations) {
        SortedMap<Double, Double> methodSolution = new TreeMap<>();
        BiFunction<Double, Double, Double> function = equations.getFunction();
        double lastX = equations.getInitPointArg();
        double lastY = equations.getInitPointValue();
        double y, x = lastX, h = equations.getH();
        int i = 0;
        while (true) {
            x += h;
            y = lastY + h / 2 * (function.apply(lastX, lastY) + function.apply(x, lastY + h * function.apply(lastX, lastY)));
            log.info("{}: x={}, y={}, f(x, y)={}", i, lastX, lastY, function.apply(lastX, lastY));
            log.info("R={}", getR(function, lastX, lastY, h));
            methodSolution.put(lastX, lastY);
            if (x >= equations.getRight()) break;
            lastY = y;
            lastX = x;
            i++;
        }
        log.info("{}: x={}, y={}", ++i, x, y);
        log.info("R={}", getR(function, x, y, h));
        methodSolution.put(x, y);
        return new Table(methodSolution);
    }

    private double getR(BiFunction<Double, Double, Double> function, double x, double y, double h) {
        int ACCURACY = 2;
        double lastY = y;
        double lastX = x;
        x += h;
        double y1 = lastY + h / 2 * (function.apply(lastX, lastY) + function.apply(x, lastY + h * function.apply(lastX, lastY)));
        x -= h / 2;
        double y2 = lastY + h / 4 * (function.apply(lastX, lastY) + function.apply(x, lastY + h / 2 * function.apply(lastX, lastY)));
        lastY = y2;
        lastX = x += h/2;
        y2 = lastY + h / 4 * (function.apply(lastX, lastY) + function.apply(x, lastY + h / 2 * function.apply(lastX, lastY)));
        return Math.abs(y1 - y2) / (Math.pow(2, ACCURACY) - 1);
    }
}
