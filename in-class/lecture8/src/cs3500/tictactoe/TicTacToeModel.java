package cs3500.tictactoe;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * An implementation of the {@link TicTacToe} interface.
 */
public class TicTacToeModel implements TicTacToe {

  private final Player[][] board;
  private Player current = Player.X;
  // add your implementation here

  /**
   * Creates a standard game of tic-tac-toe.
   */
  public TicTacToeModel() {
    board = new Player[3][3];
  }

  @Override
  public String toString() {
    // Using Java stream API to save code:
    return Arrays.stream(getBoard()).map(
        row -> " " + Arrays.stream(row).map(
            p -> p == null ? " " : p.toString()).collect(Collectors.joining(" | ")))
        .collect(Collectors.joining("\n-----------\n"));
    // This is the equivalent code as above, but using iteration, and still using the helpful
    // built-in String.join method.
    // List<String> rows = new ArrayList<>();
    // for(Player[] row : getBoard()) {
    //   List<String> rowStrings = new ArrayList<>();
    //   for(Player p : row) {
    //     if(p == null) {
    //       rowStrings.add(" ");
    //     } else {
    //       rowStrings.add(p.toString());
    //     }
    //   }
    //   rows.add(" " + String.join(" | ", rowStrings));
    // }
    // return String.join("\n-----------\n", rows);
  }

  @Override
  public void move(int r, int c) {
    if (r > 2 || r < 0 || c < 0 || c > 2) {
      throw new IllegalArgumentException("Cannot move to a position out of the board");
    }

    if (board[r][c] != null) {
      throw new IllegalArgumentException("Cannot move to an occupied space");
    }

    if (this.isGameOver()) {
      throw new IllegalStateException("Cannot move when the game is over");
    }

    board[r][c] = this.getTurn();

    if (current.toString() == "X") {
      current = Player.O;
    } else {
      current = Player.X;
    }
  }

  @Override
  public Player getTurn() {
    return current;
  }

  @Override
  public boolean isGameOver() {
    boolean isBoardFull = true;
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board[0].length; col++) {
        if (board[row][col] == null) {
          isBoardFull = false;
        }
      }
    }
    return isBoardFull || (this.getWinner() != null);
  }

  @Override
  public Player getWinner() {
    boolean winnerPresent = false;
    Player winner;
    for (int row = 0; row < board.length; row++) {
      if (checkThreeEqualPlayers(board[row][0], board[row][1], board[row][2])) {
        return this.getMarkAt(row, 0);
      } else if (checkThreeEqualPlayers(board[0][row], board[1][row], board[2][row])) {
        return this.getMarkAt(0, row);
      } else if (checkThreeEqualPlayers(board[0][0], board[1][1], board[2][2]) ||
          checkThreeEqualPlayers(board[0][2], board[1][1], board[2][0])) {
        return this.getMarkAt(1, 1);
      }
    }

    return null;
  }

  private boolean checkThreeEqualPlayers(Player first, Player second, Player third) {
    return ((first != null && second != null && third != null)
        && first.toString().equals(second.toString())
        && second.toString().equals(third.toString()));
  }

  @Override
  public Player[][] getBoard() {
    Player[][] newBoard = new Player[3][3];
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        newBoard[row][col] = board[row][col];
      }
    }

    return newBoard;
  }

  @Override
  public Player getMarkAt(int r, int c) {
    if (r > 2 || r < 0 || c < 0 || c > 2) {
      throw new IllegalArgumentException("Cannot move to a position out of the board");
    }

    return board[r][c];
  }
}
