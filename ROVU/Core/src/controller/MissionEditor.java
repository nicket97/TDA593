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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import model.Constraint;
import model.Mission;
import model.MissionPoint;

public class MissionEditor implements MissionEditable, Initializable {
	@FXML ChoiceBox<Mission> missionChoices;
    @FXML ChoiceBox<Constraint> constraintChoices;
	@FXML TextField xTextfield, zTextfield, prioTextfield;
	@FXML Button addPointBtn, createMissionBtn;
	@FXML ListView<MissionPoint>  pointListView;
	private List<Mission> premadeMissions = new ArrayList<>();
	private Mission mission;

	public MissionEditor() {
	    // Hard-coded missions
        List<MissionPoint> points1 = Arrays.asList(new MissionPoint(-2,2), new MissionPoint(3,1));
        Mission mission1 = new Mission(points1);

        List<MissionPoint> points2 = Arrays.asList(new MissionPoint(5,4), new MissionPoint(1,4));
        Mission mission2 = new Mission(points2);
        premadeMissions.add(mission1);
        premadeMissions.add(mission2);
    }

	public Mission createMission(List <MissionPoint>missionPoints){
		return new Mission(missionPoints);
	}

	// Todo
	public Mission editMission(Mission currentMission, List <MissionPoint>missionPoints){
		return null;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
        addMissionChoices();
        addConstraintChoices();

        // Only allows double values that can also be negative
        Arrays.asList(xTextfield, zTextfield).forEach(textField ->
        textField.textProperty().addListener((observable, oldValue, newValue)-> {
            if (!newValue.matches("^[-]?\\d*(\\.\\d*)?")) {
                textField.setText(oldValue);
            }
        }));
        // Only allows integers
        prioTextfield.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                prioTextfield.setText(newValue.replaceAll("[^\\d]", ""));
            }
        }));

        addPointBtn.setOnAction(event -> {
            if (xTextfield.getText().equals("") || zTextfield.getText().equals("")) return;
            Double x = Double.parseDouble(xTextfield.getText());
            Double z = Double.parseDouble(zTextfield.getText());
            int prio = 0;
            if (!prioTextfield.getText().equals("")) {
                prio = Integer.parseInt(prioTextfield.getText());
            }
            if (constraintChoices.getValue() != null) {
                pointListView.getItems().add(new MissionPoint(x, z, constraintChoices.getValue(), prio));
            } else {
                pointListView.getItems().add(new MissionPoint(x, z, prio));
            }
        });

        createMissionBtn.setOnAction(event -> {
            if (pointListView.getItems() == null) return;
            List<MissionPoint> points = pointListView.getItems();
            mission = createMission(points);
            System.out.println(mission);
        });

	}

	private void addMissionChoices() {
        premadeMissions.forEach(premadeMission -> missionChoices.getItems().add(premadeMission));
        missionChoices.getSelectionModel().selectFirst();
    }

    private void addConstraintChoices () {
	    // Add empty constraint
	    constraintChoices.getItems().add(null);
        for (Constraint constraint : Constraint.values()) {
            constraintChoices.getItems().add(constraint);
        }
    }

}