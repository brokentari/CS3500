package cs3500.excellence.hw05.shape;

import cs3500.excellence.hw05.Color;

/**
 * A class for representing the classic rectangle.
 */
public class Rectangle extends AbstractShape {

  /**
   * A constructor for a rectangle that takes in values for its width, height, and color.
   *
   * @param width  the width of the rectangle
   * @param height the height of the rectangle
   * @param color  the {@link Color} of the rectangle
   */
  public Rectangle(double width, double height, Color color) {
    super(width, height, color);
  }


  @Override
  public double area() {
    return this.width * this.height;
  }

  @Override
  public Shape copy() {
    return new Rectangle(this.width, this.height, this.color);
  }
}
