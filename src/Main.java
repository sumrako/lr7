import functions.*;
import functions.basic.Cos;
import functions.basic.Exp;
import functions.basic.Log;
import functions.basic.Sin;
import functions.meta.Composition;
import functions.meta.Sum;
import threads.*;

import java.io.*;

/**
* <b>Пакет работы с табулированными функциями</b>
 * пример использования
* @version 3.0
* @author Mr.Arnautov
 */
public class Main {
    public static void nonThreads(){
        Task task = new Task(150);

        for (int i = 0; i < 150; i++){
            Function log = new Log((Math.random() + 0.1) * 10);
            double left = Math.random() * 100;
            double right = 100 * (Math.random() + 1);
            double step = Math.random();

            System.out.println("Source " + task.toString());
            System.out.println("Result " + task.toString() + " " + Functions.integral(log, left, right, step));
        }
    }

    public static void simpleThreads(){
        Task task = new Task(150);

        Thread generator = new Thread(new SimpleGenerator(task));
        generator.start();

        Thread integrator = new Thread(new SimpleIntegrator(task));
        integrator.start();
    }

    public static void complicatedThreads() throws InterruptedException {
        Task task = new Task(150);

        SimpleSemaphore semaphore = new SimpleSemaphore();

        Thread generator = new Generator(task, semaphore);
        generator.start();

        Thread integrator = new Integrator(task, semaphore);
        integrator.start();

        Thread.sleep(50);
        generator.interrupt();
        integrator.interrupt();
        //generator.setPriority(Thread.MIN_PRIORITY);
        //integrator.setPriority(Thread.MAX_PRIORITY);
    }


    public static void main(String[] arg) {
        try {
            TabulatedFunction f = new ArrayListTabulatedFunction(0, 1, new double[]{1, 2, 3});/* получение или создание объекта */;
            for (FunctionPoint p : f) {
                System.out.println(p);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
