# Chess Game


## Summary

The project consists of a chess game graphic user interface implemented with Java. The GUI contains the chessboard with all the pieces and allows two players to play a full game of chess. It also accounts for the rules of chess such as validating moves and checking the game state for check and checkmate.

## Project Details

  game/

    Game.java: The main class that sets up the GUI and handles user interactions.

  board/

    Board.java: Manages the state of the chessboard and all movements of the pieces.

  piece/

    Piece.java: Abstract base class for all chess pieces.
    King.java: Manages the king piece
    Queen.java: Manages the queen piece
    Rook.java: Manages the rook piece
    Bishop.java: Manages the bishop piece
    Knight.java: Manages the knight piece
    Pawn.java: Manages the pawn piece

## Setup Instructions

### Prerequisites
Requires Java Development Kit (jdk) installed

### Clone Repository

```git clone https://git.txstate.edu/aso59/Chess-Project/tree/main```

After cloning, change directory to project folder

```cd GitHub/Chess-Project/Project-2/Chess\ Board/src```

### Compile and Run

Compile

```javac game/*.java board/*.java piece/*.java```

Run

```java game.Game```


### How the Game Works

- Game automatically starts on white's turn
- Select any valid piece and all possible moves will be shown via dots
- Select any valid move and the game state will be updated and player turn will swap
- A transcription of all moves are displayed on the left side
- You will be notified at any point if a king is in check or checkmate
- If in checkmate, it will announce a winner and the game will end



