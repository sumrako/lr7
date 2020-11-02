package functions.meta;

import functions.Function;

public class Power implements Function{
    private Function base;
    private double degree;

    public Power(Function base, double degree) {
        this.base = base;
        this.degree = degree;
    }

    @Override
    public double getLeftDomainBorder() {
        return base.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return base.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return Math.pow(base.getFunctionValue(x), degree);
    }
}
