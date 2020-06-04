package cs3500.excellence.hw05.animation;

import cs3500.excellence.hw05.Color;
import cs3500.excellence.hw05.Position;
import cs3500.excellence.hw05.shape.Shape;
import java.util.ArrayList;
import java.util.Map;

/**
 * An interface for an animation application that performs basic animation, such as moving,
 * resizing, recoloring, etc shapes.
 */
public interface ExcellenceAnimator {

  /**
   * Places a named shape unto the canvas at a given {@link Position}.
   *
   * @param name     the {@link String} id given to the newly created shape
   * @param newShape an {@link Shape} object that will be added
   * @param position the {@link Position} that the {@code newShape} will be placed at
   * @param tick     the tick at which the users wishes to places the shape
   * @throws IllegalArgumentException if the shape's name is already present, the tick is outside
   *                                  the range of present ticks (1 - # of ticks) or if user tries
   *                                  to generate shape at a tick greater than 1 when the animation
   *                                  is empty
   */
  void generateShape(String name, Shape newShape, Position position, int tick);

  /**
   * Moves a {@link Shape} to the newly given {@link Position}.
   *
   * @param name        the id what will be used to get selected {@link Shape}
   * @param newPosition the new {@link Position} that the selected shape will be moved to
   * @param initialTick the beginning tick where the shape will start moving
   * @param endTick     the tick at which the shape will finish its movement
   * @throws IllegalArgumentException if the difference between the initial tick and the end tick is
   *                                  zero, if the initial tick is less than 0, the end tick is less
   *                                  than the initial tick, or if the tick indicated by {@code
   *                                  initialTick} does not contain the referenced {@code Shape}
   */
  void moveShape(String name, Position newPosition, int initialTick, int endTick);

  /**
   * Changes the {@link Color} of the selected {@link Shape}.
   *
   * @param name        the id what will be used to get selected {@link Shape}
   * @param newColor    the new {@link Color} that will be applied to the selected shape
   * @param initialTick the tick where the shape will begin transitioning to the new color
   * @param endTick     the tick where the shape would finally reach the given color
   */
  void changeShapeColor(String name, Color newColor, int initialTick, int endTick);

  /**
   * Resizes the selected {@link Shape}.
   *
   * @param name        the id what will be used to get selected {@link Shape}
   * @param width       the new width of the selected shape
   * @param height      the new height of the selected shape
   * @param initialTick the beginning tick where the shape will begin growing or shrinking
   * @param endTick     the tick at which the shape will have finished resizing
   */
  void resizeShape(String name, int width, int height, int initialTick, int endTick);

  /**
   * Returns the latest {@link Position} of the selected {@link Shape}
   *
   * @param name the id what will be used to get selected {@link Shape}
   * @return the {@link Position} associated with the selected shape
   * @throws IllegalArgumentException if the shape is not present at the given tick or if the name
   *                                  is null
   */
  Position getShapePosition(String name, int tick);

  /**
   * Returns the {@link Color} of the selected {@link Shape}.
   *
   * @param name the id what will be used to get selected {@link Shape}
   * @return the {@link Color} associated with the selected shape
   * @throws IllegalArgumentException if the shape is not present at the given tick or if the name
   *                                  is null
   */
  Color getShapeColor(String name, int tick);

  /**
   * Returns the dimensions (width, height) of the selected {@link Shape}.
   *
   * @param name the id what will be used to get selected {@link Shape}
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
   * Returns a snapshot of the animation at the given tick.
   *
   * @param tick the corresponding tick of the animation
   * @return a {@link Moment} with all the information about that tick, including a list of {@link
   * AnimatedShape}
   */
  Moment getSingleTick(int tick);

  /**
   * Makes the given shape the instruction to remain put, not changing anything from it.
   *
   * @param name        the name to reference the appropraite {@link Shape}
   * @param initialTick the tick where the shape should start to remain put
   * @param endTick     the tick where the shape can finally perform motion
   */
  void doNothing(String name, int initialTick, int endTick);
}
