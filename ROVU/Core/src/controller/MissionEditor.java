package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Constraint;
import model.Mission;
import model.MissionPoint;

public class MissionEditor implements MissionEditable, Initializable{
	@FXML ChoiceBox<Mission> missionChoices;
    @FXML ChoiceBox<Constraint> constraintChoices;
	@FXML TextField xTextfield, zTextfield, prioTextfield;
	@FXML Button addPointBtn, createMissionBtn, runButton, emergencyStopBtn;
	@FXML ListView<MissionPoint>  pointListView, executedPointListView;
	@FXML TabPane missionsTabPane;
	@FXML Tab customMissionsTab;
	private List<Mission> missions = new ArrayList<>();
	private Mission currentMission;
	private boolean customMission;

	public MissionEditor() {
	    // Hard-coded missions
        List<MissionPoint> points1 = Arrays.asList(new MissionPoint(-2,2), new MissionPoint(3,1));
        Mission mission1 = new Mission(points1);

        List<MissionPoint> points3 = Arrays.asList(new MissionPoint(-2.5,4), new MissionPoint(2.5,-2.5));
        Mission mission4 = new Mission(points3);

        List<MissionPoint> points2 = Arrays.asList(new MissionPoint(5,4), new MissionPoint(1,4));
        Mission mission2 = new Mission(points2);
        List<MissionPoint> mission = new ArrayList<>();
        mission.add(new MissionPoint(-6, -2.5, Constraint.ROBOT1, 3));
        mission.add(new MissionPoint(4.2, 4.2, Constraint.ROBOT1, 2));
        mission.add(new MissionPoint(-7.5, -4, Constraint.ROBOT1, 1));
        mission.add(new MissionPoint(-1.5, -2.5, Constraint.ROBOT2, 3));
        mission.add(new MissionPoint(4.4, 4.2, Constraint.ROBOT2, 2));
        mission.add(new MissionPoint(-3, -4, Constraint.ROBOT2, 1));
        mission.add(new MissionPoint(1.5, -2.5, Constraint.ROBOT3, 3));
        mission.add(new MissionPoint(4, 4.5, Constraint.ROBOT3, 2));
        mission.add(new MissionPoint(3, -4, Constraint.ROBOT3, 1));
        mission.add(new MissionPoint(6, -2.5, Constraint.ROBOT4, 3));
        mission.add(new MissionPoint(5, 3.8, Constraint.ROBOT4, 2));
        mission.add(new MissionPoint(7.5, -4, Constraint.ROBOT4, 1));

        Mission mission3 = new Mission(mission);
        missions.add(mission1);
        missions.add(mission2);
        missions.add(mission3);
        missions.add(mission4);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    Platform.runLater(() -> {
            if (!customMission) {
                missionsTabPane.getTabs().remove(customMissionsTab);
            }
        });
        addMissionChoices();
        addConstraintChoices();

        displayExecutedMissionPoints();

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

        runButton.setOnAction(event -> {
            currentMission = missionChoices.getValue();
            RobotController.getController().cancelExecution();
            RobotController.getController().setMission(currentMission);
            RobotController.getController().executeMission();
            displayExecutedMissionPoints();
            System.out.println("done");
        });

        emergencyStopBtn.setStyle("-fx-background-color: RED; -fx-text-fill: WHITE;");
        emergencyStopBtn.setOnAction(event -> {
            RobotController.getController().cancelExecution();
        });
	}

	public void setCustomMission(boolean customMission) {
	    this.customMission = customMission;
    }

	private void addMissionChoices() {
        missionChoices.getItems().clear();
        missions.forEach(premadeMission -> missionChoices.getItems().add(premadeMission));
        missionChoices.getSelectionModel().selectFirst();
    }

    private void addConstraintChoices () {
	    // Add empty constraint
	    constraintChoices.getItems().add(null);
        for (Constraint constraint : Constraint.values()) {
            constraintChoices.getItems().add(constraint);
        }
    }

    private void displayExecutedMissionPoints() {
	    if (currentMission == null) return;
        currentMission.getMission().forEach(missionPoint -> {
            missionPoint.doneProperty().addListener(((observable, oldValue, newValue) -> {
                if (oldValue != newValue) {
                    Platform.runLater(() -> executedPointListView.getItems().add(missionPoint));
                }
            }));
        });
    }

    // Creates a mission when the button is clicked
    @FXML
    public void createMission(ActionEvent event) {
        if (pointListView.getItems() == null || pointListView.getItems().isEmpty()) return;
        List<MissionPoint> points = new ArrayList<MissionPoint>();
        points.addAll(pointListView.getItems());
        missions.add(createMission(points));
        addMissionChoices();
        pointListView.getItems().clear();
        System.out.println(missions);
    }

    @FXML
    public void editMission(ActionEvent event) {
	    // TODO
    }

    // Helper functions
    private Mission createMission(List <MissionPoint>missionPoints){
        return new Mission(missionPoints);
    }

    // TODO
    private Mission editMission(Mission currentMission, List <MissionPoint>missionPoints){
        return null;
    }

}