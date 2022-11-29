package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * The ReadFromAlphaVantage is an Utility class that can be used from anywhere in
 * the program to get data from the alphavantage api in the path provided.
 * The methods are kept public to allow access from all classes in the
 * project.
 */
public class ReadFromAlphaVantage extends AbsGetDataFromSrc {

  private URL url;

  /**
   * The ReadFromAlphaVantage constructor initializes the api url.
   */
  public ReadFromAlphaVantage(String stockSymbol, String timeSeries) {

    if ("TIME_SERIES_DAILY".equals(timeSeries)) {
      try {
        String apiKey = "KFKR6IY1AUVCVWVV";
        url = new URL("https://www.alphavantage"
                + ".co/query?function=TIME_SERIES_DAILY"
                + "&outputsize=full"
                + "&symbol"
                + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
      } catch (MalformedURLException e) {
        throw new RuntimeException("the alphavantage API has either changed or "
                + "no longer works");
      }
    }
  }

  @Override
  public String[] getDataByDate(String date) throws IOException {
    BufferedReader in = new BufferedReader(
            new InputStreamReader(url.openStream()));
    return absGetDataByDate(in, date);
  }
}
