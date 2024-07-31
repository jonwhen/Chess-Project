package piece;

// base class for all pieces
public  abstract class Piece {
	
		private boolean isWhite;
		
		public Piece(boolean isWhite) {
			this.isWhite = isWhite;
			
		}
		// returns the color of the piece to indicate turns
		public boolean isWhite() {

		        return isWhite;

		}
		// overridden by image of piece
		public abstract String toString();
		
		// overridden by the rules to move for each piece 
		public abstract boolean validMove(Piece [][] board, int startRow, int startCol, int endRow, int endCol);
}
