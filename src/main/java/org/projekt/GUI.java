package org.projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

public class GUI extends JFrame {

    private Board board;
    private JPanel[][] cellPanels;
    JButton button;
    JTextField[] textFields;

    public GUI(Board board) {
        this.board = board;
        setTitle("Game Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

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

        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(100, board.getHeight() * 20));
        emptyPanel.setBackground(Color.WHITE);
        boardContainer.add(emptyPanel, BorderLayout.EAST);

        button = new JButton();
        button.setBounds(1, 100, 90, 50);
        button.setVisible(true);
        button.setText("Zatwierdz");
        emptyPanel.add(button);

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
        for (int row = 0; row < boardHeight; row++) {
            for (int col = 0; col < boardWidth; col++) {
                Cell cell = board.get(col, row);
                if (cell instanceof ValueCell) {
                    int index = row * boardWidth + col;
                    JTextField textField = textFields[index];
                    textField.setText(Integer.toString(((ValueCell) cell).getValue()));
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

    public static void main(String[] args) {
        Board board = new Board(15, 15);
        GUI gui = new GUI(board);

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        board.placeSumming();
//        for (int i = 0; i < 150; i++)
//            board.placeSummingField();

        board.placeValue();
        board.checkColumn();
        board.checkRow();
        board.checkBoard();
        gui.setVisible(true);
        gui.update();
    }
}
