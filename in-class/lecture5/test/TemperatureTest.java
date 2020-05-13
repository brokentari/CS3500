import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cs3500.temperature.CelsiusTemperature;
import cs3500.temperature.FahrenheitTemperature;
import cs3500.temperature.Temperature;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for Temperature, both Celsius and Fahrenheit representations.
 */
public class TemperatureTest {

  private Temperature cTemp;
  private Temperature fTemp;
  private Temperature fTemp2;
  private Temperature cTemp2;

  @Before
  public void init() {
    cTemp = new CelsiusTemperature(100);
    fTemp = new FahrenheitTemperature(100, true);
    fTemp2 = new FahrenheitTemperature(32);
    cTemp2 = new CelsiusTemperature(32, true);
  }

  @Test
  public void testAverage() {
    assertEquals(124.7, cTemp.average(new CelsiusTemperature(3)).inFahrenheit(), 0.001);
    assertEquals(100, cTemp.average(cTemp).inCelsius(), 0.001);
    assertEquals(32, fTemp2.average(cTemp2).inFahrenheit(), 0.001);
    assertEquals(0, cTemp2.average(fTemp2).inCelsius(), 0.001);
  }

  @Test
  public void testValidConstructors() {
    assertTrue(cTemp instanceof CelsiusTemperature);
    assertTrue(fTemp instanceof FahrenheitTemperature);
    assertTrue(cTemp2 instanceof CelsiusTemperature);
    assertTrue(fTemp2 instanceof FahrenheitTemperature);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCelsiusTemperature() {
    new CelsiusTemperature(-1000);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCelsiusTemperature2() {
    new CelsiusTemperature(-1000, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCelsiusFalseArgument() {
    new CelsiusTemperature(0, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFahrenheitTemperature() {
    new FahrenheitTemperature(-1000);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFahrenheitTemperature2() {
    new FahrenheitTemperature(-1000, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFahrenheitFalseArgument() {
    new FahrenheitTemperature(0, false);
  }

  @Test
  public void testConversions() {
    assertEquals(100, fTemp.inCelsius(), 0.01);
    assertEquals(273.15, fTemp2.inKelvin(), 0.01);
  }

  @Test
  public void testToString() {
    assertEquals("100.0° Celsius", cTemp.toString());
    assertEquals("212.0° Fahrenheit", fTemp.toString());
  }

  @Test
  public void testCompareToEqual() {
    assertEquals(0, fTemp.compareTo(fTemp));
    assertEquals(0, fTemp2.compareTo(cTemp2));
    assertTrue(cTemp2.compareTo(fTemp) < 0);
    assertTrue(fTemp.compareTo(cTemp2) > 0);
  }

  @Test
  public void testHashCodeEquals() {
    Temperature f = new FahrenheitTemperature(212);
    assertEquals(f, cTemp);
    assertEquals(f.hashCode(), cTemp.hashCode());
  }

  @Test
  public void testObservers() {
    assertEquals(100, cTemp.inCelsius(), 0.001);
    assertEquals(212, cTemp.inFahrenheit(), 0.001);
    assertEquals(373.15, fTemp.inKelvin(), 0.001);
  }

  @Test
  public void testObservers2() {
    assertEquals(0, fTemp2.inCelsius(), 0.001);
    assertEquals(32, cTemp2.inFahrenheit(), 0.001);
  }

}
