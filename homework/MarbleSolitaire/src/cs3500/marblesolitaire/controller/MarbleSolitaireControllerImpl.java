package cs3500.marblesolitaire.controller;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * An implementation of {@link MarbleSolitaireController} for the console to control the execution
 * of the given board's gameplay, along with printing logs from the model.
 */
public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {

  private final Appendable output;
  private final Scanner input;

  /**
   * The constructor for {@link MarbleSolitaireControllerImpl}, which creates a Scanner with the
   * given {@link Readable} for its input and an {@link Appendable} as its output.
   *
   * @param rd the argument used when constructing the {@link Scanner} for the controller
   * @param ap {@link Appendable} that is used to append game logs
   * @throws IllegalArgumentException if either {@code rd} or {@code ap} are null
   */
  public MarbleSolitaireControllerImpl(Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Readable and Appendable cannot be null");
    }

    this.output = ap;
    this.input = new Scanner(rd);
  }

  /**
   * Goes through the given {@link Scanner} and parses the input into the appropriate action to be
   * performed by the {@link MarbleSolitaireModel}.
   *
   * @param scan - the input stream the method will go through
   * @return the {@link ArrayList} containing the integer values for the user's move
   * @throws IllegalStateException when there is no more input in the {@link Scanner}.
   */
  private ArrayList<Integer> parseMove(Scanner scan) throws IllegalStateException {
    ArrayList<Integer> move = new ArrayList<>();

    while (move.size() < 4) {
      try {
        move.add(scan.nextInt());
      } catch (InputMismatchException ime) {
        ArrayList<String> command = new ArrayList<>();
        command.add(scan.next());

        if (command.contains("Q") || command.contains("q")) {
          return null;
        } else {
          printToConsole("Please enter valid input\n");
        }
      } catch (NoSuchElementException elementException) {
        throw new IllegalStateException("No more input");
      }
    }

    return move;
  }


  /**
   * Executes a single game of marble solitaire given a {@link MarbleSolitaireModel}. When the game
   * over, the {@code playGame} method ends. Displays an error in console for any exception thrown.
   *
   * @param m a non-null {@link MarbleSolitaireModel}
   * @throws IllegalArgumentException when the given {@link MarbleSolitaireModel} is {@code null}
   * @throws IllegalStateException    when there is no more input from the {@link Scanner}
   */
  @Override
  public void playGame(MarbleSolitaireModel m) throws IllegalArgumentException,
      IllegalStateException {
    if (m == null) {
      throw new IllegalArgumentException("Cannot pass a null model.");
    }

    boolean invalidMove = false;

    while (true) {
      ArrayList<Integer> move;

      if (!invalidMove) {
        printToConsole(m.getGameState() + "\n");
        printToConsole("Score: " + m.getScore() + "\n");
      }

      move = parseMove(input);


      // Removed some of the exceptions that the method should not have thrown
      if (move == null) {
        printToConsole("Game quit!\nState of game when quit:\n");
        break;
      } else if (move.size() != 4) {
        throw new IllegalStateException("Cannot move with the given inputs");
      } else {
        try {
          m.move(move.get(0) - 1, move.get(1) - 1,
              move.get(2) - 1, move.get(3) - 1);

          invalidMove = false;
          if (m.isGameOver()) {
            printToConsole("Game over!\n");
            break;
          }
        } catch (IllegalArgumentException iae) {
          invalidMove = true;
          printToConsole("Invalid move. Play again. " + iae.getMessage() + "\n");
        }
      }
    }
    printToConsole(m.getGameState() + "\n");
    printToConsole("Score: " + m.getScore() + "\n");
    input.close();
  }


  private void printToConsole(String message) throws IllegalStateException {
    try {
      output.append(message);
    } catch (IOException ioe) {
      throw new IllegalStateException("Failed to append", ioe);
    }
  }
}

