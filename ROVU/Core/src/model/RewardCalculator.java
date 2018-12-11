package model;

public class RewardCalculator {
    static boolean isA = true;
    public static void calculateReward() {
        if(isA){
            calculateProcedureA();
        }
        else{
            calculateProcedureB();
        }
    }

    private static void calculateProcedureA() {
    }

    private static void calculateProcedureB() {
    }
}
