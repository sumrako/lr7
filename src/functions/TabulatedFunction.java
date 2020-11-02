package functions;

import java.io.*;


public interface TabulatedFunction extends Function, Serializable, Cloneable {
    int getPointsCount();

    FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException;

    void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException;

    double getPointX(int index) throws FunctionPointIndexOutOfBoundsException;

    void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException;

    double getPointY(int index) throws FunctionPointIndexOutOfBoundsException;

    void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException;

    void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, IllegalStateException;

    void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;

    Object clone() throws CloneNotSupportedException;

    default void printResults(){
        System.out.println("№p  |    X    |    Y    |");
        System.out.println("----+---------+---------+");
        for (int i = 0; i < getPointsCount(); i++) System.out.println(String.format("%-4d|%-9.3f|%-9.3f|", i, getPointX(i), getPointY(i)));
        System.out.println();
    }

}
