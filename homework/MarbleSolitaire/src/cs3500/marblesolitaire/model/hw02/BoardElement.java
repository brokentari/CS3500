package cs3500.marblesolitaire.model.hw02;

/**
 * All possible values for a board element in a {@link MarbleSolitaireModel}: an empty board space,
 * a marble, or a non-existing space (out of bounds).
 */
public enum BoardElement {
  EMPTY("_"), MARBLE("O"), NULL(" ");
  private final String disp;

  BoardElement(String disp) {
    this.disp = disp;
  }


  @Override
  public String toString() {
    return disp;
  }
}
