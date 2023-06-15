package org.projekt;

import java.io.Serializable;
import java.util.*;

public class Board implements MatrixInterface<Cell>, Serializable {
    private Stack<List<Cell>> moveHistory;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.moveHistory = new Stack<>();
        innitBoardWithBlanks();
    }

    Random random = new Random();

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

    // dodanie do stosu biezacego stanu planszy
    private void saveCurrentState() {
        moveHistory.push(new ArrayList<>(cells));
    }

    // przywracanie poprzedniego stanu planszy
    private void restorePreviousState() {
        if (!moveHistory.isEmpty()) {
            cells = moveHistory.pop();
        }
    }


    // cofanie ruchu
    public void undoLastMove() {
        if (moveHistory.isEmpty()) {
            System.out.println("Brak ruchów do cofnięcia.");
        } else {
            restorePreviousState();
            System.out.println("Cofnięcie ruchu powiodło się.");
        }
    }

//    public void placeSummingField() {
//        int x = random.nextInt(width);
//        int y = random.nextInt(height);
//        if ((get(x, y) instanceof BlankCell)) {
//            saveCurrentState(); // Zapisanie bieżącego stanu planszy przed wykonaniem ruchu
//            set(x, y, new SummingCell());
//            fillRight(x + 1, y, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
//            fillDown(x, y + 1, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
//        }
//    }

    public void checkBoard() {
        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < height - 1; j++) {
                var current = get(i, j);
                var right = get(i + 1, j);
                var down = get(i, j + 1);

                if (
                        current instanceof SummingCell &&
                                (right instanceof SummingCell || right instanceof BlankCell) &&
                                (down instanceof SummingCell || down instanceof BlankCell)) {
                    set(i, j, new BlankCell());
                } else if (current instanceof SummingCell && right instanceof BlankCell && down instanceof BlankCell) {
                    set(i, j, new BlankCell());

                }
                if (
                        get(width - 1, j) instanceof SummingCell &&
                                (get(width - 1, j + 1) instanceof BlankCell || get(width - 1, j + 1) instanceof SummingCell)) {
                    set(width - 1, j, new BlankCell());
                } else if (get(i, height - 1) instanceof SummingCell &&
                        (get(i + 1, height - 1) instanceof BlankCell || get(i + 1, height - 1) instanceof SummingCell)) {
                    set(i, height - 1, new BlankCell());
                }

            }
        }
    }


    public Cell randomizeCell() {
        int cos = random.nextInt(100);
        if (cos < 50) {
            //BlankCell
            return new BlankCell();
        } else {
            //SummingCell
            return new SummingCell();
        }
    }

    public void placeSumming() {
        set(0, 0, new BlankCell());
        for (int x = 1; x < width; x++) {
            for (int y = 1; y < height; y++) {
                set(x, 0, randomizeCell());
                set(0, y, randomizeCell());
            }
        }
        set(width - 1, height - 1, new BlankCell());
    }


    public void placeValue() {
        for (int x = 1; x < width; x++) {
            for (int y = 1; y < height; y++) {
                set(x, y, new ValueCell());
            }
        }
    }

    public void checkColumn() {
        for (int x = 1; x < width; x++) {
            if (get(x, 0) instanceof BlankCell) {
                set(x, 1, new SummingCell());
            }
        }
    }

    public void checkRow() {
        for (int y = 1; y < height; y++) {
            if (get(0, y) instanceof BlankCell) {
                set(1, y, new SummingCell());
            }
        }
    }

    public void fillSumming() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (get(x, y) instanceof SummingCell) {
                    int move = random.nextInt(2, 9);
                    if (x + move < width) {
                        set(x + move, y, new SummingCell());
                    }
                    if (y + move < height) {
                        set(x, y + move, new SummingCell());
                    }
                }
            }
        }
    }

    public void chooseValue(int i, int j, List<Integer> numbers) {
        List<Integer> numbersInside = numbers;
        for (int x = i; x < width; x++) {
            for (int y = j; y < height; y++) {
                if (get(x, y) instanceof ValueCell) {
                    int randomIndex = random.nextInt(numbersInside.size());
                    int chosenNumber = numbersInside.get(randomIndex);
                    ((ValueCell) get(x, y)).setTargetValue(chosenNumber);
                    System.out.println(((ValueCell) get(x, y)).getTargetValue());
                    //fillRight(x + 1, y, numbers.stream().filter(number -> number != chosenNumber).toList());
                    numbersInside = numbersInside.stream().filter(number -> number != chosenNumber).toList();
                } else numbersInside = numbers;
            }

        }
    }

    public void setSummingValues() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (get(x, y) instanceof SummingCell) {
                    for (int i = x + 1; i < width; i++) {
                        if (get(i, y) instanceof ValueCell) {
                            int currentRightValue = ((SummingCell) get(x, y)).getRightTargetValue();
                            ((SummingCell) get(x, y)).setRightTargetValue(((ValueCell) get(i, y)).getTargetValue() + currentRightValue);
                        } else break;

                    }
                    for (int j = y + 1; j < height; j++) {
                        if (get(x, j) instanceof ValueCell) {
                            int currentDownValue = ((SummingCell) get(x, y)).getDownTargetValue();
                            ((SummingCell) get(x, y)).setDownTargetValue(((ValueCell) get(x, j)).getTargetValue() + currentDownValue);
                        } else break;
                    }
//                    System.out.println(((SummingCell) get(x, y)).getDownTargetValue());
//                    System.out.println(((SummingCell) get(x, y)).getRightTargetValue());
                }

            }
        }
    }
//    public boolean recalculateAllSummings() {
//        boolean isSolved = false;
//        boolean isRightSumming = false;
//        boolean isDownSumming = false;
//        int currentRightSum = 0;
//        int currentDownSum = 0;
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                if (get(x, y) instanceof SummingCell) {
//                    for (int i = x + 1; i < width; i++) {
//                        if (get(i, y) instanceof ValueCell) {
//                            currentRightSum = ((SummingCell) get(x, y)).getRightSum();
//                            ((SummingCell) get(x, y)).setRightSum(((ValueCell) get(i, y)).getValue() + currentRightSum);
//                        } else if (currentRightSum == ((SummingCell) get(x, y)).getRightTargetValue()) {
//                            isRightSumming = true;
//                            break;
//                        } else break;
//                    }
//                    for (int j = y + 1; j < height; j++) {
//                        if (get(x, j) instanceof ValueCell) {
//                            currentDownSum = ((SummingCell) get(x, y)).getDownSum();
//                            ((SummingCell) get(x, y)).setDownSum(((ValueCell) get(x, j)).getValue() + currentDownSum);
//                        } else if (currentDownSum == ((SummingCell) get(x, y)).getDownTargetValue()) {
//                            isDownSumming = true;
//                            break;
//                        } else break;
//                    }
//                }
//                if (isRightSumming && isDownSumming) {
//                    isSolved = true;
//                } else {
//                    isSolved = false;
//                    //break;
//                }
//            }
//        }
//        if (isSolved){
//            System.out.println("Solved");
//        } else {
//            System.out.println("Not solved");
//        }
//        return isSolved;
//    }

    public boolean recalculateAllSummings(){
        int mistakeCounter = 0;
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                if (get(x, y) instanceof ValueCell){
                    if (((ValueCell) get(x, y)).getValue() != ((ValueCell) get(x, y)).getTargetValue()){
                        mistakeCounter++;
                    }
                }
            }
        }
        if (mistakeCounter == 0){
            System.out.println("Solved");
            return true;
        } else {
            System.out.println("Not solved");
            return false;
        }
    }

    public void hint(){
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        if (get(x, y) instanceof ValueCell){
            ((ValueCell) get(x, y)).setValue(((ValueCell) get(x, y)).getTargetValue());
        }else hint();
    }



//    public void checkIfAlreadyExists(){
//        List<Integer> listRight = new ArrayList<>();
//        List<Integer> listDown = new ArrayList<>();
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                if (get(x, y) instanceof SummingCell) {
//                    for (int i = x + 1; i < width; i++) {
//                        if (get(i, y) instanceof ValueCell) {
//                            if (listRight.contains(((ValueCell) get(i, y)).getTargetValue())){
//                                int value = ((ValueCell) get(i, y)).getTargetValue();
//                                if (value < 9){
//                                    ((ValueCell) get(i, y)).setTargetValue(value + 1);
//                                    listRight.add(value + 1);
//                                } else {
//                                    value = random.nextInt(1, 9);
//                                    ((ValueCell) get(i, y)).setTargetValue(value);
//                                    listRight.add(value - 1);
//                                }
//
////                                System.out.println("lista prawo");
////                                System.out.println(listRight);
//                            }else listRight.add(((ValueCell) get(i, y)).getTargetValue());
//
//                        } else listRight.clear();
//                    }
//                    for (int j = y + 1; j < height; j++) {
//                        if (get(x, j) instanceof ValueCell) {
//                            if (listDown.contains(((ValueCell) get(x, j)).getTargetValue())){
//                                int value = ((ValueCell) get(x, j)).getTargetValue();
//                                if (value < 9){
//                                    ((ValueCell) get(x, j)).setTargetValue(value + 1);
//                                    listDown.add(value + 1);
//                                } else {
//                                    value = random.nextInt(1, 9);
//                                    ((ValueCell) get(x, j)).setTargetValue(value);
//                                    listDown.add(value - 1);
//                                }
//
////                                System.out.println("lista dół");
////                                System.out.println(listDown);
//                            }else listDown.add(((ValueCell) get(x, j)).getTargetValue());
//
//                        } else listDown.clear();
//
//                    }
//
////                    System.out.println(((SummingCell) get(x, y)).getDownTargetValue());
////                    System.out.println(((SummingCell) get(x, y)).getRightTargetValue());
//                }
//
//            }
//        }
//    }

}






