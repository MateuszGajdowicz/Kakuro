package org.example;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(5,5);
        VisualizerAscii v = new VisualizerAscii(board);


        List<Integer> list= Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        for(int i = 0; i < 30; i++)
        board.placeSummingField();
        v.display();

    }
}