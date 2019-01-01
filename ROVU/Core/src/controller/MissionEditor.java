package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import model.Constraint;
import model.Mission;
import model.MissionPoint;
import project.Point;
import view.MissionEditorView;

public class MissionEditor implements MissionEditable, Initializable {
	@FXML ChoiceBox<Mission> missionChoices;
    @FXML ChoiceBox<Constraint> constraintChoices;
	@FXML TextField xTextfield, zTextfield, prioTextfield;
	@FXML Button addPointBtn, createMissionBtn,runButton;
	@FXML ListView<MissionPoint>  pointListView;
	@FXML ScrollPane execPoints = new ScrollPane();
	private List<Mission> missions = new ArrayList<>();
	private Mission mission;
	private ListView<String> list = new ListView<String>();
	private ObservableList<String> toDisplay =FXCollections.observableArrayList ();
	private Mission currentMission;
	


	public MissionEditor() {
	    // Hard-coded missions
        List<MissionPoint> points1 = Arrays.asList(new MissionPoint(-2,2), new MissionPoint(3,1));
        Mission mission1 = new Mission(points1);

        List<MissionPoint> points3 = Arrays.asList(new MissionPoint(-2.5,2.5), new MissionPoint(2.5,-2.5));
        Mission mission4 = new Mission(points3);

        List<MissionPoint> points2 = Arrays.asList(new MissionPoint(5,4), new MissionPoint(1,4));
        Mission mission2 = new Mission(points2);
        List<MissionPoint> mission = new ArrayList<>();
        mission.add(new MissionPoint(-6, -2.5, Constraint.ROBOT1));
        mission.add(new MissionPoint(-6.8, 2.5, Constraint.ROBOT1));
        mission.add(new MissionPoint(-7.5, -4, Constraint.ROBOT1));
        mission.add(new MissionPoint(-1.5, -2.5, Constraint.ROBOT2));
        mission.add(new MissionPoint(-2.3, 2.5, Constraint.ROBOT2));
        mission.add(new MissionPoint(-3, -4, Constraint.ROBOT2));
        mission.add(new MissionPoint(1.5, -2.5, Constraint.ROBOT3));
        mission.add(new MissionPoint(-2.3, 2.5, Constraint.ROBOT3));
        mission.add(new MissionPoint(3, -4, Constraint.ROBOT3));
        mission.add(new MissionPoint(6, -2.5, Constraint.ROBOT4));
        mission.add(new MissionPoint(6.8, 2.5, Constraint.ROBOT4));
        mission.add(new MissionPoint(7.5, -4, Constraint.ROBOT4));
        
        Mission mission3 = new Mission(mission);
        missions.add(mission1);
        missions.add(mission2);
        missions.add(mission3);
        missions.add(mission4);
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
   	 	execPoints.setPrefSize(200, 400);
   	 	list.setItems(toDisplay);
   	 	
   	 	execPoints.setContent(list);
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
            mission = null;
            List<MissionPoint> points = new ArrayList<MissionPoint>();
            points.addAll(pointListView.getItems());
            mission = createMission(points);
            missions.add(mission);
            addMissionChoices();
            pointListView.getItems().clear();
            System.out.println(missions);
        });
        runButton.setOnAction(event -> {
            Mission runMission = missionChoices.getValue();
            //temp
            //MissionEditorView.stage.close();
            RobotController.getController().setMission(runMission);
            RobotController.getController().executeMission();
            this.currentMission=runMission;
            System.out.println("done");
        });

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
    
    public void updateExecPoints(Point executed){
    	
    	Platform.runLater(new Runnable() {
            @Override public void run() {
                toDisplay.add(executed.toString());
                list.setItems(toDisplay);
            	execPoints.setContent(list);
            	int i=0;
            	for (MissionPoint mis: currentMission.getMission()){
            		if (mis.getPoint().getX()==executed.getX() && mis.getPoint().getZ()==executed.getZ()){
            		mis.done();
            		}
            		if (mis.isDone()){
            			i++;
            		}
            	}
            	if (i==currentMission.getMission().size()){
            		System.out.println("Mission accomplished");
            	}
            }
        });  	
    }

}