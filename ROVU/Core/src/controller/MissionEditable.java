package controller;

import java.awt.Point;
import java.util.List;

import model.Mission;

public interface MissionEditable {
	
	
	/**
	 * Creates mission
	 * @param missionPoints
	 * @return
	 */
Mission createMission(List <Point>missionPoints);


    /** 
     * Edits mission
     * @param currentMission
     * @param missionPoints
     * @return
     */
Mission editMission(Mission currentMission, List <Point>missionPoints);
}
