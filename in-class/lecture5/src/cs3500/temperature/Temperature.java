package cs3500.temperature;

/**
 * Represents a temperature.
 */
public interface Temperature extends Comparable<Temperature> {

  /**
   * Absolute zero, in degrees Celsius. For our purposes, no temperature can be below this value.
   */
  double ABS_ZERO_C = -273.15f;

  /**
   * Compute and return the conversion of the given temperature in degrees Celsius to Fahrenheit.
   *
   * @param temperature the given temperature (assumed to be in Fahrenheit)
   * @return the degrees Celsius equivalent of the temperature
   */
  static double fahrenheitToCelsius(double temperature) {
    return (temperature - 32) * 5 / 9;
  }

  /**
   * Compute and return the conversion of the given temperature in degrees Fahrenheit to Celsius.
   *
   * @param temperature the given temperature (assumed to be in Celsius)
   * @return the degrees Fahrenheit equivalent of the temperature
   */
  static double celsiusToFahrenheit(double temperature) {
    return temperature * 9 / 5 + 32;
  }

  /**
   * The temperature in degrees Celsius.
   *
   * @return the temperature in degrees Celsius
   */
  double inCelsius();

  /**
   * The temperature in degrees Fahrenheit.
   *
   * @return the temperature in degrees Fahrenheit
   */
  double inFahrenheit();

  /**
   * The temperature in degrees Kelvin.
   *
   * @return the temperature in degrees Kelvin
   */
  double inKelvin();

  /**
   * Compute and return the average of the two temperatures.
   *
   * @return the average temperature
   */
  Temperature average(Temperature t);
}
