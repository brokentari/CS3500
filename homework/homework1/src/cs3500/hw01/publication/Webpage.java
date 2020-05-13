package cs3500.hw01.publication;

/**
 * Represents bibliographic information for websites.
 */
public class Webpage implements Publication {

  private final String title;
  private final String url;
  private final String retrieved;

  /**
   * Constructs an website.
   *
   * @param title     the title of the website
   * @param url       the url of the website
   * @param retrieved date the website was visited/retrieved
   * @throws IllegalArgumentException if any of the arguments are null
   */
  public Webpage(String title, String url, String retrieved) {
    if (title == null || url == null || retrieved == null) {
      throw new IllegalArgumentException("Cannot pass null arguments");
    }

    this.title = title;
    this.url = url;
    this.retrieved = retrieved;
  }

  @Override
  public String citeApa() {
    return title + ". Retrieved " + retrieved + ", from " + url + ".";
  }

  @Override
  public String citeMla() {
    return "\"" + title + ".\" Web. " + retrieved + " <" + url + ">.";
  }
}
