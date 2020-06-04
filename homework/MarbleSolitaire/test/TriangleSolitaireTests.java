import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModelImpl;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests for {@link TriangleSolitaireModelImpl}'s functionality , an implementation of {@link
 * MarbleSolitaireModel}.
 */
public class TriangleSolitaireTests {

  MarbleSolitaireModel game;
  MarbleSolitaireModel game2;
  MarbleSolitaireModel game3;
  MarbleSolitaireModel game4;

  @Before
  public void init() {
    game = new TriangleSolitaireModelImpl();
    game2 = new TriangleSolitaireModelImpl(1, 1);
    game3 = new TriangleSolitaireModelImpl(7);
    game4 = new TriangleSolitaireModelImpl(7, 6, 6);
  }

  @Test
  public void testValidDefaultConstructor() {
    try {
      game = new TriangleSolitaireModelImpl();
      assertNotEquals(null, game);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testValidEmptyPositionConstructor() {
    try {
      game2 = new TriangleSolitaireModelImpl(1, 1);
      assertNotEquals(null, game2);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testValidArmThicknessConstructor() {
    try {
      game3 = new TriangleSolitaireModelImpl(5);
      assertNotEquals(null, game3);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testFullConstructor() {
    try {
      game4 = new TriangleSolitaireModelImpl(5, 1, 1);
      assertNotEquals(null, game4);
    } catch (Exception e) {
      fail();
    }
  }


  @Test(expected = IllegalArgumentException.class)
  public void testInvalidEmptyCell() {
    MarbleSolitaireModel invalidEmptyCell = new TriangleSolitaireModelImpl(5, 0, 2);
  }

  @Test
  public void testGetGameBoard() {
    assertEquals(
        "    _\n"
            + "   O O\n"
            + "  O O O\n"
            + " O O O O\n"
            + "O O O O O", game.getGameState());
    assertEquals(
        "    O\n"
            + "   O _\n"
            + "  O O O\n"
            + " O O O O\n"
            + "O O O O O", game2.getGameState());

    assertEquals(""
        + "      _\n"
        + "     O O\n"
        + "    O O O\n"
        + "   O O O O\n"
        + "  O O O O O\n"
        + " O O O O O O\n"
        + "O O O O O O O", game3.getGameState());
    assertEquals(""
        + "      O\n"
        + "     O O\n"
        + "    O O O\n"
        + "   O O O O\n"
        + "  O O O O O\n"
        + " O O O O O O\n"
        + "O O O O O O _", game4.getGameState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMove() {
    game.move(0, 0, 3, 3);
  }

  @Test
  public void testValidMove() {
    game.move(2, 2, 0, 0);

    game.move(3, 1, 1, 1);

    game.move(3, 3, 3, 1);

    assertEquals(
        "    O\n"
            + "   O O\n"
            + "  O _ _\n"
            + " O O _ _\n"
            + "O O O O O", game.getGameState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovingNonExistingMarble() {
    game.move(0, 0, 3, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovingToOccupiedPos() {
    game.move(1, 1, 3, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovingEmptyPosition() {
    game.move(0, 0, 2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovingMoreThanTwoSpaces() {
    game.move(3, 2, 3, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovingToInvalidSpace() {
    game.move(3, 1, 0, 1);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testMovingOverEmptySpot() {
    game.move(3, 2, 3, 3);
    game.move(3, 3, 3, 2);
  }

  @Test
  public void testGetScore() {
    assertEquals(14, game.getScore());

    game.move(2, 2, 0, 0);

    game.move(3, 1, 1, 1);

    game.move(3, 3, 3, 1);

    assertEquals(11, game.getScore());
  }

  private void winningBoardHelper() {
    game.move(2, 0, 0, 0);
    game.move(2, 2, 2, 0);
    game.move(4, 4, 2, 2);
    game.move(1, 1, 3, 3);
    game.move(4, 2, 2, 2);
    game.move(4, 0, 4, 2);
    game.move(4, 3, 4, 1);
    game.move(4, 1, 2, 1);
    game.move(3, 3, 1, 1);
    game.move(3, 0, 1, 0);//
    game.move(0, 0, 2, 2);
    game.move(2, 2, 2, 0);
    game.move(2, 0, 0, 0);
  }

  @Test
  public void testGameOver() {
    assertFalse(game.isGameOver());
    assertEquals(
        "    _\n"
            + "   O O\n"
            + "  O O O\n"
            + " O O O O\n"
            + "O O O O O", game.getGameState());
    winningBoardHelper();

    assertTrue(game.isGameOver());
    assertEquals("    O\n"
        + "   _ _\n"
        + "  _ _ _\n"
        + " _ _ _ _\n"
        + "_ _ _ _ _", game.getGameState());
  }
}
