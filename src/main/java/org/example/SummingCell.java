package org.example;

public abstract class SummingCell extends Cell {
    public void setTargetValue(int value){targetValue = value;}
    private int targetValue = 0;
    public boolean isSolved(){return targetValue == getValue();}
}
