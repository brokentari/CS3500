import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import cs3500.marblesolitaire.controller.MarbleSolitaireController;
import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelImpl;
import java.io.StringReader;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the {@link MarbleSolitaireControllerImpl}, using mocks for readable and
 * appendable.
 */
public class MarbleControllerTests {

  MarbleSolitaireModel model;
  StringBuilder gameLog;
  MarbleSolitaireController msc;

  @Before
  public void init() {
    model = new MarbleSolitaireModelImpl();
    gameLog = new StringBuilder();
  }

  @Test
  public void testValidControllerConstructor() {
    StringBuilder gameLog = new StringBuilder();
    try {
      MarbleSolitaireController msc = new MarbleSolitaireControllerImpl(
          new StringReader("4 2 4 4"), gameLog);
      assertNotEquals(null, msc);

    } catch (Exception e) {
      fail();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullInput() {
    init();
    msc = new MarbleSolitaireControllerImpl(null, gameLog);
    msc.playGame(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullOutput() {
    init();
    msc = new MarbleSolitaireControllerImpl(
        new StringReader("4 2 4 4"), null);
    msc.playGame(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    model = null;
    gameLog = new StringBuilder();
    msc = new MarbleSolitaireControllerImpl(
        new StringReader("4 2 4 4"), gameLog);

    msc.playGame(model);
  }

  @Test
  public void testSingleValidMove() {
    init();
    msc = new MarbleSolitaireControllerImpl(
        new StringReader("4 2 4 4"), gameLog);

    try {
      msc.playGame(model);
    } catch (IllegalStateException ise) {

      assertEquals("    O O O\n"
          + "    O O O\n"
          + "O O O O O O O\n"
          + "O O O _ O O O\n"
          + "O O O O O O O\n"
          + "    O O O\n"
          + "    O O O\n"
          + "Score: 32\n"
          + "    O O O\n"
          + "    O O O\n"
          + "O O O O O O O\n"
          + "O _ _ O O O O\n"
          + "O O O O O O O\n"
          + "    O O O\n"
          + "    O O O\n"
          + "Score: 31\n", gameLog.toString());
    }
  }

  @Test
  public void testGameOver() {
    init();
    assertFalse(model.isGameOver());
    msc = new MarbleSolitaireControllerImpl(
        new StringReader("2 4 4 4 3 6 3 4 1 5 "
            + "3 5 4 5 2 5 6 5 4 5 5 7 5 5 5 4 5 "
            + "6 3 7 5 7 5 7 5 5 3 3 3 5 3 1 3 3 "
            + "5 2 5 4 5 4 5 6 5 6 3 6 3 6 3 4 3 "
            + "4 3 2 7 3 5 3 4 3 6 3 7 5 7 3 7 3 "
            + "5 3 5 1 3 1 3 1 3 3 1 3 1 5 1 5 3 "
            + "5 3 5 5 5 2 3 4 3 4 3 6 3 6 3 6 5 "
            + "6 5 4 5 4 5 4 3 4 2 4 4 "), gameLog);

    msc.playGame(model);
    assertTrue(model.isGameOver());
    assertEquals(1, model.getScore());

    String[] lines = gameLog.toString().split("\n");
    assertEquals(257, lines.length);
    assertEquals("Game over!", lines[lines.length - 9]);

    String lastMsg = String.join("\n",
        Arrays.copyOfRange(lines, lines.length - 8, lines.length));

    assertEquals("    _ _ _\n"
        + "    _ _ _\n"
        + "_ _ _ _ _ _ _\n"
        + "_ _ _ O _ _ _\n"
        + "_ _ _ _ _ _ _\n"
        + "    _ _ _\n"
        + "    _ _ _\n"
        + "Score: 1", lastMsg);
  }

  @Test
  public void testCompleteGameWithSomeInvalidMoves() {
    init();
    assertFalse(model.isGameOver());
    msc = new MarbleSolitaireControllerImpl(
        new StringReader("2 4 4 4 3 6 3 4 1 5 3 5 "
            + "4 3 4 6 2 2 4 4 " // 2 invalid moves
            + "4 5 2 5 6 5 4 5 5 7 5 5 5 4 5 "
            + "6 3 7 5 7 5 7 5 5 3 3 3 5 3 1 3 3 "
            + "5 2 5 4 5 4 5 6 5 6 3 6 3 6 3 4 3 "
            + "4 3 2 7 3 5 3 4 3 6 3 7 5 7 3 7 3 "
            + "5 3 5 1 3 1 3 1 3 3 1 3 1 5 1 5 3 "
            + "5 3 5 5 5 2 3 4 3 4 3 6 3 6 3 6 5 "
            + "6 5 4 5 4 5 4 3 4 2 4 4 "), gameLog);

    msc.playGame(model);
    assertEquals(1, model.getScore());

    String[] lines = gameLog.toString().split("\n");
    assertEquals(259, lines.length); // increases lines length by 2, due to error messages
    assertEquals("Game over!", lines[lines.length - 9]);

    String lastMsg = String.join("\n",
        Arrays.copyOfRange(lines, lines.length - 8, lines.length));

    assertEquals("    _ _ _\n"
        + "    _ _ _\n"
        + "_ _ _ _ _ _ _\n"
        + "_ _ _ O _ _ _\n"
        + "_ _ _ _ _ _ _\n"
        + "    _ _ _\n"
        + "    _ _ _\n"
        + "Score: 1", lastMsg);
  }

  @Test
  public void testQuitGameAsFirstInput() {
    init();
    msc = new MarbleSolitaireControllerImpl(new StringReader("q 2 4"), gameLog);
    try {
      msc.playGame(model);
    } catch (IllegalStateException e) {

      String[] lines = gameLog.toString().split("\n");
      assertEquals("Game quit!", lines[lines.length - 10]);

      String lastMsg = String.join("\n",
          Arrays.copyOfRange(lines, lines.length - 9, lines.length));

      assertEquals("State of game when quit:\n"
          + "    O O O\n"
          + "    O O O\n"
          + "O O O O O O O\n"
          + "O O O _ O O O\n"
          + "O O O O O O O\n"
          + "    O O O\n"
          + "    O O O\n"
          + "Score: 32", lastMsg);
    }
  }

  @Test
  public void testQuitGameInTOColumn() {
    init();
    msc = new MarbleSolitaireControllerImpl(new StringReader("2 2 4 q"), gameLog);
    try {
      msc.playGame(model);
    } catch (IllegalStateException e) {

      String[] lines = gameLog.toString().split("\n");
      assertEquals("Game quit!", lines[lines.length - 10]);

      String lastMsg = String.join("\n",
          Arrays.copyOfRange(lines, lines.length - 9, lines.length));

      assertEquals("State of game when quit:\n"
          + "    O O O\n"
          + "    O O O\n"
          + "O O O O O O O\n"
          + "O O O _ O O O\n"
          + "O O O O O O O\n"
          + "    O O O\n"
          + "    O O O\n"
          + "Score: 32", lastMsg);
    }
  }
  @Test
  public void testQuitGameInFromCol() {
    init();
    msc = new MarbleSolitaireControllerImpl(new StringReader("2 q 2 4"), gameLog);
    try {
      msc.playGame(model);
    } catch (IllegalStateException e) {

      String[] lines = gameLog.toString().split("\n");
      assertEquals("Game quit!", lines[lines.length - 10]);

      String lastMsg = String.join("\n",
          Arrays.copyOfRange(lines, lines.length - 9, lines.length));

      assertEquals("State of game when quit:\n"
          + "    O O O\n"
          + "    O O O\n"
          + "O O O O O O O\n"
          + "O O O _ O O O\n"
          + "O O O O O O O\n"
          + "    O O O\n"
          + "    O O O\n"
          + "Score: 32", lastMsg);
    }
  }

  @Test
  public void testQuitGameAnywhereInInput() {
    init();
    msc = new MarbleSolitaireControllerImpl(new StringReader("2 2 q 4"), gameLog);

    try {
      msc.playGame(model);
    } catch (IllegalStateException e) {
      String[] lines = gameLog.toString().split("\n");
      assertEquals("Game quit!", lines[lines.length - 10]);

      String lastMsg = String.join("\n",
          Arrays.copyOfRange(lines, lines.length - 9, lines.length));

      assertEquals("State of game when quit:\n"
          + "    O O O\n"
          + "    O O O\n"
          + "O O O O O O O\n"
          + "O O O _ O O O\n"
          + "O O O O O O O\n"
          + "    O O O\n"
          + "    O O O\n"
          + "Score: 32", lastMsg);
    }
  }

  @Test
  public void testSingleBogusInput() {
    init();
    msc = new MarbleSolitaireControllerImpl(new StringReader("s"), gameLog);

    try {
      msc.playGame(model);
    } catch (IllegalStateException ise) {

      String[] lines = gameLog.toString().split("\n");
      assertEquals("Please enter valid input", lines[lines.length - 1]);
    }
  }

  @Test
  public void testBogusInputMidInput() {
    init();
    msc = new MarbleSolitaireControllerImpl(new StringReader("2 2 s 4"), gameLog);
    try {
      msc.playGame(model);
    } catch (IllegalStateException ise) {

      String[] lines = gameLog.toString().split("\n");
      assertEquals("Please enter valid input", lines[lines.length - 1]);
    }
  }

  @Test
  public void testMultipleBogusInput() {
    init();
    msc = new MarbleSolitaireControllerImpl(new StringReader("s x"), gameLog);

    try {
      msc.playGame(model);
    } catch (IllegalStateException ise) {

      String[] lines = gameLog.toString().split("\n");
      assertEquals("Please enter valid input", lines[lines.length - 1]);

      assertEquals("Please enter valid input", lines[lines.length - 2]);
    }
  }

  @Test
  public void testMoveANonExistentMarbleErrorMessage() {
    init();
    msc = new MarbleSolitaireControllerImpl(new StringReader("2 2 4 4"), gameLog);

    try {
      msc.playGame(model);
    } catch (IllegalStateException ise) {

      String[] lines = gameLog.toString().split("\n");
      assertEquals("Invalid move. Play again. Cannot move from or move to a non-existing position",
          lines[lines.length - 1]);
    }
  }

  @Test
  public void testMoveToNonExistentSpaceErrorMessage() {
    init();
    msc = new MarbleSolitaireControllerImpl(new StringReader("4 2 1 1"), gameLog);

    try {
      msc.playGame(model);
    } catch (IllegalStateException ise) {
      String[] lines = gameLog.toString().split("\n");
      assertEquals("Invalid move. Play again. Cannot move from or move to a non-existing position",
          lines[lines.length - 1]);
    }
  }

  @Test
  public void testMovingToOccupiedPositionErrorMessage() {
    init();
    msc = new MarbleSolitaireControllerImpl(new StringReader("4 1 4 3"), gameLog);

    try {
      msc.playGame(model);
    } catch (IllegalStateException ise) {
      String[] lines = gameLog.toString().split("\n");
      assertEquals("Invalid move. Play again. Cannot move to an occupied position",
          lines[lines.length - 1]);
    }
  }

  @Test
  public void testDiagonalMovementErrorMessage() {
    init();
    msc = new MarbleSolitaireControllerImpl(new StringReader("4 3 5 4"), gameLog);

    try {
      msc.playGame(model);
    } catch (IllegalStateException ise) {
      String[] lines = gameLog.toString().split("\n");
      assertEquals("Invalid move. Play again. Cannot move to an occupied position",
          lines[lines.length - 1]);
    }
  }

  @Test
  public void testMovingMoreThanTwoSpaces() {
    init();
    msc = new MarbleSolitaireControllerImpl(new StringReader("4 3 4 6"), gameLog);
    try {
      msc.playGame(model);
    } catch (IllegalStateException ise) {
      String[] lines = gameLog.toString().split("\n");
      assertEquals("Invalid move. Play again. Cannot move to an occupied position",
          lines[lines.length - 1]);
    }
  }

  @Test
  public void testMultipleInvalidMovesInARow() {
    init();
    msc = new MarbleSolitaireControllerImpl(new StringReader("4 3 4 6 2 2 4 4"), gameLog);

    try {
      msc.playGame(model);
    } catch (IllegalStateException ise) {
      String[] lines = gameLog.toString().split("\n");
      assertEquals("Invalid move. Play again. Cannot move to an occupied position",
          lines[lines.length - 2]);

      assertEquals("Invalid move. Play again. Cannot move from or move to a non-existing position",
          lines[lines.length - 1]);
    }
  }

  @Test
  public void testValidInputMixedWithBogusInput() {
    init();
    msc = new MarbleSolitaireControllerImpl(
        new StringReader("4 2 s x 4 4"), gameLog);

    try {
      msc.playGame(model);
    } catch (IllegalStateException ise) {

      assertEquals("    O O O\n"
          + "    O O O\n"
          + "O O O O O O O\n"
          + "O O O _ O O O\n"
          + "O O O O O O O\n"
          + "    O O O\n"
          + "    O O O\n"
          + "Score: 32\n"
          + "Please enter valid input\n"
          + "Please enter valid input\n"
          + "    O O O\n"
          + "    O O O\n"
          + "O O O O O O O\n"
          + "O _ _ O O O O\n"
          + "O O O O O O O\n"
          + "    O O O\n"
          + "    O O O\n"
          + "Score: 31\n", gameLog.toString());
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    MarbleSolitaireModel model = new MarbleSolitaireModelImpl();
    StringReader input = new StringReader("2 4 4 4 3 6 3 4 1 5 \"\n"
        + "            + \"3 5 4 5 2 5 6 5 4 5 5 7 5 5 5 4 5 \"\n"
        + "            + \"6 3 7 5 7 5 7 5 5 3 3 3 5 3 1 3 3 \"\n"
        + "            + \"5 2 5 4 5 4 5 6 5 6 3 6 3 6 3 4 3 \"\n"
        + "            + \"4 3 2 7 3 5 3 4 3 6 3 7 5 7 3 7 3 \"\n"
        + "            + \"5 3 5 1 3 1 3 1 3 3 1 3 1 5 1 5 3 \"\n"
        + "            + \"5 3 5 5 5 2 3 4 3 4 3 6 3 6 3 6 5 \"\n"
        + "            + \"6 5 4 5 4 5 4 3 4 2 4 4 ");
    Appendable gameLog = new FailingAppendable();
    MarbleSolitaireController msc = new MarbleSolitaireControllerImpl(input, gameLog);

    msc.playGame(model);
  }
}
