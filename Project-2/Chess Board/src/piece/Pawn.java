package piece;
public class Pawn extends Piece {
    private boolean hasMoved;

    public Pawn(boolean isWhite) {
        super(isWhite);
        this.hasMoved = false; // set all pawns to has not moved
    }

    @Override
    public String toString() {
        return isWhite() ? "\u2659" : "\u265F";
    }

    @Override
    public boolean validMove(Piece[][] board, int startRow, int startCol, int endRow, int endCol) {
        int direction = isWhite() ? -1 : 1; // sets direction for each pawn

        // checks to see if the move is forward one
        if (startCol == endCol && endRow == startRow + direction && board[endRow][endCol] == null) {
            return true;
        }

        // checks if its the first move, if its two squares forward
        if (!hasMoved && startCol == endCol && endRow == startRow + 2 * direction &&
            board[endRow][endCol] == null && (startRow == 1 || startRow == 6)) {
            return true;
        }

        // checks if the move is a diagonal capture
        if (Math.abs(startCol - endCol) == 1 && endRow == startRow + direction && board[endRow][endCol] != null &&
            board[endRow][endCol].isWhite() != isWhite()) {
            return true;
        }

        return false;
    }

    // updates a pawn once it has moved to disable two square moves
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
