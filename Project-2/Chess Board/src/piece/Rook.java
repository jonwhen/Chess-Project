package piece;

public class Rook extends Piece {

    private boolean hasMoved;

    public Rook(boolean isWhite) {
        super(isWhite);
        this.hasMoved = false;
    }

    @Override
    public boolean validMove(Piece[][] board, int startRow, int startCol, int endRow, int endCol) {
        // initializes the possible moves for rook
        if (startRow == endRow) {
            // move horizontally
            int minCol = Math.min(startCol, endCol);
            int maxCol = Math.max(startCol, endCol);

            for (int i = minCol + 1; i < maxCol; i++) {
                if (board[startRow][i] != null) {
                    return false;
                }
            }

            return board[endRow][endCol] == null || board[endRow][endCol].isWhite() != isWhite();
        } else if (startCol == endCol) {
            // move vertically
            int minRow = Math.min(startRow, endRow);
            int maxRow = Math.max(startRow, endRow);

            for (int i = minRow + 1; i < maxRow; i++) {
                if (board[i][startCol] != null) {
                    return false;
                }
            }

            return board[endRow][endCol] == null || board[endRow][endCol].isWhite() != isWhite();
        }

        return false;
    }

    @Override
    public String toString() {
        return isWhite() ? "\u2656" : "\u265c";
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean hasMoved() {
        return hasMoved;
    }
}
