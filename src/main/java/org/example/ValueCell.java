package org.example;

public class ValueCell extends Cell {
    public ValueCell(){}
    private int value;
    //for doing hints, code needs to know what to write in a cell as a hint
    //???
    private int targetValue;

    public int getValue() {
        return value;
    }

    public void setValue(int element) {
        value = element;
    }
}
