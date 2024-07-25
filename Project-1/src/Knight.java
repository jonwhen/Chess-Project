public class Knight extends Piece {

    public Knight(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean validMove(Piece[][] board, int startRow, int startCol, int endRow, int endCol) {
        // implement the logic for a knight's valid moves
        // this will depend on the rules of chess and the current state of the board
        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);

        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
            // check if the destination is empty or occupied by an opponent's piece
            if (board[endRow][endCol] == null || board[endRow][endCol].isWhite()!= isWhite()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return isWhite()? "wn " : "bn ";
    }
}