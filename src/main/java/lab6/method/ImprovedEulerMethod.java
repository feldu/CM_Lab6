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
            methodSolution.put(lastX, lastY);
            if (x >= equations.getRight()) break;
            lastY = y;
            lastX = x;
            i++;
        }
        log.info("{}: x={}, y={}", ++i, x, y);
        methodSolution.put(x, y);
        return new Table(methodSolution);
    }
}
