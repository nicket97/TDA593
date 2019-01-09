package controller;

import java.util.List;

import javafx.event.ActionEvent;
import model.Mission;
import model.MissionPoint;

public interface MissionEditable {
	
	
	/**
	 * Creates mission
	 * @param event The event that is sent through the JavaFX Button.
	 * @return
	 */
void createMission(ActionEvent event);


    /** 
     * Edits the current mission
     * @param event The event that is sent through the JavaFX Button.
     * @return
     */
void editMission(ActionEvent event);
}
