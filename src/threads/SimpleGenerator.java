package threads;

import functions.basic.Log;

public class SimpleGenerator implements Runnable{
    private Task task;

    public SimpleGenerator(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.getCountOfTasks(); i++){
            synchronized (task) {
                task.setFunction(new Log((Math.random() + 0.1) * 10));
                task.setLeft(Math.random() * 100);
                task.setRight(100 * (Math.random() + 1));
                task.setStep(Math.random());
                System.out.println("Source: " + task.toString());
            }
            Thread.yield();
        }

    }
}
