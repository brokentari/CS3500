package cs3500.excellence.hw05.animation;

import cs3500.excellence.hw05.Position;
import cs3500.excellence.hw05.shape.Shape;
import java.util.ArrayList;

public class AnimatedShape {

  ArrayList<MotionType> motionsExperienced;
  String name;
  Shape shape;
  Position position;


  AnimatedShape(String name, Shape shape, Position position, MotionType m) {
    this.name = name;
    this.shape = shape;
    this.position = position;
    motionsExperienced = new ArrayList<>();
    motionsExperienced.add(m);
  }

  public AnimatedShape(String name, Shape newShape, Position position) {
    this.name = name;
    this.shape = newShape;
    this.position = position;
    motionsExperienced = new ArrayList<>();
  }

  protected void addMotionType(MotionType m) {
    this.motionsExperienced.add(m);
  }

  @Override
  public String toString() {
    return String.format(position.toString() + "  " +
        shape.toString());
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    // null check
    if (obj == null) {
      return false;
    }

    // this instance check
    if (this == obj) {
      return true;
    }

    // instanceof Check and actual value check
    if (obj instanceof AnimatedShape) {
      AnimatedShape compare = (AnimatedShape) obj;
      return this.name.equals(compare.name) && this.shape.equals(compare.shape);
    } else {
      return false;
    }
  }
}
