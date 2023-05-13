package org.projekt;

import java.util.*;

public class Board implements MatrixInterface<Cell> {
    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        innitBoardWithBlanks();
    }

    Random random = new Random();
    int summingProbability = 15;
    int valueProbability = 45;

    public Cell chooseCellType() {
        int cos = random.nextInt(100);
        if (cos < summingProbability) {
            //BlankCell
            return new BlankCell();
        } else if (cos < valueProbability) {
            //SummingCell
            return new SummingCell();
        } else {
            //ValueCell
            return new ValueCell();
        }
    }

    public void placeSummingField() {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        if ((get(x, y) instanceof BlankCell)) {
            set(x, y, new SummingCell());
            fillRight(x + 1, y, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
            fillDown(x, y + 1, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        }
    }

    private void fillDown(int x, int y, List<Integer> numbers) {
        Cell currentCell = chooseCellType();
        System.out.println(numbers);
        //Cell currentCell = new ValueCell();

        if (y >= height) {

        } else if (!(get(x, y) instanceof BlankCell)) {

        } else if (numbers.size() == 0) {

        } else if (currentCell instanceof ValueCell) {
            ValueCell newCell = new ValueCell();
            int randomIndex = random.nextInt(numbers.size());
            int chosenNumber = numbers.get(randomIndex);
            newCell.setValue(chosenNumber);
            set(x, y, newCell);
            fillDown(x, y + 1, numbers.stream().filter(number -> number != chosenNumber).toList());
        } else if (currentCell instanceof BlankCell) {
            set(x, y, new BlankCell());
        }
    }

    public void fillRight(int x, int y, List<Integer> numbers) {
        Cell currentCell = chooseCellType();
        System.out.println(numbers);
        //Cell currentCell = new ValueCell();

        if (x >= width) {

        } else if (!(get(x, y) instanceof BlankCell)) {

        } else if (numbers.size() == 0) {

        } else if (currentCell instanceof ValueCell) {
            ValueCell newCell = new ValueCell();
            int randomIndex = random.nextInt(numbers.size());
            int chosenNumber = numbers.get(randomIndex);
            newCell.setValue(chosenNumber);
            set(x, y, newCell);
            fillRight(x + 1, y, numbers.stream().filter(number -> number != chosenNumber).toList());
        } else if (currentCell instanceof BlankCell) {
            set(x, y, new BlankCell());
        }
    }

    private boolean isSolved = false;

    @Override
    public Cell get(int x, int y) {
        return cells.get(width * y + x);
    }

    private int height;
    private int width;
    private List<Cell> cells;

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void sumBoth(int x, int y) {
        Cell currentCell = get(x, y);
        if (currentCell instanceof SummingCell) {
            if (((SummingCell) currentCell).getRightTargetValue() != 0) {
                sumRight(x, y);
            }
            if (((SummingCell) currentCell).getDownTargetValue() != 0) {
                sumDown(x, y);
            }
        }
    }

    public void sumRight(int x, int y) {
        int sum = 0;
        for (int i = x; i < getWidth(); i++) {
            if (i != x) {
                Cell currentCell = get(i, y);
                if (currentCell instanceof ValueCell) {
                    sum += ((ValueCell) currentCell).getValue();
                } else break;
            }
        }
        ((SummingCell) get(x, y)).setRightSum(sum);
    }

    public void sumDown(int x, int y) {
        int sum = 0;
        for (int i = y; i < getHeight(); i++) {
            if (i != y) {
                Cell currentCell = get(x, i);
                if (currentCell instanceof ValueCell) {
                    sum += ((ValueCell) currentCell).getValue();
                } else break;
            }
        }
        ((SummingCell) get(x, y)).setDownSum(sum);
    }

    public void recalculateAllSums() {
        isSolved = true;
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                Cell currentCell = get(x, y);
                if (currentCell instanceof SummingCell) {
                    sumBoth(x, y);
                    isSolved = isSolved && ((SummingCell) currentCell).isSolved();
                }
            }
        }
    }

    public void innitBoardWithBlanks() {
        cells = new ArrayList<Cell>(width * height);
        for (int i = 0; i < width * height; i++) {
            cells.add(new BlankCell());
        }
    }

    public void set(int x, int y, Cell cell) {
        cells.set(x + y * width, cell);
    }
}
