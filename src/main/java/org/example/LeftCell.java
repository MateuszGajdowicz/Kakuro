package org.example;

public class LeftCell extends SummingCell {
    public LeftCell(){setType(CellType.LEFT);}

    @Override
    public int getValue() {
        return 0;
    }
}
