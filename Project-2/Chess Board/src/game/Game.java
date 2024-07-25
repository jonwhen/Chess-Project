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
    private Board board = new Board(); // integrate Board from console game
    private String selectedPiece = null;
    private int startRow, startCol;

    public Game() {
        setTitle("Chess Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeBoard(); // initialize board with buttons
        add(boardPanel);
        setVisible(true);
    }

    private void initializeBoard() {
    	// create a chess board with buttons 
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col] = new JButton();
                squares[row][col].setFont(new Font("Arial", Font.PLAIN, 40));
                squares[row][col].setHorizontalAlignment(SwingConstants.CENTER);
                squares[row][col].setOpaque(true);
                squares[row][col].setBorderPainted(false);

                // set colors for the squares: grey and white
                if ((row + col) % 2 == 0) {
                    squares[row][col].setBackground(Color.LIGHT_GRAY); // light grey
                } else {
                    squares[row][col].setBackground(Color.DARK_GRAY); // dark grey
                }

                squares[row][col].addMouseListener(new MoveListener(row, col)); // create mouse listener to allow moves
                boardPanel.add(squares[row][col]); // add buttons to chess board
            }
        }
        updateBoard(); 
    }

    private void updateBoard() {
    	// update the piece positions to allow the buttons to be pressed
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPieceAt(row, col); // added getpiece function to get current position
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
        	// chooses the piece on selected square
            Piece piece = board.getPieceAt(row, col); 
            if (piece != null && piece.isWhite() == board.isWhiteTurn) {
                selectedPiece = piece.toString().trim();
                startRow = row;
                startCol = col;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        	// allows the piece to be moved via drag and drop
            if (selectedPiece != null) {
                boolean validMove = board.makeMove(startRow, startCol, row, col); // Update to makeMove(row, col)
                if (validMove) {
                    updateBoard(); // update board after each move
                    checkGameState(); // checks for checkmate
                } else {
                    JOptionPane.showMessageDialog(Game.this, "Invalid move for " + selectedPiece + ", try again.");
                }
                selectedPiece = null; // resets after each move
            }
        }
    }

    private void checkGameState() {
    	// checks the state of the game and whether it is check mate
        if (board.isCheckmate(board.isWhiteTurn ? "white" : "black")) {
            JOptionPane.showMessageDialog(this, "Checkmate, " + (board.isWhiteTurn ? "Black" : "White") + " wins!");
            System.exit(0); // end game
        } else if (board.isInCheck(board.isWhiteTurn ? "white" : "black")) {
            JOptionPane.showMessageDialog(this, (board.isWhiteTurn ? "White" : "Black") + " is in check!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new); // starts the game
    }
}
