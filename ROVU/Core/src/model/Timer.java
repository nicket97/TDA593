package model;

public class Timer implements Runnable {
    boolean running = true;
    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (running){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void stopTimer(){
        running = false;
    }
    public void startTimer(){
        running = true;
    }
}
