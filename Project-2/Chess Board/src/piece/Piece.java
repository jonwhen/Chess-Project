package piece;

public  abstract class Piece {
	
		private boolean isWhite;

		public Piece(boolean isWhite) {
			this.isWhite = isWhite;
			
		}
			
		public boolean isWhite() {

		        return isWhite;

		}
		public abstract String toString();
		
		public abstract boolean validMove(Piece [][] board, int startRow, int startCol, int endRow, int endCol);
}
