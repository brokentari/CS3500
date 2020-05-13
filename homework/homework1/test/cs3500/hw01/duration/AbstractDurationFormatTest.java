package cs3500.hw01.duration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for the format method of {@link Duration}s. Add your tests to this class to assure that
 * your format method works properly
 */
public abstract class AbstractDurationFormatTest {

  @Test
  public void hmsTemplate() {
    assertEquals("4 hours, 0 minutes, and 9 seconds",
        hms(4, 0, 9)
            .format("%h hours, %m minutes, and %s seconds"));
  }

  @Test
  public void HMSTemplate() {
    assertEquals("4:05:17",
        hms(4, 5, 17).format("%h:%M:%S"));
  }

  // ADD MORE TESTS HERE. 
  // THE ABOVE TEST NAMES ARE MERE PLACEHOLDERS. NAME YOUR TESTS MEANINGFULLY. 
  // IF YOU NAME YOUR TESTS SIMILAR TO ABOVE, YOU WILL LOSE POINTS!
  // Your tests must only use hms(...) and sec(...) to construct new Durations
  // and must *not* directly say "new CompactDuration(...)" or
  // "new HmsDuration(...)"


  @Test
  public void testStackedPercentageSymbols() {
    assertEquals("%20", sec(20).format("%%%s"));
    assertEquals("%t", sec(20).format("%%t"));
  }

  @Test
  public void testBackToBackCodes() {
    assertEquals("202020", hms(20, 20, 20).format("%h%m%s"));
  }

  @Test
  public void testMultipleEqualCodes() {
    assertEquals("12341234", sec(1234).format("%t%t"));
    assertEquals("010101", hms(1, 2, 3).format("%H%H%H"));
  }

  @Test
  public void testCodeTVsCodeS() {
    assertEquals("300", sec(300).format("%t"));
    assertEquals("0", sec(300).format("%s"));
    assertEquals("4064", hms(0, 0, 4064).format("%t"));
    assertEquals("44", hms(0, 0, 4064).format("%s"));
  }

  @Test
  public void testOnlyPercentages() {
    assertEquals("%%%", sec(100).format("%%%%%%"));
  }

  @Test
  public void testWhitespaceTemplate() {
    assertEquals("   ", sec(1000).format("   "));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSinglePercentage() {
    sec(2500).format("%");
  }

  @Test
  public void testEmptyTemplate() {
    assertEquals("", hms(10, 10, 10).format(""));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullTemplates() {
    sec(300).format(null);
  }

  @Test
  public void testMaxDuration() {
    assertEquals("2", hms(1, 60, 2).format("%h"));
    assertEquals("56", hms(0, 55, 60).format("%m"));
  }
  /*
    Leave this section alone: It contains two abstract methods to
    create Durations, and concrete implementations of this testing class
    will supply particular implementations of Duration to be used within 
    your tests.
   */

  /**
   * Constructs an instance of the class under test representing the duration given in hours,
   * minutes, and seconds.
   *
   * @param hours   the hours in the duration
   * @param minutes the minutes in the duration
   * @param seconds the seconds in the duration
   * @return an instance of the class under test
   */
  protected abstract Duration hms(int hours, int minutes, int seconds);

  /**
   * Constructs an instance of the class under test representing the duration given in seconds.
   *
   * @param inSeconds the total seconds in the duration
   * @return an instance of the class under test
   */
  protected abstract Duration sec(long inSeconds);

  /**
   * Concrete class for testing HmsDuration implementation of Duration.
   */
  public static final class HmsDurationTest extends AbstractDurationFormatTest {

    @Override
    protected Duration hms(int hours, int minutes, int seconds) {
      return new HmsDuration(hours, minutes, seconds);
    }

    @Override
    protected Duration sec(long inSeconds) {
      return new HmsDuration(inSeconds);
    }
  }

  /**
   * Concrete class for testing CompactDuration implementation of Duration.
   */
  public static final class CompactDurationTest extends AbstractDurationFormatTest {

    @Override
    protected Duration hms(int hours, int minutes, int seconds) {
      return new CompactDuration(hours, minutes, seconds);
    }

    @Override
    protected Duration sec(long inSeconds) {
      return new CompactDuration(inSeconds);
    }
  }
}
