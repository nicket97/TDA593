package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ROVUView extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("ROVU status");
		Parent view = FXMLLoader.load(getClass().getResource("../fxml/ROVUView.fxml"));
		Scene scene = new Scene(view);
		primaryStage.setScene(scene);
		primaryStage.show();

		primaryStage.setOnCloseRequest(event -> System.exit(0));
	}

	public static void main(String[] args) {
		launch(args);
	}
}
