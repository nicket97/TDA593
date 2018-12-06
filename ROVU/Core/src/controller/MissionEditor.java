package controller;

import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import model.Mission;

public class MissionEditor implements MissionEditable, Initializable {
	@FXML ChoiceBox missionChoices;
	private List<Mission> premadeMissions = new ArrayList<>();

	public MissionEditor() {
        List<Point> points1 = Arrays.asList(new Point(-2,2), new Point(3,1));
        Mission mission1 = new Mission(points1);

        List<Point> points2 = Arrays.asList(new Point(5,4), new Point(1,4));
        Mission mission2 = new Mission(points2);
        premadeMissions.add(mission1);
        premadeMissions.add(mission2);
    }

	public Mission createMission(List <Point>missionPoints){
		return null;
	}
	
	public Mission editMission(Mission currentMission, List <Point>missionPoints){
		return null;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
        premadeMissions.forEach(premadeMission -> missionChoices.getItems().add(premadeMission.toString()));
	}
}
