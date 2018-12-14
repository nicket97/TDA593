package controller;

import java.util.List;

import model.Mission;

public interface MissionExecutable {

	/**
	 * Gets mission from MissionEditor
	 * @return
	 */
	Mission getMission();
	
	
	/**
	 * Executes selected mission
	 * @param mission
	 */
	void executeMission(Mission mission);
	
	
	/**
	 * Cancels all execution
	 */
	void cancelExecution();
	
	
	/**
	 * Gets data from the robot (sensors, position etc)
	 * @return
	 */
	List<DataObject> getData();
	
}
