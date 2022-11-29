package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * This is the Portfolio interface which contains the function signatures for
 * all the functionalities that a user can use regarding a inflexible portfolio.
 */
public interface Portfolio {

  /**
   * Method that creates an XML document that stores
   * all the data related to the portfolio.
   */
  void createPortfolio(List<Stocks> stocks, String portfolioName);

  /**
   * Method that retrieves portfolio data from files and returns
   * it in a hashmap.
   *
   * @param portfolioName the file name from which it needs
   *                      to retrieve the data.
   */
  HashMap<String, Double> examinePortfolio(String portfolioName) throws IOException;

  /**
   * Method that gets the total value of a portfolio on a particular date.
   *
   * @param portfolioName the portfolio to get total value
   * @param date          the date at which it gets the total
   *                      value of the portfolio.
   */
  Double getTotalValue(String portfolioName, String date) throws IOException;
}
