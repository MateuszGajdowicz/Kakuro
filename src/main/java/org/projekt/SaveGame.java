package org.projekt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveGame {
    public void saveGame(Board board, GUI gui) {
            try {
                VisualizerAscii wizualizer = new VisualizerAscii(board);
                FileOutputStream fileOut = new FileOutputStream("gamestate.ser");
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(board);
                objectOut.writeObject(gui);
                objectOut.close();
                System.out.println("zapis");
                wizualizer.display();
                System.out.println("Zapisano stan gry.");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}

