package view;

import controller.MissionEditor;
import controller.RobotController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MissionEditorView extends Application {
    public static Stage stage;
    private MissionEditor edit;
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        primaryStage.setTitle("Mission Editor");
        Parent root = loader.load(getClass().getResource("../fxml/Root.fxml").openStream());
        this.edit = (MissionEditor) loader.getController();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }
    
    public MissionEditor getEditor(){
    	return this.edit;
    }

    public static void main (String[] args) {
        RobotController.getController().init();
        launch(args);

    }
}
