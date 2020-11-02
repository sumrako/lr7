package threads;

import functions.Function;

public class Task {
    private Function function;
    private double left;
    private double right;
    private double step;
    private int countOfTasks;

    public Task(int countOfTasks) {
        this.countOfTasks = countOfTasks;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public void setRight(double right) {
        this.right = right;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public int getCountOfTasks() {
        return countOfTasks;
    }

    public Function getFunction() {
        return function;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public double getStep() {
        return step;
    }

    @Override
    public String toString() {
        return left + " " + right + " " + step;
    }
}
