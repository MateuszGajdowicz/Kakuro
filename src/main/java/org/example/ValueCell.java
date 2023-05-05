package org.example;

public class ValueCell extends Cell {
    public ValueCell(){setType(CellType.VALUE);}
    @Override
    public int getValue() {
        return 0;
    }
}
