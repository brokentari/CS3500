package cs3500.excellence.hw05.model;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Moment {
  private final int tick;
  protected Map<String, AnimatedShape> presentShapes;

  Moment(int tick) {
    this.presentShapes = new LinkedHashMap<>();
    this.tick = tick;
  }

  protected void addAnimatedShape(AnimatedShape m) {
    presentShapes.put(m.name, m);
  }


  public ArrayList<AnimatedShape> getPresentShapes() {
    ArrayList<AnimatedShape> presentShapeNames = new ArrayList<>();

    for (Map.Entry<String, AnimatedShape> entry : presentShapes.entrySet()) {
      presentShapeNames.add(entry.getValue().copy());
    }

    return presentShapeNames;
  }
}
