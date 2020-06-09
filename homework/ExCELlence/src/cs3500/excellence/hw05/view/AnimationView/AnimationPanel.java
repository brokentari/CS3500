package cs3500.excellence.hw05.view.AnimationView;

import cs3500.excellence.hw05.model.AnimatedShape;
import cs3500.excellence.hw05.shape.Ellipse;
import cs3500.excellence.hw05.shape.ModelPosition;
import cs3500.excellence.hw05.shape.ModelShape;
import cs3500.excellence.hw05.shape.Rectangle;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class AnimationPanel extends JPanel {

  private ArrayList<AnimatedShape> shapes;


  public AnimationPanel() {
    super();
    shapes = new ArrayList<>();
  }


  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(Color.BLACK);

    for (AnimatedShape as : this.shapes) {
      ModelShape modelShape = as.getModelShape();
      Map<String, Double> modelShapeModelColorValues = modelShape.getModelColor()
          .getColorValues();
      g2d.setColor(new Color(modelShapeModelColorValues.get("red").intValue(),
          modelShapeModelColorValues.get("green").intValue(),
          modelShapeModelColorValues.get("blue").intValue()));

      ModelPosition modelPosition = as.getModelPosition();
      Map<String, Double> modelShapeDimension = modelShape.getSize();

      if (modelShape instanceof Rectangle) {
        g2d.fillRect((int) modelPosition.getX(), (int) modelPosition.getY(),
            modelShapeDimension.get("width").intValue(),
            modelShapeDimension.get("height").intValue());
      } else if (modelShape instanceof Ellipse) {
        g2d.fillOval((int) modelPosition.getX(), (int) modelPosition.getY(),
            modelShapeDimension.get("width").intValue(),
            modelShapeDimension.get("height").intValue());
      }
    }
  }

  public void setShapes(ArrayList<AnimatedShape> as) {
    this.shapes = as;
  }
}
