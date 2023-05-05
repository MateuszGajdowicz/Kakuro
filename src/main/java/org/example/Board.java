package org.example;

public class Board implements MatrixInterface<Cell> {
    private boolean isSolved = false;
    @Override
    public Cell get(int x, int y) {
        return null;
    }

    @Override
    public void setValue(int x, int y, int element) {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    public void sumRight(int x, int y){
        int sum = 0;
        for(int i = x; i<getWidth(); i++){
            Cell currentCell = get(i, y);
            if(currentCell.getType() != CellType.VALUE){break;}
            sum += currentCell.getValue();
        }
        setValue(x, y, sum);
    }
    public void sumDown(int x, int y) {
        int sum = 0;
        for (int i = y; i < getHeight(); i++) {
            Cell currentCell = get(x, i);
            if (currentCell.getType() != CellType.VALUE) {break;}
            sum += currentCell.getValue();
        }
        setValue(x, y, sum);
    }

    public void recalculateAllSums(){
        isSolved = true;
        for(int x = 0; x < getWidth(); x++){
            for(int y = 0; y < getHeight(); y++){
                Cell currentCell = get(x, y);
                if(currentCell.getType() == CellType.DOWN){
                    sumDown(x, y);
                    isSolved = isSolved && ((SummingCell)currentCell).isSolved();
                } else if (currentCell.getType() == CellType.RIGHT) {
                    sumRight(x, y);
                    isSolved = isSolved && ((SummingCell)currentCell).isSolved();
                }
            }
        }
    }
}
