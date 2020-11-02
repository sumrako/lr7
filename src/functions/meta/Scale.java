package functions.meta;

import functions.Function;

public class Scale implements Function {
    private Function function;
    private double scaleX;
    private double scaleY;

    public Scale(Function function, double scaleX, double scaleY) {
        this.function = function;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public double getLeftDomainBorder() {
        return function.getLeftDomainBorder() * scaleX;
    }

    @Override
    public double getRightDomainBorder() {
        return function.getRightDomainBorder() * scaleX;
    }

    @Override
    public double getFunctionValue(double x) {
        return function.getFunctionValue(scaleX * x) * scaleY;
    }
}
