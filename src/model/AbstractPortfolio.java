package model;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utility.WorkWithXML;

/**
 * This is the abstract class that contains all the common functionalities of
 * both types of portfolios - flexible and inflexible.
 */
abstract class AbstractPortfolio {

  /**
   * This is the abstraction of the createPortfolio function of both the types of portfolios.
   */
  public ArrayList<HashMap<String, String>> createPortfolioAbs(List<Stocks> stocks,
                                                               String date, float commission) {
    ArrayList<HashMap<String, String>> stocksList = new ArrayList<>();
    for (Stocks stock : stocks) {
      HashMap<String, String> stockMap = new HashMap<>();
      stockMap.put("Date", date);
      stockMap.put("Stock-ticker", stock.getTicker());
      stockMap.put("Number-of-shares", String.valueOf(stock.getNumberOfShares()));

      if (commission != -1) {
        stockMap.put("Commission", String.valueOf(commission));
      }
      stocksList.add(stockMap);
    }

    return stocksList;
  }

  /**
   * This is the abstraction of the examinePortfolio function of both the types of portfolios.
   */
  public HashMap<String, Double> examinePortfolioAbs(String portfolioName, String path)
          throws IOException {
    WorkWithXML p = new WorkWithXML(path, portfolioName);
    List<HashMap<String, String>> allStocksData = p.read();
    HashMap<String, Double> allTickerQuant = new HashMap<>();

    for (HashMap<String, String> s : allStocksData) {
      if (allTickerQuant.containsKey(s.get("Stock ticker"))) {
        allTickerQuant.put(s.get("Stock ticker"), allTickerQuant.get(s.get("Stock ticker"))
                + Double.parseDouble(s.get("Number of shares")));
      } else {
        allTickerQuant.put(s.get("Stock ticker"), Double.parseDouble(s.get("Number of shares")));
      }
    }
    return allTickerQuant;
  }

}
