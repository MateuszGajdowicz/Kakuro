package org.example;

public class Board implements MatrixInterface<Cell> {
    public Board(int height, int width){
        this.height = height;
        this.width = width;
        cells = new Cell[width * height];
    }
    private boolean isSolved = false;
    @Override
    public Cell get(int x, int y) {
        return cells[width * y + x];
    }
//
//    @Override
//    public void setValue(int x, int y, int element) {
//        cells[width * y + x].setValue(element);
//    }
    private int height;
    private int width;
    private Cell cells[];
    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
    public void sumBoth(int x, int y){
        Cell currentCell = get(x, y);
        if(currentCell instanceof SummingCell) {
            if (((SummingCell) currentCell).getRightTargetValue() != 0) {
                sumRight(x, y);
            }
            if (((SummingCell) currentCell).getDownTargetValue() != 0) {
                sumDown(x, y);
            }
        }
    }
    public void sumRight(int x, int y){
        int sum = 0;
        for(int i = x; i<getWidth(); i++) {
            if (i != x) {
                Cell currentCell = get(i, y);
                if (currentCell.getType() == CellType.VALUE) {
                    sum += ((ValueCell)currentCell).getValue();
                }
                else break;
            }
        }
        ((SummingCell)get(x, y)).setRightSum(sum);
    }
    public void sumDown(int x, int y) {
        int sum = 0;
        for (int i = y; i < getHeight(); i++) {
            if (i != y) {
                Cell currentCell = get(x, i);
                if (currentCell.getType() == CellType.VALUE) {
                    sum += ((ValueCell)currentCell).getValue();
                }
                else break;
            }
        }
        ((SummingCell)get(x, y)).setDownSum(sum);
    }

    public void recalculateAllSums(){
        isSolved = true;
        for(int x = 0; x < getWidth(); x++){
            for(int y = 0; y < getHeight(); y++){
                Cell currentCell = get(x, y);
                if(currentCell instanceof SummingCell){
                    sumBoth(x, y);
                    isSolved = isSolved && ((SummingCell)currentCell).isSolved();
                }
            }
        }
    }
}
