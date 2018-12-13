package controller;

import java.util.List;

import model.Mission;
import model.MissionPoint;

public interface MissionEditable {
	
	
	/**
	 * Creates mission
	 * @param missionPoints
	 * @return
	 */
Mission createMission(List <MissionPoint>missionPoints);


    /** 
     * Edits mission
     * @param currentMission
     * @param missionPoints
     * @return
     */
Mission editMission(Mission currentMission, List <MissionPoint>missionPoints);
}
