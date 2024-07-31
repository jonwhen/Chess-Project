package piece;

public class King extends Piece {

    private boolean hasMoved;

    public King(boolean isWhite) {
        super(isWhite);
        this.hasMoved = false;
    }

    @Override
    public boolean validMove(Piece[][] board, int startRow, int startCol, int endRow, int endCol) {
        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);

        // kings can move one square in any direction
        if ((rowDiff == 0 && colDiff == 1) || (rowDiff == 1 && colDiff == 0) || (rowDiff == 1 && colDiff == 1)) {
            if (board[endRow][endCol] == null || board[endRow][endCol].isWhite() != isWhite()) {
                return true;
            }
        }

        // castling
        if (!hasMoved && rowDiff == 0 && colDiff == 2) {
            if (endCol == startCol + 2) { 
                return canCastle(board, startRow, startCol, startRow, startCol + 3);
            }
            if (endCol == startCol - 2) { 
                return canCastle(board, startRow, startCol, startRow, startCol - 4);
            }
        }

        return false;
    }

    private boolean canCastle(Piece[][] board, int kingRow, int kingCol, int rookRow, int rookCol) {
        // check if the rook is present and has not moved
        Piece rook = board[rookRow][rookCol];
        if (rook == null || !(rook instanceof Rook) || ((Rook) rook).hasMoved()) {
            return false;
        }

        // checks that the squares between rook and king are empty
        int step = (rookCol > kingCol) ? 1 : -1;
        for (int col = kingCol + step; col != rookCol; col += step) {
            if (board[kingRow][col] != null) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return isWhite() ? "\u2654" : "\u265A";
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean hasMoved() {
        return hasMoved;
    }
}
