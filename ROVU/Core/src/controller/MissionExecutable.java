package controller;

import java.util.List;

import model.Mission;

public interface MissionExecutable {

	/**
	 * Set the current mission to be used
	 * @param mission
	 */
	void setMission(Mission mission);

	/**
	 * Gets mission from MissionEditor
	 * @return
	 */
	Mission getMission();

	/**
	 * Executes selected mission
	 */
	void executeMission();
	
	
	/**
	 * Cancels all execution
	 */
	void cancelExecution();
	
	
	/**
	 * Gets data from the robot (sensors, position etc)
	 * @return
	 */
	List getData();
	
}
