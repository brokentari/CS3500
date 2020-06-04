package cs3500.excellence.hw05.shape;

import cs3500.excellence.hw05.Color;

public class Circle extends Ellipse {

  /**
   * A constructor for a circle that takes in values for radius and color.
   *
   * @param radius the radius of the cirlce
   * @param color  the {@link Color} of the rectangle
   */
  public Circle(int radius, Color color) {
    super(radius, radius, color);
  }
}
