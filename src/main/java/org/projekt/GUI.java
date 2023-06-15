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
import java.util.Arrays;
import java.util.List;

public class GUI extends JFrame {

    private Board board;
    private JPanel[][] cellPanels;
    JButton button;
    JTextField[] textFields;
    JButton backButton; // Nowy przycisk cofania do menu
    JButton cofaniebutton;

    public GUI(Board board) {
        this.board = board;
        setTitle("Game Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    BufferedImage backgroundImage = ImageIO.read(new File("kauro2.png")); // Ścieżka do pliku tła
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        setContentPane(mainPanel);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        int boardWidth = board.getWidth();
        int boardHeight = board.getHeight();

        JPanel boardContainer = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(boardContainer), BorderLayout.CENTER);

        JPanel boardPanel = new JPanel(new GridLayout(boardHeight, boardWidth));
        boardContainer.add(boardPanel, BorderLayout.CENTER);

        cellPanels = new JPanel[boardHeight][boardWidth];
        for (int row = 0; row < boardHeight; row++) {
            for (int col = 0; col < boardWidth; col++) {
                JPanel cellPanel = new JPanel();
                cellPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cellPanels[row][col] = cellPanel;
                boardPanel.add(cellPanel);
            }
        }
        board.innitBoardWithBlanks();
        board.placeSumming();
        board.placeValue();
        board.checkRow();
        board.checkColumn();
        board.fillSumming();
        board.set(board.getWidth() - 1, board.getHeight() - 1, new BlankCell());
        board.chooseValue(0, 0, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        board.checkBoard();

        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(100, board.getHeight() * 20));
        emptyPanel.setBackground(Color.WHITE);
        boardContainer.add(emptyPanel, BorderLayout.EAST);

        JButton zatwierdz = new JButton();
        zatwierdz.setBounds(1, 100, 90, 50);
        zatwierdz.setVisible(true);
        zatwierdz.setText("Zatwierdz");
        emptyPanel.add(zatwierdz);

        JButton zapis_png = new JButton();
        zapis_png.setBounds(1, 100, 90, 50);
        zapis_png.setVisible(true);
        zapis_png.setText("Zapisz");
        zapis_png.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveBoardAsPNG("board.png");
            }
        });
        emptyPanel.add(zapis_png);

        textFields = new JTextField[boardWidth * boardHeight];
        for (int i = 0; i < textFields.length; i++) {
            textFields[i] = new JTextField(5);
            textFields[i].setHorizontalAlignment(JTextField.CENTER);
            textFields[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JTextField textField = (JTextField) e.getComponent();
                    textField.requestFocusInWindow(); // Przeniesienie fokusu na kliknięte pole tekstowe
                }
            });
        }

        updateTextFields();

        // Dodanie przycisku cofania
        backButton = new JButton("Cofnij do menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Zamknięcie obecnego okna (Game Board)
                Menu menu = new Menu(); // Tworzenie nowego obiektu Menu
                menu.setVisible(true); // Wyświetlanie Menu
            }
        });
        cofaniebutton = new JButton("Cofnij ostatni ruch");
        cofaniebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==cofaniebutton){
                    board.undoLastMove();
                }
            }


        });

        JButton zapis_gry = new JButton();
        zapis_gry.setBounds(1, 100, 90, 50);
        zapis_gry.setVisible(true);
        zapis_gry.setText("Zapisz grę");
        zapis_gry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SaveGame saveGame = new SaveGame();
                saveGame.saveGame(board);
            }
        });
        emptyPanel.add(zapis_gry);

        mainPanel.add(backButton, BorderLayout.NORTH);
        emptyPanel.add(backButton);
        emptyPanel.add(cofaniebutton);

        pack();
    }


    public void update() {
        int boardWidth = board.getWidth();
        int boardHeight = board.getHeight();
        for (int row = 0; row < boardHeight; row++) {
            for (int col = 0; col < boardWidth; col++) {
                Cell cell = board.get(col, row);
                JPanel cellPanel = cellPanels[row][col];
                cellPanel.removeAll();
                if (cell instanceof BlankCell) {
                    cellPanel.setBackground(Color.BLACK);
                } else if (cell instanceof ValueCell) {
                    int index = row * boardWidth + col;
                    JTextField textField = textFields[index];
                    cellPanel.add(textField);
                } else if (cell instanceof SummingCell) {
                    JLabel label = new JLabel(((SummingCell) cell).getDownTargetValue() + "/" + ((SummingCell) cell).getRightTargetValue(), JLabel.CENTER);
                    cellPanel.add(label);
                }
            }
        }
        revalidate();
        repaint();
    }

    private void updateTextFields() {
        int boardWidth = board.getWidth();
        int boardHeight = board.getHeight();
        for (int row = 0; row < boardWidth; row++) {
            for (int col = 0; col < boardHeight; col++) {
                Cell cell = board.get(row, col);
                if (cell instanceof ValueCell) {
                    //int index = row * boardWidth + col;
                    //x + y * width
                    int index = row + col * boardWidth;
                    JTextField textField = textFields[index];
                    textField.setText(Integer.toString(((ValueCell) cell).getTargetValue()));
                }
            }
        }
    }

    private void updateBoardValues() {
        int boardWidth = board.getWidth();
        int boardHeight = board.getHeight();
        for (int row = 0; row < boardHeight; row++) {
            for (int col = 0; col < boardWidth; col++) {
                Cell cell = board.get(col, row);
                if (cell instanceof ValueCell) {
                    int index = row * boardWidth + col;
                    JTextField textField = textFields[index];
                    ((ValueCell) cell).setValue(Integer.parseInt(textField.getText()));
                }
            }
        }
    }

    public void saveBoardAsPNG(String filePath) {
        int boardWidth = board.getWidth();
        int boardHeight = board.getHeight();
        int cellSize = 20; // Rozmiar pojedynczej komórki w pikselach

        int imageWidth = boardWidth * cellSize;
        int imageHeight = boardHeight * cellSize;

        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        for (int row = 0; row < boardHeight; row++) {
            for (int col = 0; col < boardWidth; col++) {
                Cell cell = board.get(col, row);
                Color color = cell instanceof BlankCell ? Color.YELLOW : Color.WHITE;

                int startX = col * cellSize;
                int startY = row * cellSize;

                graphics.setColor(color);
                graphics.fillRect(startX, startY, cellSize, cellSize);

                if (cell instanceof ValueCell) {
                    int value = ((ValueCell) cell).getValue();
                    String valueString = Integer.toString(value);
                    FontMetrics fm = graphics.getFontMetrics();
                    int textWidth = fm.stringWidth(valueString);
                    int textHeight = fm.getHeight();
                    int textX = startX + (cellSize - textWidth) / 2;
                    int textY = startY + (cellSize - textHeight) / 2 + fm.getAscent();
                    graphics.setColor(Color.BLACK);
                    graphics.drawString(valueString, textX, textY);
                } else if (cell instanceof SummingCell) {
                    int downTargetValue = ((SummingCell) cell).getDownTargetValue();
                    int rightTargetValue = ((SummingCell) cell).getRightTargetValue();
                    String sumString = downTargetValue + "/" + rightTargetValue;
                    FontMetrics fm = graphics.getFontMetrics();
                    int textWidth = fm.stringWidth(sumString);
                    int textHeight = fm.getHeight();
                    int textX = startX + (cellSize - textWidth) / 2;
                    int textY = startY + (cellSize - textHeight) / 2 + fm.getAscent();
                    graphics.setColor(Color.BLUE);
                    graphics.drawString(sumString, textX, textY);
                }
            }
        }

        graphics.dispose();

        File file = new File(filePath);
        try {
            ImageIO.write(image, "png", file);
            System.out.println("Zapisano planszę jako plik PNG.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Board board = new Board(15, 15);
        GUI gui = new GUI(board);

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        board.placeSumming();
        board.placeValue();
        board.checkRow();
        board.checkColumn();
        board.fillSumming();
        board.checkBoard();
        board.chooseValue(0, 0, list);
        board.set(board.getWidth() - 1, board.getHeight()- 1, new BlankCell());
        board.setSummingValues();
        gui.setVisible(true);
        gui.updateTextFields();
        gui.update();
    }
}
