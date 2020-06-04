package cs3500.excellence.hw05.shape;

import cs3500.excellence.hw05.Color;
import java.util.HashMap;
import java.util.Map;

abstract class AbstractShape implements Shape {

  protected Color color;
  protected double width, height;

  AbstractShape(double width, double height, Color color) {

    if (width < 0 || height < 0 || color == null) {
      throw new IllegalArgumentException("Cannot have a shape with negative dimensions");
    }

    this.width = width;
    this.height = height;
    this.color = color;
  }

  @Override
  public void changeColor(Color newColor) {
    this.color = newColor;
  }

  @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public void resize(double newWidth, double newHeight) {
    this.width = newWidth;
    this.height = newHeight;
  }

  @Override
  public Map<String, Double> getSize() {
    Map<String, Double> dimension = new HashMap<>();
    dimension.put("width", width);
    dimension.put("height", height);

    return dimension;
  }

  @Override
  public String toString() {
    return String.format("%d  %d  " + this.color.toString(), (int) this.width, (int) this.height);
  }

  @Override
  public int hashCode() {
    return Double.hashCode(this.area());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    // this instance check
    if (this == obj) {
      return true;
    }

    // instanceof Check and actual value check
    if (obj instanceof Shape) {
      Shape compare = (Shape) obj;
      return this.getSize().equals(compare.getSize());
    } else {
      return false;
    }
  }
}
