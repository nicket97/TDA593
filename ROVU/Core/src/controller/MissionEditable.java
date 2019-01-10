package controller;

import javafx.event.ActionEvent;

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
