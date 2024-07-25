package piece;
public class Queen extends Piece {

    public Queen(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean validMove(Piece[][] board, int startRow, int startCol, int endRow, int endCol) {
        // implement the logic for a queen's valid moves
        // this will depend on the rules of chess and the current state of the board
        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);

        // check for horizontal movement
        if (rowDiff == 0) {
            int minCol = Math.min(startCol, endCol);
            int maxCol = Math.max(startCol, endCol);
            for (int i = minCol + 1; i < maxCol; i++) {
                if (board[startRow][i] != null) {
                    return false;
                }
            }
            return board[endRow][endCol] == null || board[endRow][endCol].isWhite() != isWhite();
        }
        // check for vertical movement
        else if (colDiff == 0) {
            int minRow = Math.min(startRow, endRow);
            int maxRow = Math.max(startRow, endRow);
            for (int i = minRow + 1; i < maxRow; i++) {
                if (board[i][startCol] != null) {
                    return false;
                }
            }
            return board[endRow][endCol] == null || board[endRow][endCol].isWhite() != isWhite();
        }
        // check for diagonal movement
        else if (rowDiff == colDiff) {
            int rowInc = (endRow - startRow) / rowDiff;
            int colInc = (endCol - startCol) / colDiff;
            for (int i = 1; i < rowDiff; i++) {
                if (board[startRow + i * rowInc][startCol + i * colInc] != null) {
                    return false;
                }
            }
            return board[endRow][endCol] == null || board[endRow][endCol].isWhite() != isWhite();
        }

        return false;
    }


    @Override
    public String toString() {
        return isWhite()? "\u2655" : "\u265B";
    }
}