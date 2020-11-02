package threads;

import functions.basic.Log;

public class Generator extends Thread {
    private Task task;
    private SimpleSemaphore semaphore;

    public Generator(Task task, SimpleSemaphore semaphore) {
        this.task = task;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < task.getCountOfTasks(); i++) {

                    semaphore.beginWrite();

                    task.setFunction(new Log((Math.random() + 0.1) * 10));
                    task.setLeft(Math.random() * 100);
                    task.setRight(100 * (Math.random() + 1));
                    task.setStep(Math.random());

                    System.out.println("Source: " + task.toString());
                    semaphore.endWrite();
            }
        } catch (InterruptedException e) {
            System.out.println("Correct end working generate");
        }
    }
}
