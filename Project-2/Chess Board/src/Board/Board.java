package Board;

import piece.Bishop;
import piece.King;
import piece.Knight;
import piece.Pawn;
import piece.Piece;
import piece.Queen;
import piece.Rook;

public class Board {
    public Piece[][] pieces;
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
        // set the pieces on the board to their respective spots
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

    public boolean makeMove(int startRow, int startCol, int endRow, int endCol) {
        // will make a move as long as it is valid and also checks for castling and checkmate
        Piece piece = pieces[startRow][startCol];
        // checks for the correct turn
        if (piece != null && piece.isWhite() == isWhiteTurn) {
            // makes sure move is valid
            if (piece.validMove(pieces, startRow, startCol, endRow, endCol)) {
                if (piece instanceof King && Math.abs(startCol - endCol) == 2) {
                    // castling
                    int rookCol = (endCol > startCol) ? 7 : 0;
                    int rookEndCol = (endCol > startCol) ? 5 : 3;
                    Piece rook = pieces[startRow][rookCol];
                    // performs castling and updates rook hasMoved state
                    if (rook != null && rook instanceof Rook && !((Rook) rook).hasMoved()) {
                        pieces[endRow][endCol] = piece;
                        pieces[startRow][startCol] = null;
                        pieces[startRow][rookEndCol] = rook;
                        pieces[startRow][rookCol] = null;
                        ((Rook) rook).setHasMoved(true);
                        // checks if the move will take king out of check, if not invalid
                        if (isInCheck(piece.isWhite() ? "white" : "black")) {
                            pieces[startRow][startCol] = piece;
                            pieces[endRow][endCol] = null;
                            pieces[startRow][rookCol] = rook;
                            pieces[startRow][rookEndCol] = null;
                            System.out.println("Invalid move: King is still in check.");
                            return false;
                        }
                        isWhiteTurn = !isWhiteTurn;
                        return true;
                    }
                } else {
                    Piece targetPiece = pieces[endRow][endCol];
                    pieces[endRow][endCol] = piece;
                    pieces[startRow][startCol] = null;
                    if (piece instanceof Pawn) {
                        ((Pawn) piece).setHasMoved(true);
                    }
                    if (targetPiece instanceof King) {
                        String winner = piece.isWhite() ? "White" : "Black";
                        System.out.println("Checkmate! " + winner + " wins!");
                        return true;
                    }
                    if (isInCheck(piece.isWhite() ? "white" : "black")) {
                        pieces[startRow][startCol] = piece;
                        pieces[endRow][endCol] = targetPiece;
                        System.out.println("Invalid move: King is still in check.");
                        return false;
                    } else {
                        isWhiteTurn = !isWhiteTurn;
                        return true;
                    }
                }
                return true;
            } else {
                System.out.println("Invalid move for " + piece.getClass().getSimpleName() + ", try again.");
                return false;
            }
        } else {
            System.out.println("Invalid move, try again.");
            return false;
        }
    }

    // added getPiece function to return user selected piece
    public Piece getPieceAt(int row, int col) {
        if (row < 0 || row >= 8 || col < 0 || col >= 8) {
            throw new IllegalArgumentException("Coordinates out of bounds");
        }
        return pieces[row][col];
    }

    public boolean isCheckmate(String color) {
        // checks if in check
        if (!isInCheck(color)) {
            return false;
        }
        // checks if there are any possible moves to get out of check
        for (int x = 0; x < pieces.length; x++) {
            for (int y = 0; y < pieces[x].length; y++) {
                if (pieces[x][y] != null && pieces[x][y].isWhite() == color.equals("white")) {
                    Piece piece = pieces[x][y];
                    for (int i = 0; i < pieces.length; i++) {
                        for (int j = 0; j < pieces[i].length; j++) {
                            if (piece.validMove(pieces, x, y, i, j)) {
                                Piece capturedPiece = pieces[i][j];
                                pieces[i][j] = piece;
                                pieces[x][y] = null;
                                // checks if still in check after simulated move
                                boolean stillInCheck = isInCheck(color);
                                pieces[x][y] = piece;
                                pieces[i][j] = capturedPiece;
                                if (!stillInCheck) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean isInCheck(String color) {
        // gets the king position and determines if it is in check
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
