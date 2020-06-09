package cs3500.excellence.hw05.shape;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * A representation of a color using the RGB (Red, Green, Blue) color model.
 */
public class ModelColor {

  protected double redValue;
  protected double greenValue;
  protected double blueValue;

  /**
   * Constructor for a {@link ModelColor} that takes all the required values for the RGB model.
   *
   * @param redValue   the red value (0-255)
   * @param greenValue the green value (0-255)
   * @param blueValue  the blue value (0-255)
   * @throws IllegalArgumentException if any of the color values are either less than 0 or greater
   *                                  than 255
   */
  public ModelColor(double redValue, double greenValue, double blueValue) {
    if (redValue < -1 || greenValue < -1 || blueValue < -1
        || redValue > 256 || greenValue > 256 || blueValue > 256) {
      throw new IllegalArgumentException("Color is outside of RBG range");
    }

    this.redValue = redValue;
    this.greenValue = greenValue;
    this.blueValue = blueValue;
  }

  public Map<String, Double> getColorValues() {
    Map<String, Double> colorValues = new HashMap<>();
    colorValues.put("red", this.redValue);
    colorValues.put("blue", this.blueValue);
    colorValues.put("green", this.greenValue);

    return colorValues;
  }

  /**
   * Returns a formatted string with all of the color's RGB values
   *
   * @return a string with the format <i>redValue  greenValue  blueValue</i>
   */
  @Override
  public String toString() {
    DecimalFormat df = new DecimalFormat("#.##");
    return String.format("%d  %d  %d", (int) redValue, (int) greenValue, (int) blueValue);
  }

  @Override
  public int hashCode() {
    return Double.hashCode(this.redValue * this.blueValue * this.greenValue);
  }

  @Override
  public boolean equals(Object obj) {
    // null check
    if (obj == null) {
      return false;
    }

    // this instance check
    if (this == obj) {
      return true;
    }

    // instanceof Check and actual value check
    if (obj instanceof ModelColor) {
      ModelColor compare = (ModelColor) obj;
      return compare.redValue == this.redValue
          && compare.greenValue == this.greenValue
          && compare.blueValue == this.blueValue;
    } else {
      return false;
    }
  }
}
