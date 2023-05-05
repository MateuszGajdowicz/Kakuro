package org.example;

public class DownCell extends SummingCell {
    public DownCell(){setType(CellType.DOWN);}

    @Override
    public int getValue() {
        return 0;
    }
}
