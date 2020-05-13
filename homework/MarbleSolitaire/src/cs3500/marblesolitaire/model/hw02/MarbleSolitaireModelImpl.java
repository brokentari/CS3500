package cs3500.marblesolitaire.model.hw02;

/**
 * An implementation of the interface {@link MarbleSolitaireModel}.
 */
public class MarbleSolitaireModelImpl implements MarbleSolitaireModel {

  final int armThickness;
  public int numOfMarbles;
  public int score;
  char[][] board;


  /**
   * The default constructor for {@link MarbleSolitaireModelImpl}. Default parameters are {@code
   * armThickness = 3}, {@code sRow = 3}, {@code sCol = 3}
   */
  public MarbleSolitaireModelImpl() {
    this(3, 3, 3);
  }

  /**
   * A constructor for {@link MarbleSolitaireModelImpl} that specifies the board's {@code
   * armThickness}.
   *
   * @param armThickness represents the number of marbles in the top row (or bottom row, or left or
   *                     right columns)
   * @throws IllegalArgumentException if {@code armThickness} is less than 3 or even
   */
  public MarbleSolitaireModelImpl(int armThickness) {
    this(armThickness, (armThickness * 3 - 2) / 2, (armThickness * 3 - 2) / 2);
  }

  /**
   * A constructor for {@link MarbleSolitaireModelImpl} that specifies the board's empty slot.
   *
   * @param sRow the empty slot's row in respect to the board
   * @param sCol the empty slot's column in respect to the board
   * @throws IllegalArgumentException if the empty's slot position's coordinates are non-existent
   */
  public MarbleSolitaireModelImpl(int sRow, int sCol) {
    this(3, sRow, sCol);
  }

  /**
   * A constructor for {@link MarbleSolitaireModelImpl}, with all possible parameters.
   *
   * @param armThickness represents the number of marbles in the top row (or bottom row, or left or
   *                     right columns)
   * @param sRow         the empty slot's row in the board
   * @param sCol         the empty slot's column in the board
   * @throws IllegalArgumentException if {@code armThickness} is less than 3 or even
   * @throws IllegalArgumentException if the empty's slot position's coordinates are non-existent
   */
  public MarbleSolitaireModelImpl(int armThickness, int sRow, int sCol) {
    this.armThickness = armThickness;
    numOfMarbles = 0;
    board = new char[armThickness * 3 - 2][armThickness * 3 - 2];

    if (armThickness % 2 == 0 || armThickness < 3) {
      throw new IllegalArgumentException("Invalid arm thickness");
    }

    if (!Util.doesCoordExistInBoard(armThickness, sRow, sCol)) {
      throw new IllegalArgumentException(
          String.format("Invalid empty cell position (%d,%d)", sRow, sCol));
    }

    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board[row].length; col++) {
        if (Util.doesCoordExistInBoard(armThickness, row, col)) {
          if (col == sCol && row == sRow) {
            board[row][col] = '_';
          } else {
            board[row][col] = 'O';
            numOfMarbles++;
          }
        } else {
          board[row][col] = ' ';
        }
      }
    }

    score = numOfMarbles;
  }

  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) {
    if (Util.isItIllegalMove(this, fromRow, fromCol, toRow, toCol)) {
      throw new IllegalArgumentException("Illegal move");
    }

    board[fromRow][fromCol] = '_';
    board[toRow][toCol] = 'O';
    board[fromRow + ((toRow - fromRow) / 2)][fromCol + ((toCol - fromCol) / 2)] = '_';
    this.numOfMarbles--;
    this.score--;
  }

  @Override
  public boolean isGameOver() {
    boolean isGameOver = true;
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board.length; col++) {
        if (board[row][col] == 'O') {
          isGameOver = isGameOver
              && (Util.isItIllegalMove(this, row, col, row + 2, col)
              && Util.isItIllegalMove(this, row, col, row, col + 2)
              && Util.isItIllegalMove(this, row, col, row - 2, col)
              && Util.isItIllegalMove(this, row, col, row, col - 2));
        }
      }
    }

    return isGameOver;
  }

  @Override
  public String getGameState() {
    String gameState = "";

    for (int row = 0; row < board.length; row++) {
      String currRow = Character.toString(board[row][0]);

      for (int col = 1; col < board[row].length; col++) {
        if (((board[row][col - 1] == 'O' || board[row][col - 1] == '_')
            && board[row][col] == ' ')) {
          break;
        }
        currRow = String.join(" ", currRow, Character.toString(board[row][col]));
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
}
