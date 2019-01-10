package model;

import controller.RobotController;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.List;

public class RewardCalculator implements IRewardCalculator {
    private RobotController rc = RobotController.getController();
    private boolean isProcedureA = true;
    private static final RewardCalculator REWARD_CALCULATOR = new RewardCalculator();
    private IntegerProperty currentRewardProperty = new SimpleIntegerProperty(0);

    private RewardCalculator() {}

    public static RewardCalculator getRewardCalculator() {
        return REWARD_CALCULATOR;
    }

    @Override
    public IntegerProperty currentRewardProperty() {
        return currentRewardProperty;
    }

    public void calculateReward() {
        currentRewardProperty.setValue(calculate());
    }

    private int calculate() {
        int rewardPoints = -99; // If it returns -99, then something's gone wrong

        List<Node> nodes = rc.getNodes();
        boolean inPhysicalArea = inPhysicalArea(nodes);
        boolean inLogicalArea = inLogicalArea(nodes);
        if (isProcedureA){
            rewardPoints = calculateProcedureA(nodes);
            if (inLogicalArea) isProcedureA = !isProcedureA;
        }
        else {
            rewardPoints = calculateProcedureB(nodes);
            if (inPhysicalArea) isProcedureA = !isProcedureA;
        }
        return rewardPoints;
    } 

    private int calculateProcedureA(List<Node> nodes) {
        int points = 0;
        for (Node node : nodes) {
            // A node can be defined as different physical areas
            List<String> areas = node.getPhysical();
            for (String area : areas) {
                if (area.matches("Surgery room.*")) {
                    points += 20;
                } else if (area.equals("Consulting room")) {
                    points += 10;
                }
            }
        }
        return points;
    }

    private int calculateProcedureB(List<Node> nodes) {
        int points = 0;
        for (Node node : nodes) {
            if (node.isWifi()) {
                points += 10;
            } else if (node.isEating()) {
                points += 20;
            }
        }
        return points;
    }

    private boolean inPhysicalArea(List<Node> nodes) {
        boolean inPhysicalArea = false;
        for (Node node : nodes) {
            if (node.isPhysical()) {
                inPhysicalArea = true;
                break;
            }
        }
        return inPhysicalArea;
    }

    private boolean inLogicalArea(List<Node> nodes) {
        boolean inLogicalArea = false;
        for (Node node : nodes) {
            if (node.isWifi() || node.isEating()) {
                inLogicalArea = true;
                break;
            }
        }
        return inLogicalArea;
    }
}
