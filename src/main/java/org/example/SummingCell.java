package org.example;

public abstract class SummingCell extends Cell {
    private int downTargetValue = 0;
    private int rightTargetValue = 0;
    private int rightSum = 0;
    private int downSum = 0;
    public void setRightTargetValue(int value){
        rightTargetValue = value;}

    public boolean isSolved(){return (rightTargetValue != 0 && rightTargetValue == rightSum)
            && (downTargetValue != 0 && downTargetValue == downSum);}

    public void setRightSum(int sum) {
        rightSum = sum;
    }

    public void setDownSum(int sum) {
        downSum = sum;
    }

    public int getRightTargetValue() {return  rightTargetValue;}

    public int getDownTargetValue() {return downTargetValue;}
}
