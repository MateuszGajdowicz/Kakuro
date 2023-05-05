package org.example;

public abstract class Cell {
    private CellType type = CellType.BLANK;
    public void setType(CellType t){type = t;}
    public CellType getType(){return type;}

}