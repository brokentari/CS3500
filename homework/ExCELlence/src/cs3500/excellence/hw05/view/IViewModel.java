package cs3500.excellence.hw05.view;

import cs3500.excellence.hw05.model.Moment;
import java.util.ArrayList;
import java.util.Map;

public interface IViewModel {

  ArrayList<Moment> getAnimation();

  Map<String, Integer> getCanvasDimensions();

  Map<String, Integer> getCanvasOffset();

  String toString();

  int getMaximumX();

  int getMaximumY();
}
