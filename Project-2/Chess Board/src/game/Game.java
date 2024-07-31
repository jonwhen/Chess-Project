package game;
import piece.Bishop;
import piece.King;
import piece.Knight;
import piece.Piece;
import piece.Queen;
import piece.Pawn;
import javax.swing.*;

import Board.Board;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game extends JFrame {
    private JButton[][] squares = new JButton[8][8];
    private JPanel boardPanel = new JPanel(new GridLayout(8, 8));
    private Board board = new Board(); // Your console game Board class
    private Piece selectedPiece = null;
    private int startRow, startCol;
    private Color[][] originalColors = new Color[8][8]; // To store original colors of the squares

    public Game() {
        setTitle("Chess Board");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeBoard();
        add(boardPanel);
        setVisible(true);
    }

    private void initializeBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col] = new JButton();
                squares[row][col].setFont(new Font("Arial", Font.PLAIN, 40));
                squares[row][col].setHorizontalAlignment(SwingConstants.CENTER);
                squares[row][col].setOpaque(true);
                squares[row][col].setBorderPainted(false);

                // Set colors for the squares: light wood and dark wood
                Color color = (row + col) % 2 == 0 ? new Color(240, 217, 181) : new Color(181, 136, 99);
                squares[row][col].setBackground(color);
                originalColors[row][col] = color; // Store the original color

                squares[row][col].addMouseListener(new MoveListener(row, col));
                boardPanel.add(squares[row][col]);
            }
        }
        updateBoard();
    }

    private void updateBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPieceAt(row, col);
                if (piece != null) {
                    squares[row][col].setText(piece.toString().trim());
                } else {
                    squares[row][col].setText("");
                }
            }
        }
    }

    private class MoveListener extends MouseAdapter {
        private int row, col;

        public MoveListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (selectedPiece != null) {
                // Reset the background color of the previously selected square
                squares[startRow][startCol].setBackground(originalColors[startRow][startCol]);
            }

            Piece piece = board.getPieceAt(row, col);
            if (piece != null && piece.isWhite() == board.isWhiteTurn) {
                selectedPiece = piece;
                startRow = row;
                startCol = col;

                // Highlight the selected piece's square
                squares[startRow][startCol].setBackground(Color.GREEN);
            } else {
                if (selectedPiece != null) {
                    // Try to make the move
                    boolean validMove = board.makeMove(startRow, startCol, row, col);
                    if (validMove) {
                        updateBoard();
                        checkGameState();
                    } else {
                        JOptionPane.showMessageDialog(Game.this, "Invalid move for " + selectedPiece.toString().trim() + ", try again.");
                    }

                    // Reset the background color of the previously selected square
                    squares[startRow][startCol].setBackground(originalColors[startRow][startCol]);
                    selectedPiece = null;
                }
            }
        }
    }

    private void checkGameState() {
        if (board.isCheckmate(board.isWhiteTurn ? "white" : "black")) {
            JOptionPane.showMessageDialog(this, "Checkmate, " + (board.isWhiteTurn ? "Black" : "White") + " wins!");
            System.exit(0);
        } else if (board.isInCheck(board.isWhiteTurn ? "white" : "black")) {
            JOptionPane.showMessageDialog(this, (board.isWhiteTurn ? "White" : "Black") + " is in check!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new);
    }
}

