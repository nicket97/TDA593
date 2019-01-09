package model;

import javafx.beans.property.IntegerProperty;

public interface IRewardCalculator {
    void calculateReward();
    IntegerProperty currentRewardProperty();
}
