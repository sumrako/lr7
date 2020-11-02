package functions;

import java.io.*;

public class TabulatedFunctions {
    private TabulatedFunctions() {
    }

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        if (function.getLeftDomainBorder() > leftX || function.getRightDomainBorder() < rightX)
            throw new IllegalArgumentException("Границы функции лежат вне области определения");
        TabulatedFunction tabulatedFunction = new ArrayListTabulatedFunction(leftX, rightX, pointsCount);
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
        return new ArrayListTabulatedFunction(points);
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

        return new ArrayListTabulatedFunction(points);
    }
}
