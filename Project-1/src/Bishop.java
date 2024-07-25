public class Bishop extends Piece {

    public Bishop(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean validMove(Piece[][] board, int startRow, int startCol, int endRow, int endCol) {
        // implement the logic for a bishop's valid moves
        // this will depend on the rules of chess and the current state of the board
        int rowDiff = Math.abs(endRow - startRow);
        int colDiff = Math.abs(endCol - startCol);

        if (rowDiff == colDiff) {
            // check if the path is clear
            int rowInc = endRow > startRow ? 1 : -1;
            int colInc = endCol > startCol ? 1 : -1;

            for (int i = 1; i < rowDiff; i++) {
                if (board[startRow + i * rowInc][startCol + i * colInc] != null) {
                    return false;
                }
            }

            // check if the destination is empty or occupied by an opponent's piece
            return board[endRow][endCol] == null || board[endRow][endCol].isWhite() != isWhite();
        }

        return false;
    }

    @Override
    public String toString() {
        return isWhite() ? "wb " : "bb ";
    }
}