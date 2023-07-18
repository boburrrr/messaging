package uz.pdp.lesson.model;

import static uz.pdp.lesson.main.Main.integers;

public class MyThread extends Thread{
    @Override
    public void run() {
        System.out.println(integers.poll());
    }
}
