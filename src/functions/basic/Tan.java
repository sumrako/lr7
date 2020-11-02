package functions.basic;

public class Tan extends TrigonometricFunction{

    @Override
    public double getFunctionValue(double x) {
        if ((2 * x) / Math.PI == (int) ((2 * x) / Math.PI)) return Double.NaN;
        return Math.tan(x);
    }
}
