package functions;

public interface Function {
    double getLeftDomainBorder();
    double getRightDomainBorder();
    double getFunctionValue(double x);
    default void printValues(double begin, double end, double step) throws IllegalArgumentException{
        if (begin < getLeftDomainBorder() || end > getRightDomainBorder()) throw new IllegalArgumentException("Неверно заданы границы распечатки");
        System.out.println("№p  |    X    |    Y    |");
        System.out.println("----+---------+---------+");
        for (double i = begin, j = 0; i <= end; i += step, j++){
            System.out.println(String.format("%-4d|%-9.3f|%-9.3f|", (int) j, i, getFunctionValue(i)));
        }
        System.out.println();
    }
}
