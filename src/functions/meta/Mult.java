package functions.meta;

import functions.Function;

public class Mult implements Function {
    private Function first;
    private Function second;

    public Mult(Function first, Function second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public double getLeftDomainBorder() {
        return Math.max(first.getLeftDomainBorder(), second.getLeftDomainBorder());
    }

    @Override
    public double getRightDomainBorder() {
        return Math.min(first.getRightDomainBorder(), second.getRightDomainBorder());
    }

    @Override
    public double getFunctionValue(double x) {
        return first.getFunctionValue(x) * second.getFunctionValue(x);
    }
}
