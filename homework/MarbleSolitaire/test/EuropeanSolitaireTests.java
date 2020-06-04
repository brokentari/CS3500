import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModelImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link EuropeanSolitaireModelImpl}'s functionality , an implementation of {@link
 * MarbleSolitaireModel}.
 */
public class EuropeanSolitaireTests {

  MarbleSolitaireModel game;
  MarbleSolitaireModel game2;
  MarbleSolitaireModel game3;
  MarbleSolitaireModel game4;
  MarbleSolitaireModel game5;

  @Before
  public void init() {
    game = new EuropeanSolitaireModelImpl();
    game2 = new EuropeanSolitaireModelImpl(0, 2);
    game3 = new EuropeanSolitaireModelImpl(5);
    game4 = new EuropeanSolitaireModelImpl(7, 7, 7);
    game5 = new EuropeanSolitaireModelImpl(3, 0, 2);
  }

  @Test
  public void testValidDefaultConstructor() {
    try {
      game = new EuropeanSolitaireModelImpl();
      assertNotEquals(null, game);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testValidEmptyPositionConstructor() {
    try {
      game2 = new EuropeanSolitaireModelImpl(0, 2);
      assertNotEquals(null, game2);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testValidArmThicknessConstructor() {
    try {
      game3 = new EuropeanSolitaireModelImpl(5);
      assertNotEquals(null, game3);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testFullConstructor() {
    try {
      game4 = new EuropeanSolitaireModelImpl(7, 7, 7);
      assertNotEquals(null, game4);
    } catch (Exception e) {
      fail();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvenArmThickness() {
    MarbleSolitaireModel evenArmThickness = new EuropeanSolitaireModelImpl(4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testArmThicknessLessThanThree() {
    MarbleSolitaireModel lessThan3 = new EuropeanSolitaireModelImpl(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidEmptyCell() {
    MarbleSolitaireModel invalidEmptyCell = new EuropeanSolitaireModelImpl(5, 0, 0);
  }

  @Test
  public void testGetGameBoard() {
    assertEquals(
        "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O", game.getGameState());
    assertEquals(
        "    _ O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O", game2.getGameState());

    assertEquals(""
        + "        O O O O O\n"
        + "      O O O O O O O\n"
        + "    O O O O O O O O O\n"
        + "  O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O\n"
        + "O O O O O O _ O O O O O O\n"
        + "O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O\n"
        + "  O O O O O O O O O O O\n"
        + "    O O O O O O O O O\n"
        + "      O O O O O O O\n"
        + "        O O O O O", game3.getGameState());
    assertEquals(""
        + "            O O O O O O O\n"
        + "          O O O O O O O O O\n"
        + "        O O O O O O O O O O O\n"
        + "      O O O O O O O O O O O O O\n"
        + "    O O O O O O O O O O O O O O O\n"
        + "  O O O O O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O O O O O O O\n"
        + "O O O O O O O _ O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O O O O O O O\n"
        + "  O O O O O O O O O O O O O O O O O\n"
        + "    O O O O O O O O O O O O O O O\n"
        + "      O O O O O O O O O O O O O\n"
        + "        O O O O O O O O O O O\n"
        + "          O O O O O O O O O\n"
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
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O _ O O O O O\n"
            + "O O _ O O O O\n"
            + "  O _ O O O\n"
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

  @Test(expected = IllegalArgumentException.class)
  public void testMovingOverEmptySpot() {
    game.move(3, 2, 3, 3);
    game.move(3, 3, 3, 2);
  }

  @Test
  public void testGetScore() {
    assertEquals(36, game.getScore());

    game.move(3, 1, 3, 3);

    game.move(5, 2, 3, 2);

    assertEquals(34, game.getScore());
  }

  private void winningBoardHelper() {
    game5.move(2, 2, 0, 2);
    game5.move(2, 0, 2, 2);
    game5.move(1, 4, 1, 2);
    game5.move(3, 4, 1, 4);
    game5.move(3, 2, 3, 4);
    game5.move(2, 3, 2, 1);
    game5.move(5, 3, 3, 3);
    game5.move(3, 0, 3, 2);
    game5.move(5, 1, 3, 1);
    game5.move(4, 5, 4, 3);
    game5.move(5, 5, 5, 3);
    game5.move(0, 4, 2, 4);
    game5.move(2, 1, 4, 1);
    game5.move(2, 4, 4, 4);
    game5.move(5, 2, 5, 4);
    game5.move(3, 6, 3, 4);
    game5.move(1, 1, 1, 3);
    game5.move(2, 6, 2, 4);
    game5.move(0, 3, 2, 3);
    game5.move(3, 2, 5, 2);
    game5.move(3, 4, 3, 2);
    game5.move(6, 2, 4, 2);
    game5.move(3, 2, 5, 2);
    game5.move(4, 0, 4, 2);
    game5.move(4, 3, 4, 1);
    game5.move(6, 4, 6, 2);
    game5.move(6, 2, 4, 2);
    game5.move(4, 1, 4, 3);
    game5.move(4, 3, 4, 5);
    game5.move(4, 6, 4, 4);
    game5.move(5, 4, 3, 4);
    game5.move(3, 4, 1, 4);
    game5.move(1, 5, 1, 3);
    game5.move(2, 3, 0, 3);
    game5.move(0, 2, 0, 4);
  }

  @Test
  public void testGameOver() {
    assertFalse(game5.isGameOver());
    assertEquals(
        "    _ O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O", game5.getGameState());
    winningBoardHelper();

    assertTrue(game5.isGameOver());
    assertEquals("    _ _ O\n"
        + "  _ _ _ _ _\n"
        + "_ _ _ _ _ _ _\n"
        + "_ _ _ _ _ _ _\n"
        + "_ _ _ _ _ _ _\n"
        + "  _ _ _ _ _\n"
        + "    _ _ _", game5.getGameState());
  }
}