package org.projekt;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoadGame {
    public void loadGame() {
        try {
            FileInputStream fileIn = new FileInputStream("gamestate.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Board board = (Board) objectIn.readObject();
            objectIn.close();
            System.out.println("Załadowano stan gry.");

            SwingUtilities.invokeLater(() -> {
                GUI gui = new GUI(board);
                gui.setExtendedState(JFrame.MAXIMIZED_BOTH);
                gui.setVisible(true);
                gui.update();
            });
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
