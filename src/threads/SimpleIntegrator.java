package threads;

import functions.Functions;

public class SimpleIntegrator implements Runnable{
    private Task task;

    public SimpleIntegrator(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.getCountOfTasks(); i++){
            if (task.getFunction() == null) continue;
            double integral;

            synchronized (task) {
                integral = Functions.integral(task.getFunction(),
                        task.getLeft(),
                        task.getRight(),
                        task.getStep());
                System.out.println("Integral: " + task.toString() + " " + integral);
            }
            Thread.yield();
        }
    }
}
