package org.projekt;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu extends JFrame {
    private JButton quit;
    private JButton play;
    private JButton back;
    private JButton load;
    private JLabel ez;
    private JLabel medium;
    private JLabel hard;

    private JPanel mainPanel;
    private JPanel difficultyPanel;

    public Menu() {
        setTitle("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        mainPanel = new JPanel(new GridBagLayout());
        setContentPane(new JLabel(new ImageIcon("kauro2.png")));

        play = new JButton();
        try {
            Image ezImage = ImageIO.read(new File("PLAY.png"));
            play.setIcon(new ImageIcon(ezImage));
            play.setContentAreaFilled(false);
            play.setBorderPainted(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        load = new JButton();
        try {
            Image ezImage = ImageIO.read(new File("LOAD.png"));
            load.setIcon(new ImageIcon(ezImage));
            load.setContentAreaFilled(false);
            load.setBorderPainted(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        quit = new JButton();
        try {
            Image ezImage = ImageIO.read(new File("QUIT.png"));
            quit.setIcon(new ImageIcon(ezImage));
            quit.setContentAreaFilled(false);
            quit.setBorderPainted(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainPanel.setOpaque(false);
        mainPanel.add(play);
        mainPanel.add(load);
        mainPanel.add(quit);

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.setVisible(false);
                showDifficultyLevels();
            }
        });

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the load_game button click event
                LoadGame loadGame = new LoadGame();
                loadGame.loadGame();
            }
        });

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        difficultyPanel = new JPanel(new GridBagLayout());
        try {
            BufferedImage backgroundImage = ImageIO.read(new File("kauro2.png"));
            difficultyPanel = new JPanel(new GridBagLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            };
        } catch (IOException e) {
            e.printStackTrace();
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ez = new JLabel();
        medium = new JLabel();
        hard = new JLabel();
        back = new JButton();

        try {
            Image ezImage = ImageIO.read(new File("EASY.png"));
            ez.setIcon(new ImageIcon(ezImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Image ezImage = ImageIO.read(new File("MEDIUM.png"));
            medium.setIcon(new ImageIcon(ezImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Image ezImage = ImageIO.read(new File("HARD.png"));
            hard.setIcon(new ImageIcon(ezImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Image ezImage = ImageIO.read(new File("BACK.png"));
            back.setIcon(new ImageIcon(ezImage));
            back.setContentAreaFilled(false);
            back.setBorderPainted(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        buttonPanel.add(ez);
        buttonPanel.add(medium);
        buttonPanel.add(hard);
        buttonPanel.setOpaque(false);

        difficultyPanel.add(buttonPanel, gbc);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(10, 10, 10, 10);
        difficultyPanel.add(back, gbc);

        ez.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Easy selected");
                Board board1 = new Board(3, 3);
                GUI gui1 = new GUI(board1, false);
                gui1.setExtendedState(JFrame.MAXIMIZED_BOTH);
                gui1.setVisible(true);
                gui1.update();
                dispose();
            }
        });

        medium.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Medium selected");
                Board board2 = new Board(10, 10);
                GUI gui2 = new GUI(board2, false);
                gui2.setExtendedState(JFrame.MAXIMIZED_BOTH);
                gui2.setVisible(true);
                gui2.update();
                dispose();
            }
        });

        hard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Hard selected");
                Board board3 = new Board(20, 20);
                GUI gui3 = new GUI(board3, false);
                gui3.setExtendedState(JFrame.MAXIMIZED_BOTH);
                gui3.setVisible(true);
                gui3.update();
                dispose();
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Menu menu = new Menu();
                menu.setVisible(true);
            }
        });
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
