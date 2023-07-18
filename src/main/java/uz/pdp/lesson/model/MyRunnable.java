package uz.pdp.lesson.model;

import static uz.pdp.lesson.main.Main.integers;
import static uz.pdp.lesson.main.Main.integers1;

public class MyRunnable implements Runnable{

    @Override
    public void run() {
        while (integers.poll()!=null) {
            System.out.println(integers.poll() + " " + Thread.currentThread().getName());
        }
    }

}
