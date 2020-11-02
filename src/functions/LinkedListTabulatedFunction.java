package functions;

import java.io.*;

public class LinkedListTabulatedFunction implements TabulatedFunction{
    private class FunctionNode implements Serializable {
        private FunctionPoint point;
        private FunctionNode next;
        private FunctionNode prev;

        public FunctionNode(){}

        public FunctionNode(FunctionPoint point, FunctionNode next, FunctionNode prev) {
            this.point = point;
            this.next = next;
            this.prev = prev;
        }
    }

    private int length = 0;
    private FunctionNode head = new FunctionNode();

    {
        head.point = null;
        head.next = head;
        head.prev = head;
    }

    private FunctionNode getNodeByIndex(int index) throws FunctionPointIndexOutOfBoundsException{
        if (index > length - 1 || index < 0) new FunctionPointIndexOutOfBoundsException("Неверно указан индекс");
        FunctionNode temp = head;
        for (int i = 0; i <= index; ++i) temp = temp.next;
        return temp;
    }

    private FunctionNode addNodeToHead(){
        FunctionNode nov;
        if (length == 0){
            nov = new FunctionNode();
            nov.prev = nov;
            nov.next = nov;
        }
        else {
            nov = new FunctionNode(null, getNodeByIndex(0), getNodeByIndex(length - 1));
            getNodeByIndex(0).prev = nov;
            getNodeByIndex(length - 1).next = nov;
        }
        head.next = nov;
        length++;
        return nov;
    }

    private FunctionNode addNodeToTail(){
        FunctionNode nov;
        if (length == 0){
            nov = new FunctionNode();
            nov.prev = nov;
            nov.next = nov;
            head.next = nov;
        }
        else {
            nov = new FunctionNode(null, getNodeByIndex(0), getNodeByIndex(length - 1));
            getNodeByIndex(0).prev = nov;
            getNodeByIndex(length - 1).next = nov;
        }
        length++;
        return nov;
    }

    private FunctionNode addNodeByIndex(int index) throws FunctionPointIndexOutOfBoundsException{
        if (length == 0) new FunctionPointIndexOutOfBoundsException("Ой а ваш список пуст. Возпользуйтесь addNodeToTail");
        if (index > length - 1 || index < 0) new FunctionPointIndexOutOfBoundsException("Неверно указан индекс");

        FunctionNode post = getNodeByIndex(index + 1);

        FunctionNode pre;
        pre = post.prev;

        FunctionNode nov = new FunctionNode(null, post, pre);
        post.prev = nov;
        pre.next = nov;
        //if (index == 0) head.next = nov;
        length++;

        return nov;
    }

    private FunctionNode deleteNodeByIndex(int index){
        if (index > length - 1 || index < 0) new FunctionPointIndexOutOfBoundsException("Неверно указан индекс");

        FunctionNode pre;
        pre = getNodeByIndex(index).prev;

        FunctionNode post;
        post = getNodeByIndex(index).next;

        FunctionNode cur = getNodeByIndex(index);
        //cur.next = null;
        //cur.prev = null;
        //cur.point = null;

        if (index == 0) head.next = post;
        pre.next = post;
        post.prev = pre;
        length--;

        return cur;
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        if (rightX < leftX || pointsCount < 2) throw new IllegalArgumentException("Неверно указаны границы функции");

        double stepX = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount - 1; i++) addNodeToTail().point = new FunctionPoint(leftX + stepX * i, 0);
        addNodeToTail().point = new FunctionPoint(rightX, 0);
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException {
        if (rightX < leftX || values.length < 2) throw new IllegalArgumentException("Неверно указаны границы функции");

        double stepX = (rightX - leftX) / (values.length - 1);
        for (int i = 0; i < values.length - 1; i++) addNodeToTail().point = new FunctionPoint(leftX + stepX * i, values[i]);
        addNodeToTail().point = new FunctionPoint(rightX, values[values.length - 1]);
    }

    public LinkedListTabulatedFunction(FunctionPoint[] masPoint) throws IllegalArgumentException {
        if (masPoint.length < 2) throw new IllegalArgumentException("Точек маловато");
        for (int i = 0; i < masPoint.length - 1; i++) {
            if (masPoint[i].getX() >= masPoint[i + 1].getX()) throw new IllegalArgumentException("Массив точек не отсортирован");
        }

        for (int i = 0; i < masPoint.length; i++) addNodeToTail().point = masPoint[i];
    }

    public double getLeftDomainBorder() {
        return getPointX(0);
    }

    public double getRightDomainBorder() {
        return getPointX(length - 1);
    }

    public double getFunctionValue(double x) {
        //Проверяем лежит ли точка в промежутке
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) return Double.NaN;

        //Ищем точку среди точек массива
        for (int i = 0; i < length; i++) if (getPointX(i) == x) return getPointY(i);

        //Ищем индекс левой границы промежутка нахождения искомой точки
        int i = 0;
        for (; i < length - 1; ++i) if (x > getPointX(i) && x < getPointX(i + 1)) break;
        //Находим уравнение прямой и подставляем аргумент, после возвращаем
        return (x - getPointX(i)) * (getPointY(i + 1) - getPointY(i)) / (getPointX(i + 1) - getPointX(i)) + getPointY(i);
    }

    public int getPointsCount(){
        return length;
    }

    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException{
        if (index < 0 || index > length - 1) throw new FunctionPointIndexOutOfBoundsException("Индекс указан неверно");
        return getNodeByIndex(index).point;
    }

    public void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        if (index < 0 || index > length - 1) throw new FunctionPointIndexOutOfBoundsException("Индекс указан неверно");
        if (point.getX() < getLeftDomainBorder() && index != 0 || point.getX() > getRightDomainBorder() && index != length - 1 || index != 0 && point.getX() < getPointX(index - 1) || index != length - 1 && point.getX() > getPointX(index + 1)) throw new InappropriateFunctionPointException("Неверно указано значение точки");

        getNodeByIndex(index).point = point;
    }

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException{
        if (index < 0 || index > length - 1) throw new FunctionPointIndexOutOfBoundsException("Индекс указан неверно");
        return getNodeByIndex(index).point.getX();
    }

    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        if (index < 0 || index > length - 1) throw new FunctionPointIndexOutOfBoundsException("Индекс указан неверно");
        if (x < getLeftDomainBorder() && index != 0 || x > getRightDomainBorder() && index != length - 1 || index != 0 && x < getPointX(index - 1) || index != length - 1 && x > getPointX(index + 1)) throw new InappropriateFunctionPointException("Неверно указано значение x");

        getNodeByIndex(index).point.setX(x);
    }

    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException{
        if (index < 0 || index > length - 1) throw new FunctionPointIndexOutOfBoundsException("Индекс указан неверно");
        return getNodeByIndex(index).point.getY();
    }

    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException{
        if (index < 0 || index > length - 1) throw new FunctionPointIndexOutOfBoundsException("Индекс указан неверно");
        getNodeByIndex(index).point.setY(y);
    }

    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, IllegalStateException {
        if (index < 0 || index > length - 1) throw new FunctionPointIndexOutOfBoundsException("Индекс указан неверно");
        if (length < 3) throw new IllegalStateException("При удалении будет недопустимое количество элементов");
        deleteNodeByIndex(index);
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        for (int i = 0; i < length; i++) if (point.getX() == getNodeByIndex(i).point.getX()) throw new InappropriateFunctionPointException("Данное значение абсциссы уже присутствует в функции");

        if (point.getX() < getLeftDomainBorder()) addNodeToHead().point = point;
        else if (point.getX() > getRightDomainBorder()) addNodeToTail().point = point;
        else {
            int first = 0, last = length - 1, position = (first + last)/ 2;

            while (!(point.getX() > getPointX(position) && point.getX() < getPointX(position + 1)) && (first <= last)) {
                if (getPointX(position) > point.getX()) last = position - 1;
                else first = position + 1;

                position = (first + last) / 2;
            }

            addNodeByIndex(position).point = point;
        }
    }

    @Override
    public String toString() {
        String str = "{";
        int count = getPointsCount();
        FunctionNode temp = head.next;
        for (int i = 0; i < count - 1; i++) {
            str += (temp.point.toString() + ", ");
            temp = temp.next;
        }
        str += temp.point + "}";
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof TabulatedFunction) || (getPointsCount() != ((TabulatedFunction) o).getPointsCount())) return false;
        if (o.getClass() == LinkedListTabulatedFunction.class) {
            LinkedListTabulatedFunction oLinked = (LinkedListTabulatedFunction) o;
            FunctionNode tempCur = head, tempO = oLinked.head;
            for (int i = 0; i <= getPointsCount(); ++i) {
                tempCur = tempCur.next;
                tempO = tempO.next;
                if (!tempCur.point.equals(tempO.point)) return false;
            }
        }
        else {
            ArrayListTabulatedFunction oArray = (ArrayListTabulatedFunction) o;
            for (int i = 0; i < oArray.getPointsCount(); i++) if (!getPoint(i).equals(oArray.getPoint(i))) return false;
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
        FunctionPoint[] point = new FunctionPoint[length];
        for (int i = 0; i < length; i++) point[i] = (FunctionPoint) getPoint(i).clone();
        return new LinkedListTabulatedFunction(point);
    }
}

