package cs3500.excellence.hw05.animation;


import java.util.HashMap;
import java.util.Map;

class Moment {

  Map<String, AnimatedShape> presentShapes;
  int tick;

  Moment(int tick) {
    this.presentShapes = new HashMap<>();
    this.tick = tick;
  }

  void addAnimatedShape(AnimatedShape m) {
    presentShapes.put(m.name, m);
  }

  Map<String, AnimatedShape> returnMoment() {
    Map<String, AnimatedShape> allShapes = new HashMap<>();
    allShapes.putAll(this.presentShapes);
    return allShapes;
  }
}
