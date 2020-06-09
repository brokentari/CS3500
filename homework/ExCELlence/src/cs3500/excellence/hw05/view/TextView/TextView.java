package cs3500.excellence.hw05.view.TextView;

import cs3500.excellence.hw05.model.AnimatedShape;
import cs3500.excellence.hw05.view.IView;
import cs3500.excellence.hw05.view.IViewModel;
import java.util.ArrayList;

public class TextView implements IView {

  IViewModel model;
  int speed;

  public TextView(IViewModel model, int speed) {
    this.model = model;
    this.speed = speed;
  }

  @Override
  public void makeVisible() {
    String log = model.toString();
    System.out.print(log);
  }

  @Override
  public void refresh() {

  }

  @Override
  public void setShapes(ArrayList<AnimatedShape> as) {

  }
}
