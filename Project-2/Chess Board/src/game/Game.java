package game;

import piece.Bishop;
import piece.King;
import piece.Knight;
import piece.Piece;
import piece.Queen;
import piece.Pawn;
import piece.Rook;
import javax.swing.*;
import Board.Board;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class Game extends JFrame {
    private JButton[][] squares = new JButton[8][8];
    private JPanel boardPanel = new JPanel(new GridLayout(8, 8));
    private Board board = new Board(); 
    private Piece selectedPiece = null;
    private int startRow, startCol;
    private Color[][] originalColors = new Color[8][8]; 
    private JTextArea transcript = new JTextArea(20, 20); 

    private Set<Point> possibleMoves = new HashSet<>();

    public Game() {
        setTitle("Chess Board");
        setSize(800, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeBoard();
        add(boardPanel, BorderLayout.CENTER); 
        add(new JScrollPane(transcript), BorderLayout.EAST);
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

                Color color = (row + col) % 2 == 0 ? new Color(240, 217, 181) : new Color(181, 136, 99);
                squares[row][col].setBackground(color);
                originalColors[row][col] = color; 

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
    private void highlightPossibleMoves(int row, int col) {
        Piece piece = board.getPieceAt(row, col);
        if (piece != null) {
            possibleMoves.clear();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (piece.validMove(board.pieces, row, col, i, j)) {
                        possibleMoves.add(new Point(i, j));
                    }
                }
            }
            for (Point move : possibleMoves) {
                int r = move.x;
                int c = move.y;
                squares[r][c].setBackground(new Color(144, 238, 144)); // Light green
            }
        }
    }
 void clearHighlights() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col].setBackground(originalColors[row][col]);
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
                squares[startRow][startCol].setBackground(originalColors[startRow][startCol]);
                clearHighlights();
            }
            Piece piece = board.getPieceAt(row, col);
            if (piece != null && piece.isWhite() == board.isWhiteTurn) {
                selectedPiece = piece;
                startRow = row;
                startCol = col;
                // highlights selected square
                squares[startRow][startCol].setBackground(Color.GREEN);
                highlightPossibleMoves(startRow, startCol);
            } else {
                if (selectedPiece != null && possibleMoves.contains(new Point(row, col))) {
                    // make move
                    boolean validMove = board.makeMove(startRow, startCol, row, col);
                    if (validMove) {
                        updateBoard();
                        checkGameState();
                        logMove(startRow, startCol, row, col); // add move to transcript
                    } else {
                        JOptionPane.showMessageDialog(Game.this, "Invalid move for " + selectedPiece.toString().trim() + ", try again.");
                    }
                }
                // unhighlight the square
                clearHighlights();
                selectedPiece = null;
            }
        }
    }

    private void logMove(int startRow, int startCol, int endRow, int endCol) {
        char startColChar = (char) ('a' + startCol);
        char endColChar = (char) ('a' + endCol);
        String moveString = String.format("%s: %c%d to %c%d\n",
            selectedPiece.toString().trim(), startColChar, 8 - startRow, endColChar, 8 - endRow);
        transcript.append(moveString); 
    }

    private void checkGameState() {
        String currentPlayerColor = board.isWhiteTurn ? "white" : "black";
        String opponentColor = board.isWhiteTurn ? "black" : "white";

        if (board.isCheckmate(currentPlayerColor)) {
            JOptionPane.showMessageDialog(this, "Checkmate! " + opponentColor + " wins!");
            System.exit(0);
        } else if (board.isInCheck(currentPlayerColor)) {
            JOptionPane.showMessageDialog(this, currentPlayerColor + " is in check!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new);
    }
}
