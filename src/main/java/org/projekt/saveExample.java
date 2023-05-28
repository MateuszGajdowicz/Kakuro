package org.projekt;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class saveExample {

    public static void main(String[] args) {

        try {
            JFrame frame = new JFrame("Okno do zapisu");

            JPanel panel = new JPanel();
            JLabel label = new JLabel("Plansza");
            panel.add(label);
            frame.add(panel);
            frame.setSize(300, 300);
            frame.setVisible(true);

            Thread.sleep(1000);

            BufferedImage image = new BufferedImage(frame.getContentPane().getWidth(),
                    frame.getContentPane().getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = image.createGraphics();
            frame.getContentPane().printAll(graphics2D);
            graphics2D.dispose();

            File file = new File("plansza.png");
            ImageIO.write(image, "png", file);

            System.out.println("Plik zapisany jako: " + file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}