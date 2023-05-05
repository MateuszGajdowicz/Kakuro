package org.example;

public class RightCell extends SummingCell {
    public RightCell(){setType(CellType.RIGHT);}

    @Override
    public int getValue() {
        return 0;
    }
}
