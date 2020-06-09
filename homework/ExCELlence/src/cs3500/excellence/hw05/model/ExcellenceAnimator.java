package cs3500.excellence.hw05.model;

import cs3500.excellence.hw05.shape.ModelColor;
import cs3500.excellence.hw05.shape.ModelPosition;
import cs3500.excellence.hw05.shape.ModelShape;
import cs3500.excellence.hw05.view.IViewModel;
import java.util.ArrayList;
import java.util.Map;

/**
 * An interface for an animation application that performs basic animation, such as moving,
 * resizing, recoloring, etc shapes.
 */
public interface ExcellenceAnimator {

  /**
   * Places a named shape unto the canvas at a given {@link ModelPosition}.
   *
   * @param name          the {@link String} id given to the newly created shape
   * @param newModelShape an {@link ModelShape} object that will be added
   * @param modelPosition the {@link ModelPosition} that the {@code newShape} will be placed at
   * @param tick          the tick at which the users wishes to places the shape
   * @throws IllegalArgumentException if the shape's name is already present, the tick is outside
   *                                  the range of present ticks (1 - # of ticks) or if user tries
   *                                  to generate shape at a tick greater than 1 when the animation
   *                                  is empty
   */
  void generateShape(String name, ModelShape newModelShape, ModelPosition modelPosition, int tick);

  /**
   * Moves a {@link ModelShape} to the newly given {@link ModelPosition}.
   *
   * @param name             the id what will be used to get selected {@link ModelShape}
   * @param newModelPosition the new {@link ModelPosition} that the selected shape will be moved to
   * @param initialTick      the beginning tick where the shape will start moving
   * @param endTick          the tick at which the shape will finish its movement
   * @throws IllegalArgumentException if the difference between the initial tick and the end tick is
   *                                  zero, if the initial tick is less than 0, the end tick is less
   *                                  than the initial tick, or if the tick indicated by {@code
   *                                  initialTick} does not contain the referenced {@code Shape}
   */
  void moveShape(String name, ModelPosition newModelPosition, int initialTick, int endTick);

  /**
   * Changes the {@link ModelColor} of the selected {@link ModelShape}.
   *
   * @param name          the id what will be used to get selected {@link ModelShape}
   * @param newModelColor the new {@link ModelColor} that will be applied to the selected shape
   * @param initialTick   the tick where the shape will begin transitioning to the new color
   * @param endTick       the tick where the shape would finally reach the given color
   */
  void changeShapeColor(String name, ModelColor newModelColor, int initialTick, int endTick);

  /**
   * Resizes the selected {@link ModelShape}.
   *
   * @param name        the id what will be used to get selected {@link ModelShape}
   * @param width       the new width of the selected shape
   * @param height      the new height of the selected shape
   * @param initialTick the beginning tick where the shape will begin growing or shrinking
   * @param endTick     the tick at which the shape will have finished resizing
   */
  void resizeShape(String name, int width, int height, int initialTick, int endTick);

  /**
   * Returns the latest {@link ModelPosition} of the selected {@link ModelShape}
   *
   * @param name the id what will be used to get selected {@link ModelShape}
   * @return the {@link ModelPosition} associated with the selected shape
   * @throws IllegalArgumentException if the shape is not present at the given tick or if the name
   *                                  is null
   */
  ModelPosition getShapePosition(String name, int tick);

  /**
   * Returns the {@link ModelColor} of the selected {@link ModelShape}.
   *
   * @param name the id what will be used to get selected {@link ModelShape}
   * @return the {@link ModelColor} associated with the selected shape
   * @throws IllegalArgumentException if the shape is not present at the given tick or if the name
   *                                  is null
   */
  ModelColor getShapeColor(String name, int tick);

  /**
   * Returns the dimensions (width, height) of the selected {@link ModelShape}.
   *
   * @param name the id what will be used to get selected {@link ModelShape}
   * @return a {@link Map<String, Integer>}, which holds the width/height as a key and it associated
   * value
   * @throws IllegalArgumentException if the shape is not present at the given tick or if the name
   *                                  is null
   */
  Map<String, Double> getShapeSize(String name, int tick);

  /**
   * Returns a list of all the moments from all the placed shapes.
   *
   * @return an {@link ArrayList<Moment>} containing all of the moments' information for all the
   * shapes in the model
   */
  ArrayList<Moment> getAnimation();


  /**
   * Makes the given shape the instruction to remain put, not changing anything from it.
   *
   * @param name        the name to reference the appropraite {@link ModelShape}
   * @param initialTick the tick where the shape should start to remain put
   * @param endTick     the tick where the shape can finally perform motion
   */
  void doNothing(String name, int initialTick, int endTick);

  IViewModel returnViewModel();

}
