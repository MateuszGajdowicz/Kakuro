package org.example;

public class UpCell extends SummingCell {
    public UpCell(){setType(CellType.UP);}
    @Override
    public int getValue() {
        return 0;
    }
}
