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
            System.out.println("wczyt");
            VisualizerAscii wizualizer = new VisualizerAscii(board);
            wizualizer.display();
            objectIn.close();
            fileIn.close();
            System.out.println("Załadowano stan gry.");


            SwingUtilities.invokeLater(() -> {
                GUI gui = new GUI(board, true);
                gui.setExtendedState(JFrame.MAXIMIZED_BOTH);
                gui.setVisible(true);
                gui.update();

            });
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
