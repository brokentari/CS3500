package cs3500.temperature;

/**
 * Temperatures represented by degrees Celsius, with a minimum value of -546.3°C.
 */
public class CelsiusTemperature extends AbstractTemperature {

  /**
   * Constructs a temperature in terms of its given Celsius temperature.
   *
   * @param temperature the temperature in degrees Celsius
   * @throws IllegalArgumentException if the argument is less than -546.3°C
   */
  public CelsiusTemperature(double temperature) {
    if (temperature + 273.1 < ABS_ZERO_C) {
      throw new IllegalArgumentException("given temperature cannot be lower than absolute zero");
    }

    this.temperature = temperature;
  }

  /**
   * Constructs a temperature in terms of its given Fahrenheit temperature.
   *
   * @param temperature  the temperature in degrees Fahrenheit
   * @param inFahrenheit if the given temperature is in degrees Fahrenheit
   * @throws IllegalArgumentException if {@code inFahrenheit} is false
   */
  public CelsiusTemperature(double temperature, boolean inFahrenheit) {
    this(Temperature.fahrenheitToCelsius(temperature));

    if (!inFahrenheit) {
      throw new IllegalArgumentException("inCelsius flag cannot be false");
    }
  }

  @Override
  public double inCelsius() {
    return temperature;
  }

  @Override
  public double inFahrenheit() {
    return Temperature.celsiusToFahrenheit(this.temperature);
  }

  @Override
  public String toString() {
    return String.format("%.1f", temperature) + "° Celsius";
  }

  @Override
  protected Temperature fromKelvin(double inKelvin) {
    return new CelsiusTemperature(inKelvin - 273.15);
  }
}
