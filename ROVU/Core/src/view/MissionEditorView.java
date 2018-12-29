package view;

import controller.RobotController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MissionEditorView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Mission Editor");
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/Root.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }

    public static void main (String[] args) {
        RobotController.getController().init();
        launch(args);

    }
}
