package cs3500.hw01.publication;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * Test class for Webpage: unit tests to ensure that Webpage can be cited correctly and otherwise
 * behave correctly.
 */
public class WebpageTest {

  Publication buzzfeed = new Webpage("The 25 Most Epic Cat Beards Of All Time",
      "http://www.buzzfeed.com/summeranne/"
          + "the-25-most-epic-cat-beards-of-all-time",
      "Fri, 15 May 2015 08:15:49 -0400");

  Publication facebook = new Webpage("Facebook", "facebook.com", "Monday");

  @Test(expected = IllegalArgumentException.class)
  public void testNullWebpage() {
    new Webpage("Google", null, "Fri, 15 May 2015 08:15:49 -0400");
  }

  @Test
  public void testCiteApa() {
    assertEquals("The 25 Most Epic Cat Beards Of All Time. "
        + "Retrieved Fri, 15 May 2015 08:15:49 -0400, from http://www.buzzfeed.com/summeranne/"
        + "the-25-most-epic-cat-beards-of-all-time.", buzzfeed.citeApa());
    assertEquals("Facebook. Retrieved Monday, from facebook.com.", facebook.citeApa());
  }

  @Test
  public void testCiteMla() {
    assertEquals("\"The 25 Most Epic Cat Beards Of All Time.\" Web. "
        + "Fri, 15 May 2015 08:15:49 -0400 <http://www.buzzfeed.com/summeranne/"
        + "the-25-most-epic-cat-beards-of-all-time>.", buzzfeed.citeMla());
    assertEquals("\"Facebook.\" Web. Monday <facebook.com>.", facebook.citeMla());
  }
}
