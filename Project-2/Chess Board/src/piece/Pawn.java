package piece;
public class Pawn extends Piece {
    private boolean hasMoved;

    public Pawn(boolean isWhite) {
        super(isWhite);
        this.hasMoved = false; // Initially, the pawn hasn't moved
    }

    @Override
    public String toString() {
        return isWhite() ? "\u2659" : "\u265F";
    }

    @Override
    public boolean validMove(Piece[][] board, int startRow, int startCol, int endRow, int endCol) {
        int direction = isWhite() ? -1 : 1; // White pawns move up, black pawns move down

        // Check if the move is one square forward
        if (startCol == endCol && endRow == startRow + direction && board[endRow][endCol] == null) {
            return true;
        }

        // Check if the move is two squares forward from the starting position
        if (!hasMoved && startCol == endCol && endRow == startRow + 2 * direction &&
            board[endRow][endCol] == null && (startRow == 1 || startRow == 6)) {
            return true;
        }

        // Check if the move is a capture move
        if (Math.abs(startCol - endCol) == 1 && endRow == startRow + direction && board[endRow][endCol] != null &&
            board[endRow][endCol].isWhite() != isWhite()) {
            return true;
        }

        return false;
    }

    // Call this method when a pawn moves to update its status
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
