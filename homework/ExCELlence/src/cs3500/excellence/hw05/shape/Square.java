package cs3500.excellence.hw05.shape;

import cs3500.excellence.hw05.Color;

public class Square extends Rectangle {

  /**
   * A constructor for a squre that takes in values for its side length and color.
   *
   * @param sideLength the side length of the square
   * @param color      the {@link Color} of the square
   */
  public Square(int sideLength, Color color) {
    super(sideLength, sideLength, color);
  }
}
