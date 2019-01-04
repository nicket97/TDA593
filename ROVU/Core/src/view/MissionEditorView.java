package view;

import controller.MissionEditor;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MissionEditorView {
    private boolean customMission;

    public void setCustomMission(boolean customMission) {
        this.customMission = customMission;
    }

    public Stage getStage() {
        Stage missionEditorStage = new Stage();
        FXMLLoader missionEditorLoader = new FXMLLoader(getClass().getResource("../fxml/Root.fxml"));
        try {
            Parent missionEditorRoot = missionEditorLoader.load();
            MissionEditor controller = missionEditorLoader.getController();
            controller.setCustomMission(customMission);
            Scene missionEditorScene = new Scene(missionEditorRoot);
            missionEditorStage.setScene(missionEditorScene);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return missionEditorStage;
    }
}
