package cs3500.excellence.hw05.shape;

import cs3500.excellence.hw05.Color;
import java.util.Map;

/**
 * An interface to represent a geometric shape, such as a rectangle or triangle, along with
 * modification methods.
 */
public interface Shape {

  /**
   * Changes the {@link Shape}'s color.
   *
   * @param newColor the new color the shape will have
   */
  void changeColor(Color newColor);

  /**
   * Resizes the {@link Shape} to the given width and height.
   *
   * @param newWidth  the {@link Shape}'s new width
   * @param newHeight the {@link Shape}'s new height
   */
  void resize(double newWidth, double newHeight);

  /**
   * Returns the {@link Shape}'s dimensions.
   *
   * @return a {@link Map} with the width/height as keys and their respective values
   */
  Map<String, Double> getSize();

  /**
   * Returns the {@link Shape}'s color.
   *
   * @return the shape's color
   */
  Color getColor();


  /**
   * Returns a string representation of the shape, where it displays the shape's width and height
   * first, followed with a string representation of its {@link Color}.
   *
   * @return the {@link Shape}'s string representation
   */
  String toString();

  /**
   * Returns the {@link Shape}'s area.
   *
   * @return a integer representing the shape's area
   */
  double area();

  /**
   * Creates a new copy of this shape.
   *
   * @return a new {@link Shape} with the same information as this one
   */
  Shape copy();

  int hashCode();

  boolean equals(Object obj);
}
