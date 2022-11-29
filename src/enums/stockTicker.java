package enums;

/**
 * This enum represents the names and ticker of all stocks that are supported
 * by this application.
 */
public enum stockTicker {
  MSFT("Microsoft"),
  GOOGL("Google"),
  AAPL("Apple"),
  IBM("IBM"),
  MS("Morgan Stanley"),
  AMZN("Amazon.com Inc"),
  BAC("Bank Of America Corp"),
  DASH("DoorDash Inc - Class A"),
  ETSY("Etsy Inc"),
  JPM("JPMorgan Chase & Company"),
  MDB("MongoDB Inc - Class A"),
  NFLX("Netflix Inc"),
  USAU("U.S. Gold Corp"),
  VOD("Vodafone Group plc"),
  WBD("Warner Bros. Discovery Inc - Class A");

  private final String stockName;

  /**
   * A constructor that intializes the stockTicker string name.
   */
  stockTicker(String name) {
    this.stockName = name;
  }

  /**
   * A method to get the name of a stock.
   */
  public String getStockName() {
    return this.stockName;
  }
}
