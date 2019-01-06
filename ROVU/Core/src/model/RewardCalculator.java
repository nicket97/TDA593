package model;

import controller.RobotController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import project.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RewardCalculator implements IRewardCalculator {
    private RobotController rc = RobotController.getController();
    private boolean isProcedureA = true;
    private static final RewardCalculator rewardCalculator = new RewardCalculator();
    private IntegerProperty currentRewardProperty = new SimpleIntegerProperty(0);

    private RewardCalculator() {}

    public static RewardCalculator getRewardCalculator() {
        return rewardCalculator;
    }

    // Return a list of random nodes
    private List<Node> mockNodes() {
        Random r = new Random();
        int rangeMax = 10;
        int rangeMin = -10;
        List<Node> nodes = new ArrayList<>();
        double r1 = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        double r2 = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        for (int i=0; i<4; i++) {
            nodes.add(new Node(r.nextBoolean(), r.nextBoolean(), r.nextBoolean(), i, new Point(r1, r2)));
        }
        return nodes;
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
