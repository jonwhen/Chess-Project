package game;


import piece.Piece;

import javax.swing.*;
import Board.Board;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import java.awt.image.BufferedImage;

public class Game extends JFrame {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
    	// initializes the board at the start of the game 
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
    	// updates the board after a piece is moved
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
    	// receives possible moves and sets icon for each 
        Piece piece = board.getPieceAt(row, col);
        if (piece != null) {
            possibleMoves.clear();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
        
                    squares[i][j].setIcon(null);
                }
            }
            
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
               
                squares[r][c].setIcon(createDotIcon());
            }
        }
    }

    private Icon createDotIcon() {
    	// creates the dot icon to represent possible locations to move
        int size = 15;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.BLACK);
        g.fillOval(0, 0, size, size);
        g.dispose();
        return new ImageIcon(image);
    }

    private void clearHighlights() {
    	// clears highlight of pieces and possible places after moving
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col].setBackground(originalColors[row][col]);
                squares[row][col].setIcon(null);
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
        	// allows the piece selection and to move
            if (selectedPiece != null) {
                squares[startRow][startCol].setBackground(originalColors[startRow][startCol]);
                clearHighlights();
            }

            Piece piece = board.getPieceAt(row, col);
            if (piece != null && piece.isWhite() == board.isWhiteTurn) {
            	// select the piece if its the right turn
                selectedPiece = piece;
                startRow = row;
                startCol = col;
                squares[startRow][startCol].setBackground(Color.GREEN);
                highlightPossibleMoves(startRow, startCol);
            } else {
                if (selectedPiece != null && possibleMoves.contains(new Point(row, col))) {
                    boolean validMove = board.makeMove(startRow, startCol, row, col);
                    if (validMove) {
                        updateBoard();
                        checkGameState();
                        transcribeMove(startRow, startCol, row, col);
                    } else {
                    	// shows if move is invalid 
        
                        JOptionPane.showMessageDialog(Game.this, "Invalid move for " + selectedPiece.toString().trim() + ", try again.");
                    }
                }
                clearHighlights();
                selectedPiece = null;
            }
        }

    }

    private void transcribeMove(int startRow, int startCol, int endRow, int endCol) {
    	// adds a transcription of moves made to the side
        char startColChar = (char) ('a' + startCol);
        char endColChar = (char) ('a' + endCol);
        String moveString = String.format("%s: %c%d to %c%d\n",
            selectedPiece.toString().trim(), startColChar, 8 - startRow, endColChar, 8 - endRow);
        transcript.append(moveString); 
    }

    private void checkGameState() {
    	// checks the state of the game, notifies if in check and ends game if checkmate
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
