package org.projekt;

public class ValueCell extends Cell {
    public ValueCell(){}
    private int value;
    //for doing hints, code needs to know what to write in a cell as a hint
    //???
    private int targetValue;

    public int getValue() {
        return value;
    }

    public void hint(){this.value = targetValue;}

    public void setValue(int element) {
        value = element;
    }

    public void setTargetValue(int element) {
        targetValue = element;
    }
    public int getTargetValue() {
        return targetValue;
    }
}
