import java.util.Scanner;

public class Game {

	public static void main(String[] args) {
	    Board board = new Board();
	    Scanner scanner = new Scanner(System.in);

	    while (true) {
	        board.displayBoard();
	        if (board.isWhiteTurn) {
	            System.out.println("White, enter move: ");
	        } else {
	            System.out.println("Black, enter move: ");
	        }
	        String[] inputs = scanner.nextLine().split(" ");
	        if (inputs.length == 2) {
	            board.makeMove(inputs[0], inputs[1]);

	            // Check if the game is in checkmate
	            if (board.isCheckmate(board.isWhiteTurn ? "white" : "black")) {
	                System.out.println("Checkmate, " + (board.isWhiteTurn ? "Black" : "White") + " wins!");
	                break; // End the game loop
	            }
	        } else {
	            System.out.println("Invalid input format, try again.");
	        }
	    }
	    scanner.close();
	}

}
