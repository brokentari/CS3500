package cs3500.excellence.hw05.view.AnimationView;

import cs3500.excellence.hw05.model.AnimatedShape;
import cs3500.excellence.hw05.model.Moment;
import cs3500.excellence.hw05.view.IView;
import cs3500.excellence.hw05.view.IViewModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.Timer;


public class VisualAnimationView extends JFrame implements IView, ActionListener {

  private final Timer timer;
  private final AnimationPanel animationPanel;
  private final JScrollPane scrollPane;
  private final int totalFrames;
  private int currentFrame = 1;
  private final IViewModel model;
  private final ArrayList<Moment> frames;


  public VisualAnimationView(IViewModel model, int ticksPerSecond) {
    super();
    setTitle("Animation");
    setPreferredSize(new Dimension(500, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.model = model;
    this.totalFrames = model.getAnimation().size();
    frames = model.getAnimation();

    int canvasWidth = model.getCanvasDimensions().get("width");
    int canvasHeight = model.getCanvasDimensions().get("height");

    this.setPreferredSize(new Dimension(canvasWidth, canvasHeight));

    animationPanel = new AnimationPanel();

    int animationMaxX = model.getMaximumX();
    int animationMaxY = model.getMaximumY();

    animationPanel.setPreferredSize(new Dimension((int) (animationMaxX * 1.5),
        (int) (animationMaxY * 1.5)));

    animationPanel.setLocation(-model.getCanvasOffset().get("xOffset"),
        -model.getCanvasOffset().get("yOffset"));



    timer = new Timer(1000 / ticksPerSecond, this);
    timer.setInitialDelay(0);

    scrollPane = new JScrollPane(animationPanel);

    this.add(scrollPane);

    animationPanel.revalidate();

    timer.start();

    this.pack();
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void setShapes(ArrayList<AnimatedShape> as) {
    this.animationPanel.setShapes(as);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (currentFrame >= totalFrames) {
      currentFrame = 0;
    }

    this.setShapes(frames.get(currentFrame).getPresentShapes());

    refresh();

    currentFrame++;
  }
}
