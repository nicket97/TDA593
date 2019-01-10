package controller;


import model.Mission;

public interface MissionExecutable {

	/**
	 * Set the current mission to be used
	 * @param mission
	 */
	void setMission(Mission mission);

	/**
	 * Executes selected mission
	 */
	void executeMission();
	
	
	/**
	 * Cancels all execution
	 */
	void cancelExecution();
}
