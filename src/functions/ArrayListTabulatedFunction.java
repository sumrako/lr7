package functions;

import java.io.Serializable;
import java.util.Arrays;

public class ArrayListTabulatedFunction implements TabulatedFunction {
    private FunctionPoint[] points;

    public ArrayListTabulatedFunction(FunctionPoint[] masPoint) throws IllegalArgumentException {
        if (masPoint.length < 2) throw new IllegalArgumentException("Точек маловато");
        for (int i = 0; i < masPoint.length - 1; i++) {
            if (masPoint[i].getX() >= masPoint[i + 1].getX())
                throw new IllegalArgumentException("Массив точек не отсортирован");
        }

        points = new FunctionPoint[(int)(masPoint.length * 1.5 + 1)];
        for (int i = 0; i < masPoint.length; i++) points[i] = masPoint[i];
    }

    public ArrayListTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException{
        if (rightX <= leftX || pointsCount < 2) throw new IllegalArgumentException("Неверно указаны границы функции");
        points = new FunctionPoint[(int)(pointsCount * 1.5 + 1)];

        double stepX = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount - 1; i++){
            points[i] = new FunctionPoint(leftX + stepX * i, 0);
        }
        points[pointsCount - 1] = new FunctionPoint(rightX, 0);
    }

    public ArrayListTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException{
        if (rightX <= leftX || values.length < 2) throw new IllegalArgumentException("Неверно указаны границы функции");
        points = new FunctionPoint[(int)(values.length * 1.5 + 1)];

        double stepX = (rightX - leftX) / (values.length - 1);
        for (int i = 0; i < values.length - 1; i++) points[i] = new FunctionPoint(leftX + stepX * i, values[i]);

        points[values.length - 1] = new FunctionPoint(rightX, values[values.length - 1]);
    }

    public double getLeftDomainBorder() {
        return getPointX(0);
    }

    public double getRightDomainBorder() {
        return getPointX(getPointsCount() - 1);
    }

    public double getFunctionValue(double x) {
        //Проверяем лежит ли точка в промежутке
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) return Double.NaN;

        //Ищем точку среди точек массива
        for (int i = 0; i < getPointsCount(); i++) if (getPointX(i) == x) return getPointY(i);

        //Ищем индекс левой границы промежутка нахождения искомой точки
        int i = 0;
        for (; i < getPointsCount() - 1; ++i) if (x > getPointX(i) && x < getPointX(i + 1)) break;
        //Находим уравнение прямой и подставляем аргумент, после возвращаем
        return (x - getPointX(i)) * (getPointY(i + 1) - getPointY(i)) / (getPointX(i + 1) - getPointX(i)) + getPointY(i);
    }

    public int getPointsCount(){
        int count = 0;
        for (int i = 0; i < points.length; i++) if (points[i] != null) count++;
        return count;
    }

    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException{
        if (index < 0 || index > getPointsCount() - 1) throw new FunctionPointIndexOutOfBoundsException("Индекс указан неверно");
        return points[index];
    }

    public void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        if (index < 0 || index > getPointsCount() - 1) throw new FunctionPointIndexOutOfBoundsException("Индекс указан неверно");
        if (point.getX() < getLeftDomainBorder() && index != 0 || point.getX() > getRightDomainBorder() && index != getPointsCount() - 1 || index != 0 && point.getX() < getPointX(index - 1) || index != getPointsCount() - 1 && point.getX() > getPointX(index + 1)) throw new InappropriateFunctionPointException("Неверно указано значение точки");

        points[index] = point;
    }

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException{
        if (index < 0 || index > getPointsCount() - 1) throw new FunctionPointIndexOutOfBoundsException("Индекс указан неверно");
        return points[index].getX();
    }

    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        if (index < 0 || index > getPointsCount() - 1) throw new FunctionPointIndexOutOfBoundsException("Индекс указан неверно");
        if (x < getLeftDomainBorder() && index != 0 || x > getRightDomainBorder() && index != getPointsCount() - 1 || index != 0 && x < getPointX(index - 1) || index != getPointsCount() - 1 && x > getPointX(index + 1)) throw new InappropriateFunctionPointException("Неверно указано значение x");

        points[index].setX(x);
    }

    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException{
        if (index < 0 || index > getPointsCount() - 1) throw new FunctionPointIndexOutOfBoundsException("Индекс указан неверно");
        return points[index].getY();
    }

    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException{
        if (index < 0 || index > getPointsCount() - 1) throw new FunctionPointIndexOutOfBoundsException("Индекс указан неверно");
        points[index].setY(y);
    }

    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, IllegalStateException {
        if (index < 0 || index > getPointsCount() - 1) throw new FunctionPointIndexOutOfBoundsException("Индекс указан неверно");
        if (getPointsCount() < 3) throw new IllegalStateException("При удалении будет недопустимое количество элементов");
        System.arraycopy(points, index + 1, points, index, getPointsCount() - index - 1);
        points[getPointsCount() - 1] = null;
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        for (int i = 0; i < getPointsCount(); i++) if (point.getX() == points[i].getX()) throw new InappropriateFunctionPointException("Данное значение абсциссы уже присутствует в функции");

        if (points.length == getPointsCount()) {
            FunctionPoint[] copy = new FunctionPoint[(int) (getPointsCount() * 1.5 + 1)];
            System.arraycopy(points, 0, copy, 0, getPointsCount());
            points = copy;
        }
        int first = 0, last = getPointsCount() - 1, position = (first + last)/ 2;

        while (!(point.getX() > getPointX(position) && point.getX() < getPointX(position + 1)) && (first <= last)) {
            if (getPointX(position) > point.getX()) last = position - 1;
            else first = position + 1;

            position = (first + last) / 2;
        }

        if (getPointsCount() - 1 == position) {
            points[position + 1] = point;
            return;
        }

        System.arraycopy(points, position + 1, points, position + 2, getPointsCount() - position - 1);
        points[position + 1] = point;
    }

    @Override
    public String toString() {
        String str = "{";
        int count = getPointsCount();
        for (int i = 0; i < count - 1; i++) str += (points[i].toString() + ", ");
        str += points[count - 1] + "}";
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof TabulatedFunction) || (getPointsCount() != ((TabulatedFunction) o).getPointsCount())) return false;
        if (o.getClass() == ArrayListTabulatedFunction.class) {
            ArrayListTabulatedFunction oArray = (ArrayListTabulatedFunction) o;
            for (int i = 0; i < oArray.getPointsCount(); i++) if (!points[i].equals(oArray.points[i])) return false;
        }
        else {
            LinkedListTabulatedFunction oLinked = (LinkedListTabulatedFunction) o;
            for (int i = 0; i < oLinked.getPointsCount(); i++) if (!points[i].equals(oLinked.getPoint(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (int i = 0; i < getPointsCount(); i++) hash += getPoint(i).hashCode();
        hash += 31 * getPointsCount();
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        FunctionPoint[] point = new FunctionPoint[getPointsCount()];
        for (int i = 0; i < getPointsCount(); i++) point[i] = (FunctionPoint) getPoint(i).clone();
        return new ArrayListTabulatedFunction(point);
    }
}
