package functions;

import java.io.*;

public class TabulatedFunctions {
    private static TabulatedFunctionFactory factory = new ArrayListTabulatedFunction.ArrayTabulatedFunctionFactory();

    public static void setFactory(TabulatedFunctionFactory factory) {
        TabulatedFunctions.factory = factory;
    }

    private TabulatedFunctions() {
    }

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
        return factory.createTabulatedFunction(points);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
        return factory.createTabulatedFunction(leftX, rightX, values);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) {
        return factory.createTabulatedFunction(leftX, rightX, pointsCount);
    }

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points,
                                                            Class<TabulatedFunction> collection) {
        return factory.createTabulatedFunction(points);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX,
                                                            double[] values, Class<TabulatedFunction> collection) {
        return factory.createTabulatedFunction(leftX, rightX, values);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX,
                                                            int pointsCount, Class<TabulatedFunction> collection) {
        return factory.createTabulatedFunction(leftX, rightX, pointsCount);
    }

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        if (function.getLeftDomainBorder() > leftX || function.getRightDomainBorder() < rightX)
            throw new IllegalArgumentException("Границы функции лежат вне области определения");

        TabulatedFunction tabulatedFunction = factory.createTabulatedFunction(leftX, rightX, pointsCount);

        for (int i = 0; i < tabulatedFunction.getPointsCount(); i++) {
            tabulatedFunction.setPointY(i, function.getFunctionValue(tabulatedFunction.getPointX(i)));
        }
        return tabulatedFunction;
    }

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException {
        int pointCount = function.getPointsCount();
        DataOutputStream stream = new DataOutputStream(out);
        stream.writeInt(pointCount);

        for (int i = 0; i < pointCount; i++) {
            stream.writeDouble(function.getPointX(i));
            stream.writeDouble(function.getPointY(i));
        }

        stream.flush();
        stream.close();
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException {
        DataInputStream stream = new DataInputStream(in);
        int pointCount = stream.readInt();
        FunctionPoint points[] = new FunctionPoint[pointCount];

        for (int i = 0; i < pointCount; i++) {
            points[i] = new FunctionPoint(stream.readDouble(), stream.readDouble());
        }
        stream.close();

        return factory.createTabulatedFunction(points);
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) throws IOException {
        PrintWriter writer = new PrintWriter(out);

        writer.println(function.getPointsCount());
        for (int i = 0; i < function.getPointsCount(); i++) {
            writer.println(function.getPointX(i));
            writer.println(function.getPointY(i));
        }

        writer.close();
    }

    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        tokenizer.nextToken();

        int pointCount = (int) tokenizer.nval;

        FunctionPoint points[] = new FunctionPoint[pointCount];
        double x, y;
        for (int i = 0; i < pointCount; i++) {
            tokenizer.nextToken();
            x = tokenizer.nval;
            tokenizer.nextToken();
            y = tokenizer.nval;
            points[i] = new FunctionPoint(x, y);
        }

        return factory.createTabulatedFunction(points);
    }
}
