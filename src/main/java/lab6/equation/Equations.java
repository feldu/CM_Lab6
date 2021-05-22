package lab6.equation;

import lombok.Getter;
import lombok.Setter;

import java.util.function.BiFunction;
import java.util.function.Function;

import static java.lang.Math.pow;

@Getter
public enum Equations {
    f1((x, y) -> y + (1 + x) * pow(y, 2), "y' = y + (1 + x)y^2, y(1) = -1", x -> {
        if (x != 0) return 1 / x;
        throw new RuntimeException("Решение данного ОДУ не существует в точке x = 0");
    }),
    f2((x, y) -> y + (1 + x) * pow(y, 2), "y' = y + (1 + x)y^2, y(1) = -1", x -> {
        if (x != 0) return 1 / x;
        throw new RuntimeException("Решение данного ОДУ не существует в точке x = 0");
    });
    private BiFunction<Double, Double, Double> function;
    private Function<Double, Double> solution;
    private String textView;
    private double left;
    private double right;
    @Setter
    private double h;

    Equations(BiFunction<Double, Double, Double> function, String textView, Function<Double, Double> solution) {
        this.function = function;
        this.textView = textView;
        this.solution = solution;
    }

    public void setLimits(double a, double b) {
        if (b > a) {
            this.left = a;
            this.right = b;
        } else throw new RuntimeException("Правая граница интервала должна быть больше левой");
    }

    //todo: delete or edit or ПОШЁЛ НАХУЙ
//    public Table convertFunctionToTable(int size) {
//        SortedMap<Double, Double> tableMap = new TreeMap<>();
//        double inc = (right - left) / (size - 1);
//        for (double i = left; i <= right; i += inc) {
//            tableMap.put(i, function.apply(i));
//        }
//        return new Table(tableMap);
//    }

}
