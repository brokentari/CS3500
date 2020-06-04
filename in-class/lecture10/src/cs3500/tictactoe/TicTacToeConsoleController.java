package cs3500.tictactoe;

import java.io.IOException;
import java.util.Scanner;

public class TicTacToeConsoleController implements TicTacToeController {

  private final Appendable out;
  private final Scanner scan;

  public TicTacToeConsoleController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    scan = new Scanner(in);
  }

  @Override
  public void playGame(TicTacToe m) {
    try {
      out.append("Hello world");
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

}
