package lab6.method;

import lab6.equation.Equations;
import lab6.table.Table;


public interface SolvingMethod {
    Table solve(Equations equations);
}
