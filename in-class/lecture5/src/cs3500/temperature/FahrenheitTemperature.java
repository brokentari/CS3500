package cs3500.temperature;

/**
 * Temperatures represented by degrees Fahrenheit, with a minimum value of -951.34°F.
 */
public class FahrenheitTemperature extends AbstractTemperature {

  /**
   * Constructs a temperature in terms of its given Fahrenheit temperature.
   *
   * @param temperature the temperature in degrees Fahrenheit
   * @throws IllegalArgumentException if the argument is less than -951.34°F
   */
  public FahrenheitTemperature(double temperature) {
    if (((temperature - 32) * 5 / 9) + 273.15 < ABS_ZERO_C) {
      throw new IllegalArgumentException("given temperature cannot be lower than absolute zero");
    }

    this.temperature = temperature;
  }

  /**
   * Constructs a temperature in terms of its given Celsius temperature.
   *
   * @param temperature the temperature in degrees Celsius
   * @param inCelsius   if the given temperature is in degrees Celsius
   * @throws IllegalArgumentException if {@code inCelsius} is false
   */
  public FahrenheitTemperature(double temperature, boolean inCelsius) {
    this(Temperature.celsiusToFahrenheit(temperature));

    if (!inCelsius) {
      throw new IllegalArgumentException("inCelsius flag cannot be false");
    }
  }

  @Override
  public double inCelsius() {
    return Temperature.fahrenheitToCelsius(this.temperature);
  }

  @Override
  public double inFahrenheit() {
    return this.temperature;
  }

  @Override
  public String toString() {
    return String.format("%.1f", temperature) + "° Fahrenheit";
  }

  @Override
  protected Temperature fromKelvin(double inKelvin) {
    return new FahrenheitTemperature(
        Temperature.celsiusToFahrenheit(inKelvin - 273.15));
  }
}
