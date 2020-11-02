package functions;

import java.io.Serializable;
import java.util.Objects;

public class FunctionPoint implements Serializable, Cloneable {
    private double x;
    private double y;

    public FunctionPoint() {
        this.x = 0;
        this.y = 0;
    }

    public FunctionPoint(FunctionPoint point) {
        this.x = point.x;
        this.y = point.y;
    }

    public FunctionPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "; " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FunctionPoint)) return false;
        FunctionPoint point = (FunctionPoint) o;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        long numX = Double.doubleToLongBits(x);
        long numY = Double.doubleToLongBits(y);
        int firstPartX = (int) (numX & 4_294_967_295L);
        int secondPartX = (int) (numX >>> 32);
        int firstPartY = (int) (numY & 4_294_967_295L);
        int secondPartY = (int) (numY >>> 32);
        return 31 * (firstPartX ^ firstPartY) + (secondPartX ^ secondPartY);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public double getX() { return x; }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
