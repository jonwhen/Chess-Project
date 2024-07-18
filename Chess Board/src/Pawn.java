class Pawn extends Piece {
    private boolean hasMoved;

    public Pawn(boolean isWhite) {
        super(isWhite);
        this.hasMoved = false;
    }

    @Override
    public boolean validMove(Piece[][] board, int startRow, int startCol, int endRow, int endCol) {
        int rowDiff = endRow - startRow;
        int colDiff = Math.abs(endCol - startCol);

       
        if (isWhite()) {
            // White pawns move up the board (row decreases)
            if (startRow == 6 && endRow == 4 && colDiff == 0 && board[5][startCol] == null && board[4][startCol] == null) {
                hasMoved = true;
                return true;
            } else if (rowDiff == -1 && colDiff == 0 && board[endRow][endCol] == null) {
                hasMoved = true;
                return true;
            } else if (rowDiff == -1 && colDiff == 1 && board[endRow][endCol] != null && !board[endRow][endCol].isWhite()) {
                hasMoved = true;
                return true;
            }
        } else {
            // Black pawns move down the board (row increases)
            if (startRow == 1 && endRow == 3 && colDiff == 0 && board[2][startCol] == null && board[3][startCol] == null) {
                hasMoved = true;
                return true;
            } else if (rowDiff == 1 && colDiff == 0 && board[endRow][endCol] == null) {
                hasMoved = true;
                return true;
            } else if (rowDiff == 1 && colDiff == 1 && board[endRow][endCol] != null && board[endRow][endCol].isWhite()) {
                hasMoved = true;
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return isWhite() ? "wp " : "bp ";
    }
}
