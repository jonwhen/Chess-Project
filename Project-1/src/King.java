public class King extends Piece {

    private boolean hasMoved;

    public King(boolean isWhite) {
        super(isWhite);
        this.hasMoved = false;
    }

    @Override
    public boolean validMove(Piece[][] board, int startRow, int startCol, int endRow, int endCol) {
        // Implement the logic for a king's valid moves
        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);

        // Standard king move (one square in any direction)
        if ((rowDiff == 0 && colDiff == 1) || (rowDiff == 1 && colDiff == 0) || (rowDiff == 1 && colDiff == 1)) {
            // Check if the destination is empty or occupied by an opponent's piece
            if (board[endRow][endCol] == null || board[endRow][endCol].isWhite() != isWhite()) {
                return true;
            }
        }

        // Castling move
        if (!hasMoved && rowDiff == 0 && colDiff == 2) {
            // King-side castling
            if (endCol == startCol + 2) {
                return canCastle(board, startRow, startCol, startRow, startCol + 3);
            }
            // Queen-side castling
            if (endCol == startCol - 2) {
                return canCastle(board, startRow, startCol, startRow, startCol - 4);
            }
        }

        return false;
    }

    private boolean canCastle(Piece[][] board, int kingRow, int kingCol, int rookRow, int rookCol) {
        // Check if the rook has moved
        Piece rook = board[rookRow][rookCol];
        if (rook == null || !(rook instanceof Rook) || ((Rook) rook).hasMoved()) {
            return false;
        }

        // Check the spaces between the king and the rook are empty
        int step = (rookCol > kingCol) ? 1 : -1;
        for (int col = kingCol + step; col != rookCol; col += step) {
            if (board[kingRow][col] != null) {
                return false;
            }
        }

        // Additional checks for king's safety can be implemented here
        // (e.g., ensuring the king does not pass through or land on a square under attack)

        return true;
    }

    @Override
    public String toString() {
        return isWhite() ? "wk " : "bk ";
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean hasMoved() {
        return hasMoved;
    }
}
