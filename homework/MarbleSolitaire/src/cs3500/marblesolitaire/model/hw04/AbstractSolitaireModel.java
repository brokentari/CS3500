package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.BoardElement;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;


/**
 * An abstraction for classes that implement the {@link MarbleSolitaireModel} and all functionality
 * that applies through all implementations.
 */
public abstract class AbstractSolitaireModel implements MarbleSolitaireModel {

  protected final int armThickness;
  protected BoardElement[][] board;
  protected int numOfMarbles;
  protected int score;

  protected AbstractSolitaireModel(BoardElement[][] board, int armThickness, int sRow, int sCol) {
    this.armThickness = armThickness;
    numOfMarbles = 0;
    if (validConstruction(armThickness, sRow, sCol)) {
      this.board = board;
      generateBoardElements(board, sRow, sCol);
    }
  }

  protected static boolean isBetween(int value, int min, int max) {
    return ((value > min) && (value < max));
  }

  /**
   * Move a single marble from a given position to another given position. A move is valid only if
   * the from and to positions are valid. Throws an exception when an illegal move is made, which is
   * listed in the documentation.
   *
   * @throws IllegalArgumentException when the player tries moving a marble that is out of bounds or
   *                                  violates the specific board's move set
   */
  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) {
    String possibleIllegalMoveError = isItIllegalMove(fromRow, fromCol, toRow, toCol);
    if (possibleIllegalMoveError != null) {
      throw new IllegalArgumentException(possibleIllegalMoveError);
    }

    board[fromRow][fromCol] = BoardElement.EMPTY;
    board[toRow][toCol] = BoardElement.MARBLE;
    board[fromRow + ((toRow - fromRow) / 2)][fromCol + ((toCol - fromCol)
        / 2)] = BoardElement.EMPTY;
    this.numOfMarbles--;
    this.score--;
  }

  @Override
  public boolean isGameOver() {
    boolean isGameOver = true;
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board.length; col++) {
        if (board[row][col].toString().equals("O")) {
          isGameOver = isGameOver
              && (isItIllegalMove(row, col, row + 2, col) != null
              && isItIllegalMove(row, col, row, col + 2) != null
              && isItIllegalMove(row, col, row - 2, col) != null
              && isItIllegalMove(row, col, row, col - 2) != null);
        }
      }
    }

    return isGameOver;
  }

  @Override
  public String getGameState() {
    String gameState = "";

    for (int row = 0; row < board.length; row++) {
      String currRow = board[row][0].toString();

      for (int col = 1; col < board[row].length; col++) {
        if (((board[row][col - 1].toString().equals("O")
            || board[row][col - 1].toString().equals("_"))
            && board[row][col].toString().equals(" "))) {
          break;
        }
        currRow = String.join(" ", currRow, board[row][col].toString());
      }

      if (row == 0) {
        gameState += currRow;
      } else {
        gameState = String.join("\n", gameState, currRow);
      }
    }

    return gameState;
  }

  @Override
  public int getScore() {
    return this.score;
  }

  // added the following methods to break up the tasks of moving or generating models, allowing
  // to create individual rule sets for implementations: generateBoardElements, validConstruction,
  // boardMoveSet, itItOutOfBounds
  protected void generateBoardElements(BoardElement[][] board, int sRow, int sCol) {
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board[row].length; col++) {
        if (doesCoordinateExistInBoard(row, col)) {
          if (col == sCol && row == sRow) {
            board[row][col] = BoardElement.EMPTY;
          } else {
            board[row][col] = BoardElement.MARBLE;
            numOfMarbles++;
          }
        } else {
          board[row][col] = BoardElement.NULL;
        }
      }
    }
    score = numOfMarbles;
  }

  // since almost every implementation have different conditions for a board position, an abstract
  // method helped clean up the code where the initial condition would only apply to the english
  // version and the other models would still have to override it
  protected abstract boolean doesCoordinateExistInBoard(int row, int col);

  protected boolean validConstruction(int armThickness, int sRow, int sCol) {
    if (armThickness % 2 == 0 || armThickness < 3) {
      throw new IllegalArgumentException("Invalid arm thickness");
    }

    if (!doesCoordinateExistInBoard(sRow, sCol)) {
      throw new IllegalArgumentException(
          String.format("Invalid empty cell position (%d,%d)", sRow, sCol));
    }
    return true;
  }

  protected String isItIllegalMove(int fromRow, int fromCol, int toRow, int toCol) {
    if (isMoveOutOfBounds(fromRow, fromCol, toRow, toCol)) {
      return "Cannot move from or move to a non-existing position";
    } else {
      if (board[toRow][toCol].toString().equals("O")) {
        return "Cannot move to an occupied position";
      }
      return boardMoveSet(fromRow, fromCol, toRow, toCol);
    }
  }

  protected String boardMoveSet(int fromRow, int fromCol, int toRow, int toCol) {
    if ((fromCol != toCol && fromRow != toRow)) {
      return "Can only move horizontally or vertically";
    } else if (!(Math.abs(toCol - fromCol) == 2 || Math.abs(toRow - fromRow) == 2)) {
      return "Cannot move to a space that is not exactly two positions away";
    } else if (!board[fromRow + ((toRow - fromRow) / 2)]
        [fromCol + ((toCol - fromCol) / 2)].toString().equals("O")) {
      return "Must jump over a marble";
    }

    return null;
  }

  protected boolean isMoveOutOfBounds(int fromRow, int fromCol, int toRow, int toCol) {
    try {
      BoardElement fromCoordinate = board[fromRow][fromCol];
      BoardElement toCoordinate = board[toRow][toCol];

      return fromCoordinate.toString().equals(" ") || toCoordinate.toString().equals(" ");

    } catch (ArrayIndexOutOfBoundsException e) {
      return true;
    }
  }
}
