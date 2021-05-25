import lab6.equation.Equations;
import lab6.method.RungeKuttaMethod;
import lab6.method.SolvingMethod;
import lab6.table.Table;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class RungeKuttaTest {
    private SolvingMethod method = new RungeKuttaMethod();
    private Table resultTable;
    private Equations equation;
    @Test
    public void LectionTest() {
        equation = Equations.f1;
        equation.setRight(1.5);
        equation.setH(0.1);
        resultTable = method.solve(equation);
        SortedMap<Double, Double> map = new TreeMap<>();
        assertEquals(Arrays.asList(1.0, 1.1, 1.2, 1.3, 1.4, 1.5), resultTable.getXData().stream().map(x -> BigDecimal.valueOf(x)
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue()).collect(Collectors.toList()));
        assertEquals(Arrays.asList(-1d, -0.909093, -0.833337, -0.769234, -0.714289, -0.666670), resultTable.getYData().stream().map(x -> BigDecimal.valueOf(x)
                .setScale(6, RoundingMode.HALF_EVEN)
                .doubleValue()).collect(Collectors.toList()));
    }
}
