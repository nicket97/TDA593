package model;

import controller.DataHandler;
import controller.ROVUViewController;
import controller.RobotController;
import project.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RewardCalculator {
    // TODO: Retrieve singleton RobotController as the DataHandler
    // DataHandler dataHandler = new RobotController();
    private boolean isA = true;
    private ROVUViewController rvcontroller = new ROVUViewController();

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

    public void calculateReward() {
    	int reward = calculate();
    	rvcontroller.updateReward(reward);
    }
    
    public int calculate() {
        int rewardPoints = -99; // If it returns -99, then something's gone wrong
        // TODO: Get the nodes from the RobotController
        List<Node> nodes = mockNodes();
        boolean inPhysicalArea = inPhysicalArea(nodes);
        boolean inLogicalArea = inLogicalArea(nodes);
        // TODO: Implement PhysicalAreas, else it won't calculate anything
        if (isA){
            rewardPoints = calculateProcedureA(nodes);
            if (inLogicalArea) isA = !isA;
        }
        else {
            rewardPoints = calculateProcedureB(nodes);
            if (inPhysicalArea) isA = !isA;
        }
        return rewardPoints;
    }

    private int calculateProcedureA(List<Node> nodes) {
        int points = 0;
        for (Node node : nodes) {
            /* TODO: Need to update Node so that it
             * differentiates between CONSULTING_ROOM
             * and SURGERY_ROOM */
            if (node.isRoom()) {
                points++;
            }
//            if (node.isConsultingRoom()) { or node.isRoom(Hospital.CONSULTING_ROOM)
//                points += 10;
//            } else if (node.isSurgeryRoom()) { or node.is(Hospital.SURGERY_ROOM)
//                points += 20;
//            }
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
            if (!node.isWall()) {
                inPhysicalArea = true;
            }
        }
        return inPhysicalArea;
    }

    private boolean inLogicalArea(List<Node> nodes) {
        boolean inLogicalArea = false;
        for (Node node : nodes) {
            if (node.isWifi() || node.isEating()) {
                inLogicalArea = true;
            }
        }
        return inLogicalArea;
    }
}
