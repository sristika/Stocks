package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import enums.stockTicker;
import utility.WorkWithFileTypes;
import utility.WorkWithXML;

/**
 * This class contains the implementation of a portfolio.
 * Performs create, view and getValue operations of a portfolio.
 */
public class PortfolioImpl extends AbstractPortfolio implements Portfolio {

  String absolutePath = System.getProperty("user.dir");
  String osSeperator = System.getProperty("file.separator");
  String finalPath = absolutePath + osSeperator + "allUserPortfolios" + osSeperator
          + "inflexiblePortfolios" + osSeperator;

  /**
   * Method to create a portfolio for an inflexible portfolio.
   *
   * @param stocks        List of stocks to be added.
   * @param portfolioName Name of the portfolio
   */
  public void createPortfolio(List<Stocks> stocks, String portfolioName) {
    String dateToday = "2022-11-02";
    ArrayList<HashMap<String, String>> stocksList = createPortfolioAbs(stocks,
            dateToday, -1);
    WorkWithXML p = new WorkWithXML(this.finalPath + portfolioName + ".xml", portfolioName);
    p.create(stocksList, dateToday);
  }

  /**
   * Method to examine the composition of a portfolio.
   *
   * @param portfolioName the file name from which it needs
   *                      to retrieve the data.
   * @return A hashmap of the portfolio with its stock and shares.
   * @throws IOException in case of I/O errors.
   */
  public HashMap<String, Double> examinePortfolio(String portfolioName) throws IOException {
    return examinePortfolioAbs(portfolioName, this.finalPath + portfolioName + ".xml");
  }

  /**
   * Method to get the total value of a portfolio on a specific date.
   *
   * @param portfolioName the portfolio to get total value
   * @param date          the date at which it gets the total
   *                      value of the portfolio.
   * @return the total value of the portfolio on the said date.
   * @throws IOException in case of I/O errors.
   */
  public Double getTotalValue(String portfolioName, String date) throws IOException {
    double totalValue = 0;
    WorkWithFileTypes p = new WorkWithXML(this.finalPath + portfolioName
            + ".xml", portfolioName);
    List<HashMap<String, String>> stockData = p.read();
    for (HashMap<String, String> s : stockData) {
      Stocks stock = new Stocks(stockTicker.valueOf(s.get("Stock ticker")),
              Integer.parseInt(s.get("Number of shares")));
      stock.fillStockData(date, false);
      totalValue += Double.parseDouble(stock.getValueOfShare()) * stock.getNumberOfShares();
    }
    return totalValue;
  }

}
