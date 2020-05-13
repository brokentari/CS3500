package cs3500.temperature;

abstract class AbstractTemperature implements Temperature {

  double temperature;
  double conversion;

  @Override
  public double inKelvin() {
    return this.inCelsius() - ABS_ZERO_C;
  }

  /**
   * Constructs a {@link Temperature} in a manner selected by each subclass of this class.
   *
   * @param inKelvin the temperature in degrees Kelvin
   * @return the new {@code Duration}
   */
  protected abstract Temperature fromKelvin(double inKelvin);

  @Override
  public int compareTo(Temperature o) {
    return (int) (this.inKelvin() - o.inKelvin());
  }

  @Override
  public int hashCode() {
    return Double.hashCode(this.inKelvin());
  }

  @Override
  public Temperature average(Temperature other) {
    return fromKelvin((this.inKelvin() + other.inKelvin()) / 2);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Temperature)) {
      return false;
    }

    return Math.abs(((Temperature) obj).inKelvin() - this.inKelvin()) < 0.01;
  }
}
