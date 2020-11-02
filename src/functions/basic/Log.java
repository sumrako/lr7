package functions.basic;

import functions.Function;

public class Log implements Function {
    private double base;

    public Log(double base) throws IllegalArgumentException{
        if (base <= 0 || base == 1) throw new IllegalArgumentException("Неверно передано основание логарифма");
        this.base = base;
    }

    @Override
    public double getLeftDomainBorder() {
        return 0;
    }

    @Override
    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double getFunctionValue(double x) {
        return Math.log(x) / Math.log(base);
    }
}
