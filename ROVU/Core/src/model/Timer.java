package model;

/**
 * A timer which ticks every 20 second
 */
public class Timer implements Runnable {
    private boolean running = true;
    private final long SLEEP_TIME = 20000;
    private IRewardCalculator rewardCalculator = RewardCalculator.getRewardCalculator();
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(SLEEP_TIME);
                rewardCalculator.calculateReward();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopTimer(){
        running = false;
    }
    public void startTimer(){
        running = true;
    }

    public boolean isRunning() {
        return this.running;
    }
}
