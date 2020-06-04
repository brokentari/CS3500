import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.excellence.hw05.Color;
import cs3500.excellence.hw05.Position;
import cs3500.excellence.hw05.animation.ExcellenceAnimator;
import cs3500.excellence.hw05.animation.ExcellenceAnimatorImpl;
import cs3500.excellence.hw05.shape.Ellipse;
import cs3500.excellence.hw05.shape.Rectangle;
import cs3500.excellence.hw05.shape.Shape;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;


public class AnimatorTests {

  ExcellenceAnimator animator;
  Shape rectangle;
  Shape ellipse;
  Color BLACK = new Color(0, 0, 0);
  Color RED = new Color(255, 0, 0);
  Color WHITE = new Color(255, 255, 255);

  @Before
  public void init() {
    animator = new ExcellenceAnimatorImpl();
    rectangle = new Rectangle(50, 100, BLACK);
    ellipse = new Ellipse(120, 60, RED);
    animator.generateShape("R", rectangle, new Position(200, 200), 1);
  }

  @Test
  public void testConstructor() {
    ExcellenceAnimator animator = new ExcellenceAnimatorImpl();

    assertNotEquals(null, animator);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDoubleShapeGeneration() {
    init();

    animator.generateShape("R", rectangle, new Position(50, 50), 1);
  }

  @Test
  public void testValidShapeMove() {
    init();

    animator.moveShape("R", new Position(100, 100), 1, 10);

    assertEquals("motion  R  1  200  200  50  100  0  0  0\t|\t10  100  100  50  100  0  0  0\n",
        animator.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveOverlap() {
    init();

    animator.moveShape("R", new Position(100, 100), 1, 10);
    animator.moveShape("R", new Position(300, 300), 5, 10);
  }

  @Test
  public void testValidRecolor() {
    init();

    animator.changeShapeColor("R", WHITE, 1, 10);
    assertEquals(
        "motion  R  1  200  200  50  100  0  0  0\t|\t10  200  200  50  100  255  255  255\n",
        animator.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRecolorOverlap() {
    init();

    animator.changeShapeColor("R", WHITE, 1, 10);
    animator.changeShapeColor("R", RED, 2, 5);
  }

  @Test
  public void testValidResize() {
    init();

    animator.resizeShape("R", 25, 100, 1, 20);

    assertEquals("motion  R  1  200  200  50  100  0  0  0\t|\t20  200  200  24  100  0  0  0\n",
        animator.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testResizeOverlap() {
    init();

    animator.resizeShape("R", 25, 200, 1, 20);
    animator.resizeShape("R", 75, 200, 5, 10);
  }

  @Test
  public void testShapeGenerationInBetweenAnimation() {
    init();

    animator.moveShape("R", new Position(100, 100), 1, 10);

    animator.generateShape("C", ellipse, new Position(440, 70), 6);
    animator.moveShape("C", new Position(500, 500), 6, 20);

    assertEquals("motion  R  1  200  200  50  100  0  0  0\t|\t10  100  100  50  100  0  0  0\n"
            + "motion  C  6  440  70  120  60  255  0  0\t|\t20  500  500  120  60  255  0  0\n",
        animator.toString());
  }

  @Test
  public void testMoveAndResizeOverlap() {
    init();

    animator.moveShape("R", new Position(100, 100), 1, 10);
    animator.resizeShape("R", 400, 400, 4, 9);

    assertEquals("motion  R  1  200  200  50  100  0  0  0\t|\t10  100  100  50  100  0  0  0\n"
            + "motion  R  4  167  167  50  100  0  0  0\t|\t9  111  111  400  400  0  0  0\n",
        animator.toString());
  }

  @Test
  public void testMoveAndRecolorOverlap() {
    init();

    animator.moveShape("R", new Position(100, 100), 1, 10);
    animator.changeShapeColor("R", RED, 4, 6);

    assertEquals("motion  R  1  200  200  50  100  0  0  0\t|\t10  100  100  50  100  0  0  0\n"
            + "motion  R  4  167  167  50  100  0  0  0\t|\t6  144  144  50  100  255  0  0\n",
        animator.toString());
  }

  @Test
  public void testDoNothing() {
    init();
    animator.doNothing("R", 1, 5);

    assertEquals("motion  R  1  200  200  50  100  0  0  0\t|\t5  200  200  50  100  0  0  0\n",
        animator.toString());
  }

  @Test
  public void testGetShapePosition() {
    init();

    Position testPosition = animator.getShapePosition("R", 1);

    assertEquals(new Position(200, 200), testPosition);
  }

  @Test
  public void testGetShapeColor() {
    init();

    assertEquals(BLACK, animator.getShapeColor("R", 1));
  }

  @Test
  public void testGetShapeSize() {
    init();

    Map<String, Double> dimensions = new HashMap<>();
    dimensions.put("width", 50.0);
    dimensions.put("height", 100.0);

    assertEquals(dimensions, animator.getShapeSize("R", 1));
  }

  @Test
  public void testAssignmentSampleOutput() {
    init();

    animator.doNothing("R", 1, 10);
    animator.moveShape("R", new Position(300, 300), 10, 50);
    animator.doNothing("R", 50, 51);
    animator.resizeShape("R", 25, 100, 51, 70);
    animator.moveShape("R", new Position(200, 200), 70, 100);

    animator.generateShape("C", ellipse, new Position(440, 70), 6);
    animator.doNothing("C", 6, 20);
    animator.moveShape("C", new Position(440, 250), 20, 50);
    animator.changeShapeColor("C",
        new Color(0, 170, 85), 50, 78);

    assertEquals("motion  R  1  200  200  50  100  0  0  0\t|\t10  200  200  50  100  0  0  0\n"
            + "motion  R  10  200  200  50  100  0  0  0\t|\t50  300  300  50  100  0  0  0\n"
            + "motion  R  50  300  300  50  100  0  0  0\t|\t51  300  300  50  100  0  0  0\n"
            + "motion  R  51  300  300  50  100  0  0  0\t|\t70  300  300  24  100  0  0  0\n"
            + "motion  R  70  300  300  24  100  0  0  0\t|\t100  200  200  24  100  0  0  0\n"
            + "motion  C  6  440  70  120  60  255  0  0\t|\t20  440  70  120  60  255  0  0\n"
            + "motion  C  20  440  70  120  60  255  0  0\t|\t50  440  250  120  60  255  0  0\n"
            + "motion  C  50  440  250  120  60  255  0  0\t|\t78  440  250  120  60  0  170  85\n",
        animator.toString());
  }

}
