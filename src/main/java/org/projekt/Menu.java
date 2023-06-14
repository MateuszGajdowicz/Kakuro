package org.projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    JButton quit;
    JButton play;
    JButton load;
    JButton stepBack;
    JButton ez;
    JButton medium;
    JButton hard;

    private JPanel mainPanel;
    private JPanel difficultyPanel;

    public Menu() {
        setTitle("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        play = new JButton("Play");
        play.setVisible(true);
        mainPanel.add(play);
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.setVisible(false);
                showDifficultyLevels();
            }
        });


        difficultyPanel = new JPanel(new GridLayout(5, 1));
        ez = new JButton("Easy");
        medium = new JButton("Medium");
        hard = new JButton("Hard");
        stepBack = new JButton("Step Back");

        ez.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Easy selected");
                Board board1 = new Board(6,6);
                GUI gui1 = new GUI(board1);
                gui1.setVisible(true);
                gui1.update();
            }
        });

        medium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Medium selected");
                Board board2 = new Board(15,15);
                GUI gui2 = new GUI(board2);
                gui2.setVisible(true);
                gui2.update();
            }
        });

        hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hard selected");
                Board board3 = new Board(20,20);
                GUI gui3 = new GUI(board3);
                gui3.setVisible(true);
                gui3.update();
            }
        });

        stepBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficultyPanel.setVisible(false);
                mainPanel.setVisible(true);
                setContentPane(mainPanel);
                setTitle("Main Menu");
            }
        });

        difficultyPanel.add(ez);
        difficultyPanel.add(medium);
        difficultyPanel.add(hard);
        difficultyPanel.add(stepBack);
    }

    public void showDifficultyLevels() {
        setTitle("Difficulty Levels");
        mainPanel.setVisible(false);
        difficultyPanel.setVisible(true);
        setContentPane(difficultyPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Menu menu = new Menu();
                menu.setVisible(true);
            }
        });
    }
}
