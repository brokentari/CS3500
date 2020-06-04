package cs3500.excellence.hw05.shape;

import cs3500.excellence.hw05.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * A class of rearresting an ellipse.
 */
public class Ellipse extends AbstractShape {

  /**
   * A constructor for a ellipse that takes in values for its width, height, and color.
   *
   * @param width  the width of the ellipse
   * @param height the height of the ellipse
   * @param color  the {@link Color} of the rectangle
   */
  public Ellipse(double width, double height, Color color) {
    super(width, height, color);
  }

  @Override
  public Map<String, Double> getSize() {
    Map<String, Double> dimensions = new HashMap<>();

    dimensions.put("width", this.width);
    dimensions.put("height", this.height);

    return dimensions;
  }

  @Override
  public double area() {
    return Math.PI * (this.height / 2) * (this.width / 2);
  }

  @Override
  public Shape copy() {
    return new Ellipse(this.width, this.height, this.color);
  }
}
