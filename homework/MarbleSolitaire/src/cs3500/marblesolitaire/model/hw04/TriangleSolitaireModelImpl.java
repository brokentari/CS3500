package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.BoardElement;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * An implementation of the interface {@link MarbleSolitaireModel}, which allows customization of
 * the board, such as arm length, or the location of the initial empty space. This board doesn't
 * allow the player to move
 * <ul>
 *   <li>from or to a non-existing board place</li>
 *   <li>an empty board position</li>
 *   <li>a marble to an occupied space</li>
 *   <li>a marble more than two rows above or below</li>
 *   <li>a marble more than two columns when moving from the same row</li>
 *   <li>a marble over an empty space</li>
 * </ul>
 */
public class TriangleSolitaireModelImpl extends AbstractSolitaireModel {

  /**
   * The default constructor for {@link TriangleSolitaireModelImpl}. Default parameters are {@code
   * armThickness = 5}, {@code sRow = 0}, {@code sCol = 0}.
   */
  public TriangleSolitaireModelImpl() {
    this(5, 0, 0);
  }

  /**
   * A constructor for {@link TriangleSolitaireModelImpl} that specifies the board's {@code
   * dimension}s.
   *
   * @param dimension represents the total number of marbles at the base of the triangular board.
   * @throws IllegalArgumentException if {@code dimension} is a negative number
   */
  public TriangleSolitaireModelImpl(int dimension) {
    this(dimension, 0, 0);
  }

  /**
   * A constructor for {@link TriangleSolitaireModelImpl} that specifies the board's empty slot.
   *
   * @param sRow the empty slot's row in respect to the board
   * @param sCol the empty slot's column in respect to the board
   * @throws IllegalArgumentException if the empty's slot position's coordinates are non-existent
   */
  public TriangleSolitaireModelImpl(int sRow, int sCol) {
    this(5, sRow, sCol);
  }

  /**
   * A constructor for {@link TriangleSolitaireModelImpl}, with all possible parameters.
   *
   * @param dimension the number of marbles in the top row (or bottom row, or left or right
   *                  columns)
   * @param sRow      the empty slot's row in the board
   * @param sCol      the empty slot's column in the board
   * @throws IllegalArgumentException if {@code dimension} is not a non-negative number
   * @throws IllegalArgumentException if the empty's slot position's coordinates are non-existent
   */
  public TriangleSolitaireModelImpl(int dimension, int sRow, int sCol) {
    super(new BoardElement[dimension][], dimension, sRow, sCol);
  }

  @Override
  protected void generateBoardElements(BoardElement[][] board, int sRow, int sCol) {
    for (int row = 0; row < board.length; row++) {
      this.board[row] = new BoardElement[row + 1];
      for (int col = 0; col < board[row].length; col++) {
        if (row == sRow && col == sCol) {
          board[row][col] = BoardElement.EMPTY;
        } else {
          board[row][col] = BoardElement.MARBLE;
          numOfMarbles++;
        }
      }
    }
    score = numOfMarbles;
  }

  @Override
  public String getGameState() {
    String gameState = "";

    for (int row = 0; row < board.length; row++) {
      String currRow = board[row][0].toString();
      StringBuilder whiteSpace = new StringBuilder();
      int emptySpace = 0;

      while (emptySpace < armThickness - board[row].length) {
        whiteSpace.append(" ");
        emptySpace++;
      }

      for (int col = 1; col < board[row].length; col++) {
        currRow = String.join(" ", currRow, board[row][col].toString());
      }

      currRow = whiteSpace + currRow;
      if (row == 0) {
        gameState += currRow;
      } else {
        gameState = String.join("\n", gameState, currRow);
      }
    }

    return gameState;
  }

  @Override
  public boolean isGameOver() {
    boolean isGameOver = true;
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board[row].length; col++) {
        if (board[row][col].toString().equals("O")) {
          isGameOver = isGameOver
              && (isItIllegalMove(row, col, row, col + 2) != null
              && isItIllegalMove(row, col, row, col - 2) != null
              && isItIllegalMove(row, col, row - 2, col) != null
              && isItIllegalMove(row, col, row - 2, col - 2) != null
              && isItIllegalMove(row, col, row + 2, col) != null
              && isItIllegalMove(row, col, row + 2, col + 2) != null);
        }
      }
    }

    return isGameOver;
  }


  @Override
  protected boolean doesCoordinateExistInBoard(int row, int col) {
    return !isMoveOutOfBounds(row, col, 0, 0);
  }

  @Override
  protected String boardMoveSet(int fromRow, int fromCol, int toRow, int toCol) {
    if (fromRow == toRow) {
      if (Math.abs(toCol - fromCol) != 2) {
        return "Can only move within two spaces when moving in the same row";
      } else if (!board[fromRow]
          [fromCol + ((toCol - fromCol) / 2)].toString().equals("O")) {
        return "Must jump over marble";
      }
    } else if (Math.abs(toRow - fromRow) == 2) {
      if (!((Math.abs(toCol - fromCol) == 2) || (toCol == fromCol))) {
        return "Can only move within two spaces when moving in different rows";
      } else if (!board[fromRow + ((toRow - fromRow) / 2)]
          [fromCol + ((toCol - fromCol) / 2)].toString().equals("O")) {
        return "Must jump over marble";
      }
    } else if (Math.abs(toRow - fromRow) != 2) {
      return "Can only move two rows up or down.";
    }

    return null;
  }

  @Override
  protected boolean validConstruction(int armThickness, int sRow, int sCol) {
    if (armThickness <= 0) {
      throw new IllegalArgumentException("Invalid board dimensions");
    }
    if (sRow < 0 || sCol < 0 || sCol > sRow || sRow > armThickness) {
      throw new IllegalArgumentException(
          String.format("Invalid empty cell position (%d,%d)", sRow, sCol));
    }

    return true;
  }
}
