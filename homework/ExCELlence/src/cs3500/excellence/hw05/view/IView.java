package cs3500.excellence.hw05.view;

import cs3500.excellence.hw05.model.AnimatedShape;
import java.util.ArrayList;

public interface IView {

  /**
   * Make the view visible. This is usually called after the view is constructed
   */
  void makeVisible();

  /**
   * Signal the view to draw itself
   */
  void refresh();

  void setShapes(ArrayList<AnimatedShape> as);
}
