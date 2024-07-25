import java.util.Scanner;

public class Board {

    private Piece[][] pieces;
    public boolean isWhiteTurn;

    public Board() {
        this.pieces = new Piece[8][8];
        this.isWhiteTurn = true;
        initializePieces();
    }
    
 
    private void initializePieces() {
        // initialize the pieces on the board
        for (int i = 0; i < 8; i++) {
            pieces[1][i] = new Pawn(false);
            pieces[6][i] = new Pawn(true);
        }
        // set the pieces on the board to their respective spot
        pieces[0][0] = pieces[0][7] = new Rook(false);
        pieces[0][1] = pieces[0][6] = new Knight(false);
        pieces[0][2] = pieces[0][5] = new Bishop(false);
        pieces[0][3] = new Queen(false);
        pieces[0][4] = new King(false);

        pieces[7][0] = pieces[7][7] = new Rook(true);
        pieces[7][1] = pieces[7][6] = new Knight(true);
        pieces[7][2] = pieces[7][5] = new Bishop(true);
        pieces[7][3] = new Queen(true);
        pieces[7][4] = new King(true);
    }

    public void displayBoard() {
    	// display the board and its labels, adding two spaces between each spot for readability
        String letters = "   a   b   c   d   e   f   g   h   ";
        System.out.println(letters);
        for (int i = 0; i < 8; i++) {
            System.out.print((8 - i) + "  ");
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j] == null) {
                    if ((i + j) % 2 == 0) {
                        System.out.print("##  ");
                    } else {
                        System.out.print("    ");
                    }
                } else {
                    System.out.print(pieces[i][j].toString() + " ");
                }
            }
            System.out.println(" " + (8 - i));
        }
        System.out.println(letters);
    }
    public void makeMove(String start, String end) {
        if (start.length() != 2 || end.length() != 2) {
            System.out.println("Invalid move format, try again.");
            return;
        }

        // checks input for start and end position 
        char startFile = start.charAt(0);
        char startRank = start.charAt(1);
        char endFile = end.charAt(0);
        char endRank = end.charAt(1);

        // if input is outside range of the board, invalid move
        if (startFile < 'a' || startFile > 'h' || startRank < '1' || startRank > '8' ||
            endFile < 'a' || endFile > 'h' || endRank < '1' || endRank > '8') {
            System.out.println("Invalid move, coordinates out of bounds.");
            return;
        }

        int startCol = startFile - 'a';
        int startRow = '8' - startRank;
        int endCol = endFile - 'a';
        int endRow = '8' - endRank;

        // display coordinates for each move for clarity
        System.out.println("Move: start (" + startRow + ", " + startCol + ") -> end (" + endRow + ", " + endCol + ")");

        Piece piece = pieces[startRow][startCol];
        if (piece != null && piece.isWhite() == isWhiteTurn) {
            if (piece.validMove(pieces, startRow, startCol, endRow, endCol)) {
                pieces[endRow][endCol] = piece;
                pieces[startRow][startCol] = null;

                // check if the move puts the opponent's king in check
                boolean isInCheck = isInCheck(piece.isWhite() ? "white" : "black");
                if (isInCheck) {
                    // check if it's checkmate
                    boolean isCheckmate = isCheckmate(piece.isWhite() ? "white" : "black");
                    if (isCheckmate) {
                        System.out.println("Checkmate, " + (piece.isWhite() ? "white" : "black") + " wins!");
                        return;
                    } else {
                        System.out.println("Check, " + (piece.isWhite() ? "white" : "black") + " is in check.");
                    }
                }

                isWhiteTurn = !isWhiteTurn;
             // if move does not correlate to the piece, give invalid move and try again
            } else {
                System.out.println("Invalid move for " + piece.getClass().getSimpleName() + ", try again.");
            }
           // if move is not on the correct turn, return invalid move
        } else {
            System.out.println("Invalid move, try again.");
        }
    }

    public boolean isCheckmate(String color) {
        // check if the player with 'color' is in checkmate
        int[] kingPos = getKingPos(color.equals("white"));
        int row = kingPos[0];
        int col = kingPos[1];

        // check all possible moves of opponent's pieces to see if the king can move out of check
        for (int x = 0; x < pieces.length; x++) {
            for (int y = 0; y < pieces[x].length; y++) {
                if (pieces[x][y] != null && pieces[x][y].isWhite() != color.equals("white")) {
                    Piece opponentPiece = pieces[x][y];
                    for (int i = 0; i < pieces.length; i++) {
                        for (int j = 0; j < pieces[i].length; j++) {
                            if (opponentPiece.validMove(pieces, x, y, i, j)) {
                                // simulate the move temporarily
                                Piece capturedPiece = pieces[i][j];
                                pieces[i][j] = opponentPiece;
                                pieces[x][y] = null;

                                // check if the king is still in check after the move
                                boolean stillInCheck = isInCheck(color);

                                // undo the temporary move
                                pieces[x][y] = opponentPiece;
                                pieces[i][j] = capturedPiece;

                                // if the king can move out of check, it's not checkmate
                                if (!stillInCheck) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }

        // if no move can remove the king from check, it's checkmate
        return true;
    }


    public boolean isInCheck(String color) {
    	// gets the king position and determines if is in check 
        int[] kingPos = getKingPos(color.equals("white"));
        int row = kingPos[0];
        int col = kingPos[1];

        for (int x = 0; x < pieces.length; x++) {
            for (int y = 0; y < pieces[0].length; y++) {
                if (pieces[x][y] != null && pieces[x][y].isWhite() != color.equals("white")) {
                    if (pieces[x][y].validMove(pieces, x, y, row, col)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
    // locates the king for each side
    private int[] getKingPos(boolean isWhite) {
        int[] pos = new int[2];
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (pieces[i][j] instanceof King && pieces[i][j].isWhite() == isWhite) {
                    pos[0] = i;
                    pos[1] = j;
                    return pos;
                }
            }
        }
        return pos;
    }
    }

