package lab6.equation;

import lombok.Getter;
import lombok.Setter;

import java.util.function.BiFunction;
import java.util.function.Function;

import static java.lang.Math.*;

@Getter
public enum Equations {
    f1((x, y) -> y + (1 + x) * pow(y, 2), 1, -1, "y' = y + (1 + x)y^2, y(1) = -1;", x -> {
        if (x != 0) return -1 / x;
        throw new RuntimeException("Решение данного ОДУ не существует в точке x = 0");
    }),
    f2((x, y) -> pow((x - y), 2) + 1, 0, 0, "y' = (x - y)^2 + 1, y(0) = 0;", x -> x),
    f3((x, y) -> x * Math.exp(-pow(x, 2)) - 2 * x * y, -1, 1 / (2 * E), "y' = xe^(-x)^2 - 2xy, y(-1) = 1 / 2E;", x -> pow(x, 2) / 2 * exp(-pow(x, 2)));
    private BiFunction<Double, Double, Double> function;
    private Function<Double, Double> solution;
    private String textView;
    private double left;
    private double right;
    private double initPointArg;
    private double initPointValue;
    @Setter
    private double h;

    Equations(BiFunction<Double, Double, Double> function, double initPointArg, double initPointValue, String textView, Function<Double, Double> solution) {
        this.function = function;
        this.initPointArg = initPointArg;
        this.left = initPointArg;
        this.initPointValue = initPointValue;
        this.textView = textView;
        this.solution = solution;
    }

    public void setRight(double right) {
        if (right > left)
            this.right = right;
        else throw new RuntimeException("Правая граница интервала должна быть больше левой");
    }
}
