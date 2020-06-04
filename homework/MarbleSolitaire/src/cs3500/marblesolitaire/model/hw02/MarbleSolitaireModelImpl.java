package cs3500.marblesolitaire.model.hw02;

import cs3500.marblesolitaire.model.hw04.AbstractSolitaireModel;

/**
 * An implementation of the interface {@link MarbleSolitaireModel}, which allows customization of
 * the board, such as arm length, or the location of the initial empty space. This board doesn't
 * allow the player to move
 * <ul>
 *   <li>from or to a non-existing board place</li>
 *   <li>an empty board position</li>
 *   <li>a marble to an occupied space</li>
 *   <li>a marble diagonally</li>
 *   <li>a marble more than two spaces away</li>
 *   <li>a marble over an empty space</li>
 * </ul>
 */
public class MarbleSolitaireModelImpl extends AbstractSolitaireModel {

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
    // created a more abstract way of generating the model using helper methods
    super(new BoardElement[armThickness * 3 - 2][armThickness * 3 - 2],
        armThickness, sRow, sCol);
  }

  // Moved all the methods from this implementation to the abstract class
  @Override
  protected boolean doesCoordinateExistInBoard(int row, int col) {
    return (isBetween(col, armThickness - 2, armThickness * 2 - 1)
        && isBetween(row, -1, armThickness * 3 - 2))
        || (isBetween(row, armThickness - 2, armThickness * 2 - 1)
        && isBetween(col, -1, armThickness * 3 - 2));
  }

}
