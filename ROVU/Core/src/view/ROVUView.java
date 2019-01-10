package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ROVUView extends Application {
    Map<String, String> parameters = new HashMap<>();

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("ROVU status");
		Parent view = FXMLLoader.load(getClass().getResource("../fxml/ROVUView.fxml"));
		Scene scene = new Scene(view);
		primaryStage.setScene(scene);
		primaryStage.setX(800);
		primaryStage.setY(525);
		primaryStage.show();
		primaryStage.setOnCloseRequest(event -> System.exit(0));

		parameters = getParameters().getNamed();

		MissionEditorView missionEditorView = new MissionEditorView();
		if (parameters.size() != 0 && parameters.containsKey("customMission")) {
		    if (parameters.get("customMission").equals("TRUE")) {
                missionEditorView.setCustomMission(true);
            }
        } else {
            missionEditorView.setCustomMission(false);
        }
		Stage missionEditorStage = missionEditorView.getStage();
		missionEditorStage.setTitle("Mission Editor");
		missionEditorStage.setX(800);
		missionEditorStage.setY(50);
		missionEditorStage.show();
		missionEditorStage.setOnCloseRequest(event -> System.exit(0));
	}
}
