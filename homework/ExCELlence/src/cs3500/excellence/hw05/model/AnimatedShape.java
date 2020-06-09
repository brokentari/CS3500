package cs3500.excellence.hw05.model;

import cs3500.excellence.hw05.shape.ModelPosition;
import cs3500.excellence.hw05.shape.ModelShape;
import java.util.ArrayList;

public class AnimatedShape {

  protected final String name;
  protected ArrayList<MotionType> motionsExperienced;
  protected ModelShape modelShape;
  protected ModelPosition modelPosition;


  AnimatedShape(String name, ModelShape modelShape, ModelPosition modelPosition, MotionType m) {
    this.name = name;
    this.modelShape = modelShape;
    this.modelPosition = modelPosition;
    motionsExperienced = new ArrayList<>();
    motionsExperienced.add(m);
  }

  AnimatedShape(String name, ModelShape newModelShape, ModelPosition modelPosition) {
    this.name = name;
    this.modelShape = newModelShape;
    this.modelPosition = modelPosition;
    motionsExperienced = new ArrayList<>();
  }


  protected void addMotionType(MotionType m) {
    this.motionsExperienced.add(m);
  }

  @Override
  public String toString() {
    return String.format(modelPosition.toString() + "  " +
        modelShape.toString());
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
      return this.name.equals(compare.name) && this.modelShape.equals(compare.modelShape);
    } else {
      return false;
    }
  }

  public ModelShape getModelShape() {
    return this.modelShape.copy();
  }

  public ModelPosition getModelPosition() {
    return this.modelPosition.copy();
  }

  public AnimatedShape copy() {
    return new AnimatedShape(name, modelShape.copy(), modelPosition.copy());
  }
}
