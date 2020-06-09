package cs3500.excellence.hw05.model;

import cs3500.animator.util.AnimationBuilder;
import cs3500.excellence.hw05.shape.Ellipse;
import cs3500.excellence.hw05.shape.ModelColor;
import cs3500.excellence.hw05.shape.ModelPosition;
import cs3500.excellence.hw05.shape.ModelShape;
import cs3500.excellence.hw05.shape.Rectangle;
import cs3500.excellence.hw05.view.IViewModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An implementation of {@link ExcellenceAnimator} that supports the placing, moving, resizing, and
 * recoloring of multiple {@link ModelShape}s.
 */
public class ExcellenceAnimatorImpl implements ExcellenceAnimator, IViewModel {

  private final Map<String, ModelShape> presentShapes;
  private final Map<String, ArrayList<Integer>> animationMoments;
  private final ArrayList<Moment> ticks;
  protected int canvasWidth, canvasHeight;
  protected int canvasX, canvasY;
  protected double maximumX, maximumY;
  private AnimatedShape selectedShape;


  /**
   * A constructor that has {@code animationSpeed} as its constructor.
   */
  public ExcellenceAnimatorImpl() {
    presentShapes = new LinkedHashMap<>();
    animationMoments = new LinkedHashMap<>();
    ticks = new ArrayList<>();
    ticks.add(new Moment(0));
  }

  private void isShapePresentAtTick(String name, int tick) {
    if (ticks.get(tick).presentShapes.get(name) == null) {
      throw new IllegalArgumentException("Shape not present at tick");
    }
  }

  private void checkValidTicks(int initialTick, int endTick) {
    if (initialTick > endTick) {
      throw new IllegalArgumentException(initialTick + " is bigger than " + endTick);
    } else if (initialTick <= 0) {
      throw new IllegalArgumentException(initialTick + " cannot be less than zero");
    } else if (initialTick >= ticks.size()) {
      throw new IllegalArgumentException(initialTick + " cannot be larger than the size of the"
          + "animation: " + ticks.size());
    }
  }

  // makes sure the shape isn't instructed to remain put for the given duration
  private void checkForNothingActivity(String name, int initialTick, int endTick) {

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
    int checkTick = initialTick + 1;
    checkForNothingActivity(name, checkTick, endTick);
  }

  private void checkMotionTypeActivity(String name, int initialTick, MotionType motion) {
    for (int i = initialTick + 1; i < ticks.size(); i++) {
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

  private ModelShape getSelectedShape(AnimatedShape backup, int tick) {
    if (tick >= ticks.size() || ticks.get(tick).presentShapes.get(backup.name) == null) {
      selectedShape = ticks.get(tick - 1).presentShapes.get(backup.name);

      return selectedShape.modelShape.copy();
    } else {
      return null;
    }
  }

  private double intermediateValue(double initialValue, double endValue,
      double currentTick, double initialTick, double endTick) {
    double initialValueFactor = (endTick - currentTick) / (endTick - initialTick);
    double endValueFactor = (currentTick - initialTick) / (endTick - initialTick);

    return (initialValue * initialValueFactor) + (endValue * endValueFactor);
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
  public void generateShape(String name, ModelShape newModelShape, ModelPosition modelPosition,
      int tick) {
    if (tick <= 0 || tick > ticks.size()) {
      throw new IllegalArgumentException("Tick does not exist");
    } else if (presentShapes.isEmpty() && tick != 1) {
      throw new IllegalArgumentException("No shape has been made yet. Please place a shape at"
          + "beginning of animation");
    }

    if (tick < ticks.size()) {
      ticks.get(tick).addAnimatedShape(
          new AnimatedShape(name, newModelShape, modelPosition));
    } else {
      Moment selectedMoment = new Moment(1);
      selectedMoment.addAnimatedShape(
          new AnimatedShape(name, newModelShape, modelPosition));
      ticks.add(selectedMoment);
    }

    ArrayList<Integer> generateMarkers = new ArrayList<>();
    animationMoments.put(name, generateMarkers);

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


  }

  @Override
  public void moveShape(String name, ModelPosition newModelPosition, int initialTick, int endTick) {
    checkValidMotion(name, initialTick, endTick);
    checkMotionTypeActivity(name, initialTick, MotionType.MOVE);

    maximumX = newModelPosition.getX() > maximumX ? newModelPosition.getX() : maximumX;

    maximumY = newModelPosition.getY() > maximumY ? newModelPosition.getY() : maximumY;


    ModelPosition initialModelPosition = ticks.get(initialTick).presentShapes
        .get(name).modelPosition;

    for (int i = initialTick + 1; i <= endTick; i++) {
      selectedShape = ticks.get(i - 1).presentShapes.get(name);

      double newXPosition = intermediateValue(initialModelPosition.getX(),
          newModelPosition.getX(), i, initialTick, endTick);
      double newYPosition = intermediateValue(initialModelPosition.getY(),
          newModelPosition.getY(), i, initialTick, endTick);

      ModelPosition endModelPosition =
          new ModelPosition(newXPosition, newYPosition);

      ModelShape currentModelShape = getSelectedShape(selectedShape, i);

      if (currentModelShape == null) {
        // Will try to update the already existing animated shape with the new position
        selectedShape = ticks.get(i).presentShapes.get(name);
        selectedShape.modelPosition = endModelPosition;
        selectedShape.addMotionType(MotionType.MOVE);
      } else {
        AnimatedShape newAnimatedShape =
            new AnimatedShape(name, currentModelShape, endModelPosition, MotionType.MOVE);

        addNewTick(newAnimatedShape, i);

      }
    }


  }

  @Override
  public void changeShapeColor(String name, ModelColor newModelColor, int initialTick,
      int endTick) {
    checkValidMotion(name, initialTick, endTick);

    checkMotionTypeActivity(name, initialTick, MotionType.RECOLOR);

    selectedShape = ticks.get(initialTick).presentShapes.get(name);
    selectedShape.addMotionType(MotionType.RECOLOR);
    ModelColor initialModelColor = selectedShape.modelShape.getModelColor();

    for (int i = initialTick + 1; i <= endTick; i++) {

      selectedShape = ticks.get(i - 1).presentShapes.get(name);
      ModelPosition currentModelPosition = selectedShape.modelPosition;
      ModelShape createdModelShape = getSelectedShape(selectedShape, i);

      double newRedValue = intermediateValue(initialModelColor.getColorValues().get("red"),
          newModelColor.getColorValues().get("red"), i, initialTick, endTick);
      double newGreenValue = intermediateValue(initialModelColor.getColorValues().get("green"),
          newModelColor.getColorValues().get("green"), i, initialTick, endTick);
      double newBlueValue = intermediateValue(initialModelColor.getColorValues().get("blue"),
          newModelColor.getColorValues().get("blue"), i, initialTick, endTick);

      ModelColor endModelColor = new ModelColor(newRedValue, newGreenValue, newBlueValue);

      if (createdModelShape == null) {
        // Will try to update the already existing animated shape with the new position
        selectedShape = ticks.get(i).presentShapes.get(name);
        selectedShape.modelShape.changeColor(endModelColor);

        selectedShape.addMotionType(MotionType.RECOLOR);
      } else {
        createdModelShape.changeColor(endModelColor);
        AnimatedShape newAnimatedShape =
            new AnimatedShape(name, createdModelShape, currentModelPosition, MotionType.RECOLOR);

        addNewTick(newAnimatedShape, i);
      }
    }


  }

  @Override
  public void resizeShape(String name, int width, int height, int initialTick, int endTick) {
    checkValidMotion(name, initialTick, endTick);

    checkMotionTypeActivity(name, initialTick, MotionType.RESIZE);

    selectedShape = ticks.get(initialTick).presentShapes.get(name);
    selectedShape.addMotionType(MotionType.RESIZE);

    double initialWidth = selectedShape.modelShape.getSize().get("width");
    double initialHeight = selectedShape.modelShape.getSize().get("height");

    for (int i = initialTick + 1; i <= endTick; i++) {
      selectedShape = ticks.get(i - 1).presentShapes.get(name);
      ModelShape createdModelShape = getSelectedShape(selectedShape, i);

      double newWidth = intermediateValue(initialWidth, width, i, initialTick, endTick);
      double newHeight = intermediateValue(initialHeight, height, i, initialTick, endTick);

      if (createdModelShape == null) {
        // Will try to resize the already existing Shape
        selectedShape = ticks.get(i).presentShapes.get(name);
        selectedShape.modelShape.resize(newWidth, newHeight);

        selectedShape.addMotionType(MotionType.RESIZE);
      } else {
        ModelPosition currentModelPosition = selectedShape.modelPosition;
        createdModelShape.resize(newWidth, newHeight);
        AnimatedShape newAnimatedShape =
            new AnimatedShape(name, createdModelShape, currentModelPosition, MotionType.RESIZE);

        addNewTick(newAnimatedShape, i);
      }
    }


  }

  @Override
  public ArrayList<Moment> getAnimation() {
    return ticks;
  }

  @Override
  public Map<String, Integer> getCanvasDimensions() {
    Map<String, Integer> dimensions = new HashMap<>();
    dimensions.put("width", this.canvasWidth);
    dimensions.put("height", this.canvasHeight);

    return dimensions;
  }

  @Override
  public Map<String, Integer> getCanvasOffset() {
    Map<String, Integer> dimensions = new HashMap<>();
    dimensions.put("xOffset", this.canvasX);
    dimensions.put("yOffset", this.canvasY);

    return dimensions;
  }

  @Override
  public int getMaximumX() {
    return (int) this.maximumX;
  }

  @Override
  public int getMaximumY() {
    return (int) this.maximumY;
  }

  @Override
  public ModelPosition getShapePosition(String name, int tick) {
    isShapePresentAtTick(name, tick);
    return ticks.get(tick).presentShapes.get(name).modelPosition;
  }

  @Override
  public ModelColor getShapeColor(String name, int tick) {
    isShapePresentAtTick(name, tick);

    return ticks.get(tick).presentShapes.get(name).modelShape.getModelColor();
  }

  @Override
  public Map<String, Double> getShapeSize(String name, int tick) {
    isShapePresentAtTick(name, tick);

    return ticks.get(tick).presentShapes.get(name).modelShape.getSize();
  }

  @Override
  public String toString() {
    StringBuilder animationLog = new StringBuilder();
    Map<String, StringBuilder> shapeMoments = new LinkedHashMap<>();

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

  public IViewModel returnViewModel() {
    return this;
  }

  public static final class Builder implements AnimationBuilder<ExcellenceAnimator> {

    ExcellenceAnimatorImpl currentAnimation;

    public Builder() {
      this.currentAnimation = new ExcellenceAnimatorImpl();
    }

    @Override
    public ExcellenceAnimator build() {
      return currentAnimation;
    }

    @Override
    public AnimationBuilder<ExcellenceAnimator> setBounds(int x, int y, int width, int height) {
      currentAnimation.canvasHeight = height;
      currentAnimation.canvasWidth = width;
      currentAnimation.canvasX = x;
      currentAnimation.canvasY = y;

      return this;
    }

    @Override
    public AnimationBuilder<ExcellenceAnimator> declareShape(String name, String type) {
      ModelShape newModelShape;
      switch (type) {
        case "rectangle":
          newModelShape = new Rectangle(5, 5, new ModelColor(0, 0, 0));
          break;
        case "ellipse":
          newModelShape = new Ellipse(5, 5, new ModelColor(0, 0, 0));
          break;
        default:
          throw new IllegalArgumentException("unknown shape");
      }

      currentAnimation.presentShapes.put(name, newModelShape);
      return this;
    }

    @Override
    public AnimationBuilder<ExcellenceAnimator> addMotion(String name, int t1, int x1, int y1,
        int w1, int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2,
        int g2, int b2) {

      if (t1 >= currentAnimation.ticks.size() || !currentAnimation.ticks.get(t1).presentShapes
          .containsKey(name)
          || t1 == t2) {
        ModelColor oldModelColor = new ModelColor(r1, g1, b1);
        ModelPosition oldModelPosition = new ModelPosition(x1, y1);
        currentAnimation.presentShapes.get(name).resize(w1, h1);
        currentAnimation.presentShapes.get(name).changeColor(oldModelColor);

        currentAnimation.generateShape(name, currentAnimation.presentShapes.get(name),
            oldModelPosition, t1);
      }

      ModelColor newModelColor = new ModelColor(r2, g2, b2);
      ModelPosition newModelPosition = new ModelPosition(x2, y2);

      currentAnimation.moveShape(name, newModelPosition, t1, t2);
      currentAnimation.changeShapeColor(name, newModelColor, t1, t2);
      currentAnimation.resizeShape(name, w2, h2, t1, t2);

      currentAnimation.updateMoments(name, t1, t2);

      return this;
    }

    @Override
    public AnimationBuilder<ExcellenceAnimator> addKeyframe(String name, int t, int x, int y, int w,
        int h, int r, int g, int b) {
      return this;
    }
  }
}
