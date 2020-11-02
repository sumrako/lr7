package functions.meta;

import functions.Function;

public class Composition implements Function {
    private Function first;
    private Function second;

    public Composition(Function first, Function second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public double getLeftDomainBorder() {
        return first.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return first.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return first.getFunctionValue(second.getFunctionValue(x));
    }
}
