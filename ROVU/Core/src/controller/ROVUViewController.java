package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.RewardCalculator;
import model.Timer;

public class ROVUViewController implements Initializable {
	@FXML Label score;
	@FXML AnchorPane pane;
	private RewardCalculator rewardCalculator = new RewardCalculator();
	private Timer timer = new Timer();

	public void updateReward(int reward) {
        score.setText(Integer.toString(reward));
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
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

		// Add the robots' current positions
		int index = 3;
		for (StringProperty currentPosition : RobotController.getController().getCurrentPositions()) {
			Label label = new Label();
			label.setText(currentPosition.getValue());
			label.setLayoutX(20);
			label.setLayoutY(index++ * 20);
			pane.getChildren().add(label);
			currentPosition.addListener(((observable, oldValue, newValue) -> {
				if (newValue == null) return;
				Platform.runLater(() -> {
					label.setText(newValue);
				});
			}));
		}
	}

}
