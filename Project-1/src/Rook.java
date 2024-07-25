public class Rook extends Piece {

    private boolean hasMoved;

    public Rook(boolean isWhite) {
        super(isWhite);
        this.hasMoved = false;
    }

    @Override
    public boolean validMove(Piece[][] board, int startRow, int startCol, int endRow, int endCol) {
        // Implement the logic for a rook's valid moves
        if (startRow == endRow) {
            // Horizontal move
            int minCol = Math.min(startCol, endCol);
            int maxCol = Math.max(startCol, endCol);

            for (int i = minCol + 1; i < maxCol; i++) {
                if (board[startRow][i] != null) {
                    return false;
                }
            }

            // Check if the destination is empty or occupied by an opponent's piece
            return board[endRow][endCol] == null || board[endRow][endCol].isWhite() != isWhite();
        } else if (startCol == endCol) {
            // Vertical move
            int minRow = Math.min(startRow, endRow);
            int maxRow = Math.max(startRow, endRow);

            for (int i = minRow + 1; i < maxRow; i++) {
                if (board[i][startCol] != null) {
                    return false;
                }
            }

            // Check if the destination is empty or occupied by an opponent's piece
            return board[endRow][endCol] == null || board[endRow][endCol].isWhite() != isWhite();
        }

        return false;
    }

    @Override
    public String toString() {
        return isWhite() ? "wr " : "br ";
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean hasMoved() {
        return hasMoved;
    }
}
