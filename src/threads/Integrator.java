package threads;

import functions.Functions;

public class Integrator extends Thread {
    private Task task;
    private SimpleSemaphore semaphore;

    public Integrator(Task task, SimpleSemaphore semaphore) {
        this.task = task;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < task.getCountOfTasks(); i++) {
                semaphore.beginRead();
                if (task.getFunction() == null) continue;

                double integral = Functions.integral(task.getFunction(),
                            task.getLeft(),
                            task.getRight(),
                            task.getStep());

                System.out.println("Integral: " + task.toString() + " " + integral);
                semaphore.endRead();
            }
        } catch (InterruptedException e) {
            System.out.println("Correct end working integral");
        }
    }
}
