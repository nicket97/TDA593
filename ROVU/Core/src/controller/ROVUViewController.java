package controller;

import java.net.URL;
import java.util.List;
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
	private RewardCalculator rewardCalculator = RewardCalculator.getRewardCalculator();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    // Listen to the current reward score
	    rewardCalculator.currentRewardProperty().addListener(((observable, oldValue, newValue) -> {
	        if (oldValue.intValue() != newValue.intValue()) {
	            Platform.runLater(() -> {
	                score.setText(newValue.intValue() + "");
                });
            }
        }));

		// Add the robots' current positions
		addLabels(RobotController.getController().getCurrentPositions(), 3);
		addLabels(RobotController.getController().getCurrentLocations(), 8);
	}

	private void addLabels(List<StringProperty> strings, int lineOffset) {
	    for (StringProperty string : strings) {
            Label label = new Label();
            label.setText(string.getValue());
            label.setLayoutX(20);
            label.setLayoutY(lineOffset++ * 20);
            pane.getChildren().add(label);
            string.addListener(((observable, oldValue, newValue) -> {
                if (newValue == null) return;
                Platform.runLater(() -> {
                    label.setText(newValue);
                });
            }));
        }
    }
}
