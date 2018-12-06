package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import project.Point;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import model.Mission;

public class MissionEditor implements MissionEditable, Initializable {
	@FXML ChoiceBox missionChoices;
	@FXML TextField xTextfield, zTextfield;
	@FXML Button addPointBtn, createMissionBtn;
	@FXML ListView<Point>  pointListView;
	private List<Mission> premadeMissions = new ArrayList<>();
	private Mission mission;

	public MissionEditor() {
	    // Hard-coded missions
        List<Point> points1 = Arrays.asList(new Point(-2,2), new Point(3,1));
        Mission mission1 = new Mission(points1);

        List<Point> points2 = Arrays.asList(new Point(5,4), new Point(1,4));
        Mission mission2 = new Mission(points2);
        premadeMissions.add(mission1);
        premadeMissions.add(mission2);
    }

	public Mission createMission(List <Point>missionPoints){
		return new Mission(missionPoints);
	}

	// Todo
	public Mission editMission(Mission currentMission, List <Point>missionPoints){
		return null;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
        premadeMissions.forEach(premadeMission -> missionChoices.getItems().add(premadeMission.toString()));
        missionChoices.getSelectionModel().selectFirst();

        // Only allows numeric values in text fields
        // Todo: Allow negative values and doubles too.
        xTextfield.textProperty().addListener((observable, oldValue, newValue)-> {
            if (!newValue.matches("\\d*")) {
                xTextfield.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        zTextfield.textProperty().addListener((observable, oldValue, newValue)-> {
            if (!newValue.matches("\\d*")) {
                zTextfield.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        addPointBtn.setOnAction(event -> {
            if (xTextfield.getText().equals("") || zTextfield.getText().equals("")) return;
            int x = Integer.parseInt(xTextfield.getText());
            int z = Integer.parseInt(zTextfield.getText());
            pointListView.getItems().add(new Point(x, z));
        });

        createMissionBtn.setOnAction(event -> {
            if (pointListView.getItems() == null) return;
            List<Point> points = pointListView.getItems();
            mission = createMission(points);
            System.out.println(mission);
        });

	}

}