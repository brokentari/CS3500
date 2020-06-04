package cs3500.excellence.hw05.animation;

import cs3500.excellence.hw05.Color;
import cs3500.excellence.hw05.Position;
import cs3500.excellence.hw05.shape.Shape;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of {@link ExcellenceAnimator} that supports the placing, moving, resizing, and
 * recoloring of multiple {@link Shape}s.
 */
public class ExcellenceAnimatorImpl implements ExcellenceAnimator {

  private final Map<String, Shape> presentShapes;
  private final Map<String, ArrayList<Integer>> animationMoments;
  private final ArrayList<Moment> ticks;
  private AnimatedShape selectedShape;

  /**
   * Default constructor that has the animation speed set at its default value of 1.
   */
  public ExcellenceAnimatorImpl() {
    this(1);
  }

  /**
   * A constructor that has {@code animationSpeed} as its constructor.
   *
   * @param animationSpeed the speed at which the animation is run
   */
  public ExcellenceAnimatorImpl(double animationSpeed) {
    if (animationSpeed < 0) {
      throw new IllegalArgumentException("Cannot have negative speed");
    }
    presentShapes = new HashMap<>();
    animationMoments = new HashMap<>();
    ticks = new ArrayList<>();
    ticks.add(new Moment(0));
  }


  private void isShapePresentAtTick(String name, int tick) {
    if (ticks.get(tick).presentShapes.get(name) == null) {
      throw new IllegalArgumentException("Shape not present at tick");
    }
  }

  private void checkValidTicks(int initialTick, int endTick) {
    if (initialTick >= endTick || initialTick <= 0 || initialTick > ticks.size()) {
      throw new IllegalArgumentException("Invalid ticks");
    }
  }

  // makes sure the shape isn't instructed to remain put for the given duration
  private void checkForNothingActivity(String name, int initialTick, int endTick) {
    checkValidTicks(initialTick, endTick);

    for (int i = initialTick; i < ticks.size(); i++) {
      if (ticks.get(i).presentShapes.get(name) != null) {
        if (ticks.get(i).presentShapes.get(name).motionsExperienced.contains(MotionType.STILL)) {
          throw new IllegalArgumentException("Cannot perform motion when shape has been "
              + "instructed to stay put");
        }
      }
    }
  }

  // makes sure that the given motion type isn't being made in the duration
  private void checkValidMotion(String name, int initialTick, int endTick) {
    checkValidTicks(initialTick, endTick);
    isShapePresentAtTick(name, initialTick);
    int checkTick = initialTick + 1;
    checkForNothingActivity(name, checkTick, endTick);
  }

  private void checkMotionTypeActivity(String name, int initialTick, MotionType motion) {
    for (int i = initialTick; i < ticks.size(); i++) {
      if (ticks.get(i).presentShapes.get(name) != null
          && ticks.get(i).presentShapes.get(name)
          .motionsExperienced.contains(motion)) {
        throw new IllegalArgumentException("Motions cannot overlap");
      }
    }
  }

  private void updateMoments(String name, int initialTick, int endTick) {
    animationMoments.get(name).add(initialTick);
    animationMoments.get(name).add(endTick);
  }

  private Shape getSelectedShape(AnimatedShape backup, int tick) {
    if (tick >= ticks.size() || ticks.get(tick).presentShapes.get(backup.name) == null) {
      selectedShape = ticks.get(tick - 1).presentShapes.get(backup.name);

      return selectedShape.shape.copy();
    } else {
      return null;
    }
  }

  private Map<String, Double> getVelocityVector(
      Position initialPosition, Position newPosition, int initialTick, int endTick) {

    double distanceTravelledX = newPosition.getX() - initialPosition.getX();
    double distanceTravelledY = newPosition.getY() - initialPosition.getY();

    double distanceTravelled = Math.sqrt((Math.pow(distanceTravelledX, 2))
        + (Math.pow(distanceTravelledY, 2)));

    double angle = Math.atan(distanceTravelledY / distanceTravelledX);

    double speed = distanceTravelled / (endTick - initialTick);

    double xVelocity = Math.cos(angle) * speed;
    double yVelocity = Math.sin(angle) * speed;

    if (distanceTravelledX < 0) {
      xVelocity *= -1;
    }
    if (distanceTravelledY < 0) {
      yVelocity *= -1;
    }

    Map<String, Double> vector = new HashMap<>();
    vector.put("x", xVelocity);
    vector.put("y", yVelocity);

    return vector;
  }

  private void addNewTick(AnimatedShape newAnimatedShape, int tick) {
    if (tick >= ticks.size()) {
      // If there are no more ticks, create a new moment with thew new AnimatedShape
      // and add to the list of ticks
      Moment selectedMoment = new Moment(tick);
      selectedMoment.addAnimatedShape(newAnimatedShape);
      ticks.add(selectedMoment);
    } else {
      // We know there is a tick, with just without this shape, so we just add a new AnimatedShape
      ticks.get(tick).addAnimatedShape(newAnimatedShape);
    }
  }

  @Override
  public void generateShape(String name, Shape newShape, Position position, int tick) {
    if (presentShapes.containsKey(name)) {
      throw new IllegalArgumentException("Shape already placed");
    } else if (tick <= 0 || tick > ticks.size()) {
      throw new IllegalArgumentException("Tick does not exist");
    } else if (presentShapes.isEmpty() && tick != 1) {
      throw new IllegalArgumentException("No shape has been made yet. Please place a shape at"
          + "beginning of animation");
    }

    if (tick != 1) {
      ticks.get(tick).addAnimatedShape(
          new AnimatedShape(name, newShape, position));
    } else {
      Moment selectedMoment = new Moment(1);
      selectedMoment.addAnimatedShape(
          new AnimatedShape(name, newShape, position));
      ticks.add(selectedMoment);
    }

    ArrayList<Integer> generateMarkers = new ArrayList<>();
    animationMoments.put(name, generateMarkers);

    presentShapes.put(name, newShape);
  }

  @Override
  public void doNothing(String name, int initialTick, int endTick) {
    checkValidTicks(initialTick, endTick);
    isShapePresentAtTick(name, initialTick);

    AnimatedShape selectedShape;

    // goes through all the ticks and makes sure no motion is occurring in between ticks
    for (int i = initialTick + 1; i < ticks.size(); i++) {
      selectedShape = ticks.get(i).presentShapes.get(name);
      if (selectedShape != null) {
        if (!selectedShape.motionsExperienced.isEmpty()) {
          throw new IllegalArgumentException("Can't override motion when trying to do nothing");
        }
      }
    }

    // updates the start tick to be still
    selectedShape = ticks.get(initialTick).presentShapes.get(name);
    selectedShape.addMotionType(MotionType.STILL);

    // goes through the ticks in between and copies the shape at initial tick all the way through
    // the end tick
    for (int i = initialTick; i <= endTick; i++) {
      try {
        ticks.get(i).addAnimatedShape(selectedShape);
      } catch (IndexOutOfBoundsException e) {
        Moment newMoment = new Moment(i);
        newMoment.addAnimatedShape(selectedShape);
        ticks.add(newMoment);
      }
    }

    updateMoments(name, initialTick, endTick);
  }

  @Override
  public void moveShape(String name, Position newPosition, int initialTick, int endTick) {
    checkValidMotion(name, initialTick, endTick);

    checkMotionTypeActivity(name, initialTick, MotionType.MOVE);

    Position initialPosition = ticks.get(initialTick).presentShapes.get(name).position;

    Map<String, Double> velocityVector =
        getVelocityVector(initialPosition, newPosition, initialTick, endTick);

    double xVelocity = velocityVector.get("x");
    double yVelocity = velocityVector.get("y");

    for (int i = initialTick + 1; i <= endTick; i++) {
      selectedShape = ticks.get(i - 1).presentShapes.get(name);
      Position currentPosition = selectedShape.position;

      Position endPosition =
          new Position((currentPosition.getX() + xVelocity),
              (currentPosition.getY() + yVelocity));

      Shape currentShape = getSelectedShape(selectedShape, i);

      if (currentShape == null) {
        // Will try to update the already existing animated shape with the new position
        selectedShape = ticks.get(i).presentShapes.get(name);
        selectedShape.position = endPosition;
        selectedShape.addMotionType(MotionType.MOVE);
      } else {
        AnimatedShape newAnimatedShape =
            new AnimatedShape(name, currentShape, endPosition, MotionType.MOVE);

        addNewTick(newAnimatedShape, i);

      }
    }

    updateMoments(name, initialTick, endTick);
  }

  @Override
  public void changeShapeColor(String name, Color newColor, int initialTick, int endTick) {
    checkValidMotion(name, initialTick, endTick);

    checkMotionTypeActivity(name, initialTick, MotionType.RECOLOR);

    selectedShape = ticks.get(initialTick).presentShapes.get(name);
    selectedShape.addMotionType(MotionType.RECOLOR);
    Color initialColor = selectedShape.shape.getColor();

    double redValueDeltaRate = (newColor.getColorValues().get("red")
        - initialColor.getColorValues().get("red")) / (endTick - initialTick);

    double greenValueDeltaRate = (newColor.getColorValues().get("green")
        - initialColor.getColorValues().get("green")) / (endTick - initialTick);

    double blueValueDeltaRate = (newColor.getColorValues().get("blue")
        - initialColor.getColorValues().get("blue")) / (endTick - initialTick);

    for (int i = initialTick + 1; i <= endTick; i++) {

      selectedShape = ticks.get(i - 1).presentShapes.get(name);
      Position currentPosition = selectedShape.position;
      Shape createdShape = getSelectedShape(selectedShape, i);

      initialColor = selectedShape.shape.getColor();
      Color endColor =
          new Color(initialColor.getColorValues().get("red") + redValueDeltaRate,
              initialColor.getColorValues().get("green") + greenValueDeltaRate,
              initialColor.getColorValues().get("blue") + blueValueDeltaRate);

      if (createdShape == null) {
        // Will try to update the already existing animated shape with the new position
        selectedShape = ticks.get(i).presentShapes.get(name);
        selectedShape.shape.changeColor(endColor);

        selectedShape.addMotionType(MotionType.RECOLOR);
      } else {
        createdShape.changeColor(endColor);
        AnimatedShape newAnimatedShape =
            new AnimatedShape(name, createdShape, currentPosition, MotionType.RECOLOR);

        addNewTick(newAnimatedShape, i);
      }
    }

    updateMoments(name, initialTick, endTick);

  }

  @Override
  public void resizeShape(String name, int width, int height, int initialTick, int endTick) {
    checkValidMotion(name, initialTick, endTick);

    checkMotionTypeActivity(name, initialTick, MotionType.RESIZE);

    selectedShape = ticks.get(initialTick).presentShapes.get(name);
    selectedShape.addMotionType(MotionType.RESIZE);
    Map<String, Double> initialDimension = selectedShape.shape.getSize();

    double widthGrowth = (width - initialDimension.get("width")) / (endTick - initialTick);
    double heightGrowth = (height - initialDimension.get("height")) / (endTick - initialTick);

    for (int i = initialTick + 1; i <= endTick; i++) {
      selectedShape = ticks.get(i - 1).presentShapes.get(name);
      Position currentPosition = selectedShape.position;
      Shape createdShape = getSelectedShape(selectedShape, i);

      Map<String, Double> currentDimensions = selectedShape.shape.getSize();

      double newWidth = (currentDimensions.get("width") + widthGrowth);
      double newHeight = (currentDimensions.get("height") + heightGrowth);

      if (createdShape == null) {
        // Will try to resize the already existing Shape
        selectedShape = ticks.get(i).presentShapes.get(name);
        selectedShape.shape.resize(newWidth, newHeight);

        selectedShape.addMotionType(MotionType.RESIZE);
      } else {
        createdShape.resize(newWidth, newHeight);
        AnimatedShape newAnimatedShape =
            new AnimatedShape(name, createdShape, currentPosition, MotionType.RESIZE);

        addNewTick(newAnimatedShape, i);
      }
    }

    updateMoments(name, initialTick, endTick);

  }

  @Override
  public ArrayList<Moment> getAnimation() {
    return this.ticks;
  }

  @Override
  public Moment getSingleTick(int tick) {
    return ticks.get(tick);
  }

  @Override
  public Position getShapePosition(String name, int tick) {
    isShapePresentAtTick(name, tick);
    return ticks.get(tick).presentShapes.get(name).position;
  }

  @Override
  public Color getShapeColor(String name, int tick) {
    isShapePresentAtTick(name, tick);

    return ticks.get(tick).presentShapes.get(name).shape.getColor();
  }

  @Override
  public Map<String, Double> getShapeSize(String name, int tick) {
    isShapePresentAtTick(name, tick);

    return ticks.get(tick).presentShapes.get(name).shape.getSize();
  }

  @Override
  public String toString() {
    StringBuilder animationLog = new StringBuilder();
    Map<String, StringBuilder> shapeMoments = new HashMap<>();

    for (Map.Entry<String, ArrayList<Integer>> animationKeyframes
        : animationMoments.entrySet()) {
      String shapeName = animationKeyframes.getKey();
      ArrayList<Integer> shapeKeyframes = animationKeyframes.getValue();

      for (int i = 1; i < shapeKeyframes.size(); i += 2) {
        int startTick = shapeKeyframes.get(i - 1);
        int endTick = shapeKeyframes.get(i);

        Moment startMoment = ticks.get(startTick);
        Moment endMoment = ticks.get(endTick);

        AnimatedShape startShapeValue = startMoment.presentShapes.get(shapeName);
        AnimatedShape endShapeValue = endMoment.presentShapes.get(shapeName);

        StringBuilder shapeLog;
        if (!shapeMoments.containsKey(shapeName)) {
          shapeLog = new StringBuilder();
          shapeMoments.put(shapeName, shapeLog);
        }

        shapeLog = shapeMoments.get(shapeName);
        shapeLog.append("motion  ").append(startShapeValue.name).append("  ").append(startTick)
            .append("  ").append(startShapeValue.toString()).append("\t|\t");

        shapeLog.append(endTick).append("  ").append(endShapeValue.toString()).append("\n");
      }
    }

    for (Map.Entry<String, StringBuilder> shapeMoment
        : shapeMoments.entrySet()) {
      animationLog.append(shapeMoment.getValue().toString());
    }

    return animationLog.toString();
  }
}
