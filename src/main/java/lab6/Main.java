package lab6;

import lab6.equation.Equations;
import lab6.io.ConsoleReader;
import lab6.io.ConsoleWriter;
import lab6.io.Reader;
import lab6.io.Writer;
import lab6.method.ImprovedEulerMethod;
import lab6.method.RungeKuttaMethod;
import lab6.method.SolvingMethod;
import lab6.plot.Plot;
import lab6.plot.Series;
import lab6.table.Table;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.InputMismatchException;


@Slf4j
public class Main {
    private static final String commandFormat = "CM_Lab6 -er \n" +
            "-e -- Improved Euler's method\n" +
            "-r -- Runge-Kutta method";
    private static Reader in;
    private static Writer out;
    private static SolvingMethod method;
    private static Equations equation;

    public static void main(String[] args) {
        configure(args);
        try {
            chooseEquation();
            equation.setH(in.readDoubleWithMessage("Введите h: "));
            Table solutionTable = method.solve(equation);
            drawPlot(solutionTable);
        } catch (InputMismatchException e) {
            log.error("Incorrect input type");
            out.printError("Введённые данные некоректны");
        } catch (NumberFormatException e) {
            log.error("Incorrect input type");
            out.printError("Введённые данные некоректны");
            out.printError(e.getMessage());
        } catch (Exception e) {
            out.printError(e.getMessage());
        }
    }

    private static void drawPlot(Table solutionTable) {
        Series series = new Series("Полученное решение");
        series.setXData(solutionTable.getXData());
        series.setYData(solutionTable.getYData());
        Series realSeries = new Series("Точное решение", equation.getSolution(), equation.getLeft(), equation.getRight());
        Plot plot = new Plot("График", series, realSeries);
        plot.save("График");
    }

    @SneakyThrows
    private static void configure(String[] args) {
        in = new ConsoleReader();
        out = new ConsoleWriter();
        if (args.length != 1)
            throw new RuntimeException("Неверное количество аргументов\n" + commandFormat);
        if (args[0].equals("-e")) method = new ImprovedEulerMethod();
        else if (args[0].equals("-r")) method = new RungeKuttaMethod();
        else throw new RuntimeException("Неверное формат команды\n" + commandFormat);

    }

    private static void chooseEquation() {
        StringBuilder message = new StringBuilder("Выберите уравнение:\n");
        for (int i = 0; i < Equations.values().length; i++) {
            message.append(i + 1).append("). ").append(Equations.values()[i].getTextView()).append("\n");
        }
        try {
            int selectedValue = in.readIntWithMessage(message.toString());
            equation = Equations.values()[selectedValue - 1];
            log.info("Chosen function is: {}", equation.getTextView());
            chooseLimits();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Номер функции вне диапазона");
        }
    }

    private static void chooseLimits() {
        equation.setRight(in.readDoubleWithMessage("Введите правую границу интервала: "));
        log.info("Chosen interval is [{}; {}]", equation.getLeft(), equation.getRight());
    }
}
