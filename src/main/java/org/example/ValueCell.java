package org.example;

public class ValueCell extends Cell {
    public ValueCell(){setType(CellType.VALUE);}
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int element) {
        value = element;
    }
}
