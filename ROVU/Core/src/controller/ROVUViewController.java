package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.RewardCalculator;
import model.Timer;

public class ROVUViewController implements Initializable {
	@FXML Label score;
	private RewardCalculator rewardCalculator = new RewardCalculator();
	private Timer timer = new Timer();
	public void updateReward(int reward) {
        score.setText("Score: " + Integer.toString(reward));
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		new Thread(() -> {
			while (true) {
                timer.run();
                if (timer.isRunning()) {
                    Platform.runLater(() -> {
                        updateReward(rewardCalculator.calculateReward());
                    });
                }
			}
		}).start();
	}

}
