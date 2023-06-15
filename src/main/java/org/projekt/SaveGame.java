package org.projekt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveGame {
    public void saveGame(Board board) {
        try {
            FileOutputStream fileOut = new FileOutputStream("gamestate.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(board);
            objectOut.close();
            System.out.println("Zapisano stan gry.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
