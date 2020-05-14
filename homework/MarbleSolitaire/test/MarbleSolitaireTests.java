import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link MarbleSolitaireModelImpl}, an implementation of {@link MarbleSolitaireModel}.
 */
public class MarbleSolitaireTests {

  MarbleSolitaireModel game;
  MarbleSolitaireModel game2;
  MarbleSolitaireModel game3;
  MarbleSolitaireModel game4;

  @Before
  public void init() {
    game = new MarbleSolitaireModelImpl();
    game2 = new MarbleSolitaireModelImpl(0, 2);
    game3 = new MarbleSolitaireModelImpl(5);
    game4 = new MarbleSolitaireModelImpl(7, 7, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvenArmThickness() {
    MarbleSolitaireModel evenArmThickness = new MarbleSolitaireModelImpl(4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testArmThicknessLessThanThree() {
    MarbleSolitaireModel lessThan3 = new MarbleSolitaireModelImpl(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidEmptyCell() {
    MarbleSolitaireModel invalidEmptyCell = new MarbleSolitaireModelImpl(5, 3, 3);
  }

  @Test
  public void testGetGameBoard() {
    assertEquals(
        "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O", game.getGameState());
    assertEquals(
        "    _ O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O", game2.getGameState());

    assertEquals(""
        + "        O O O O O\n"
        + "        O O O O O\n"
        + "        O O O O O\n"
        + "        O O O O O\n"
        + "O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O\n"
        + "O O O O O O _ O O O O O O\n"
        + "O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O\n"
        + "        O O O O O\n"
        + "        O O O O O\n"
        + "        O O O O O\n"
        + "        O O O O O", game3.getGameState());
    assertEquals(""
        + "            O O O O O O O\n"
        + "            O O O O O O O\n"
        + "            O O O O O O O\n"
        + "            O O O O O O O\n"
        + "            O O O O O O O\n"
        + "            O O O O O O O\n"
        + "O O O O O O O O O O O O O O O O O O O\n"
        + "O O O O O O O _ O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O O O O O O O\n"
        + "            O O O O O O O\n"
        + "            O O O O O O O\n"
        + "            O O O O O O O\n"
        + "            O O O O O O O\n"
        + "            O O O O O O O\n"
        + "            O O O O O O O", game4.getGameState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMove() {
    game.move(0, 0, 3, 3);
  }

  @Test
  public void testValidMove() {
    game.move(3, 1, 3, 3);

    game.move(5, 2, 3, 2);

    assertEquals(
        "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O _ O O O O O\n"
            + "O O _ O O O O\n"
            + "    _ O O\n"
            + "    O O O", game.getGameState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovingNonExistingMarble() {
    game.move(0, 0, 3, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovingToOccupiedPos() {
    game.move(0, 2, 1, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovingEmptyPosition() {
    game.move(3, 3, 3, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovingMoreThanTwoSpaces() {
    game.move(3, 2, 3, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovingDiagonally() {
    game.move(3, 1, 4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovingToInvalidSpace() {
    game.move(3, 1, 0, 0);
  }

  @Test
  public void testGetScore() {
    assertEquals(32, game.getScore());

    game.move(3, 1, 3, 3);

    game.move(5, 2, 3, 2);

    assertEquals(30, game.getScore());
  }

  public void winningBoardHelper() {
    game.move(1, 3, 3, 3);
    game.move(2, 5, 2, 3);
    game.move(0, 4, 2, 4);
    game.move(3, 4, 1, 4);
    game.move(5, 4, 3, 4);
    game.move(4, 6, 4, 4);
    game.move(4, 3, 4, 5);
    game.move(2, 6, 4, 6);
    game.move(4, 6, 4, 4);
    game.move(2, 2, 2, 4);
    game.move(2, 0, 2, 2);
    game.move(4, 1, 4, 3);
    game.move(4, 3, 4, 5);
    game.move(4, 5, 2, 5);
    game.move(2, 5, 2, 3);
    game.move(2, 3, 2, 1);
    game.move(6, 2, 4, 2);
    game.move(3, 2, 5, 2);
    game.move(6, 4, 6, 2);
    game.move(6, 2, 4, 2);
    game.move(4, 0, 2, 0);
    game.move(2, 0, 2, 2);
    game.move(0, 2, 0, 4);
    game.move(0, 4, 2, 4);
    game.move(2, 4, 4, 4);
    game.move(1, 2, 3, 2);
    game.move(3, 2, 5, 2);
    game.move(5, 2, 5, 4);
    game.move(5, 4, 3, 4);
    game.move(3, 4, 3, 2);
    game.move(3, 1, 3, 3);
  }

  @Test
  public void testGameOver() {
    assertFalse(game.isGameOver());
    assertEquals(
        "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O", game.getGameState());
    winningBoardHelper();

    assertTrue(game.isGameOver());
    assertEquals("    _ _ _\n"
        + "    _ _ _\n"
        + "_ _ _ _ _ _ _\n"
        + "_ _ _ O _ _ _\n"
        + "_ _ _ _ _ _ _\n"
        + "    _ _ _\n"
        + "    _ _ _", game.getGameState());
  }
}