package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import enums.stockTicker;

/**
 * This is the Portfolio interface for flexible portfolios which contains the function signatures
 * for all the functionalities that a user can use for a flexible portfolio.
 */
public interface PortfolioFlexibleInterface {

  /**
   * Method that handles the creation of a flexible portfolio in the inflexiblePortfolios folder.
   *
   * @param portfolioName the file name which is created.
   * @param stocks        all data of stocks that the portfolio is created with.
   */
  void createPortfolio(List<Stocks> stocks, String portfolioName, String creationDate);

  /**
   * Method that retrieves portfolio data from files and returns
   * it in a hashmap.
   *
   * @param portfolioName the file name from which it needs
   *                      to retrieve the data.
   */
  HashMap<String, Double> examinePortfolio(String portfolioName) throws IOException;

  /**
   * Method that handles the purchasing of a particular stock of a portfolio.
   *
   * @param portfolioName the portfolio to sell from
   * @param stocks        the data of all the stocks to purchase
   */
  boolean purchaseStocks(List<HashMap<String, String>> stocks, String portfolioName);

  /**
   * Method that handles the selling of particular stock of a portfolio.
   *
   * @param portfolioName the portfolio to sell from
   * @param tick          the ticker value of the stock to sell
   * @param numOfShares   the number of shares to sell
   * @param date          the date on which the selling should occur
   */
  boolean sellStocks(stockTicker tick, int numOfShares, String date, String portfolioName);

  /**
   * Method that gets the cost basis of a portfolio on a particular date. The cost
   * basis gives the total amount spent on the portfolio till that day.
   *
   * @param portfolioName the portfolio to get total value
   * @param date          the date at which it gets the cost basis of the portfolio.
   */
  double findCostBasis(String date, String portfolioName) throws IOException;

  /**
   * Method that gets the total value of a portfolio on a particular date.
   *
   * @param portfolioName the portfolio to get total value
   * @param date          the date at which it gets the total
   *                      value of the portfolio.
   */
  Double getTotalValue(String portfolioName, String date) throws IOException;

  /**
   * Method that changes commission value in the commission file.
   *
   * @param c the new commission value
   */
  boolean changeCommission(float c);

  /**
   * Method that gets the current commission value.
   */
  float getCommissionFromFile();

  /**
   * Method that receives the control from controller in the model when the user wants
   * to know the performance of a portfolio.
   *
   * @param startDate     the date from which the user wants the data
   * @param endDate       the date till which the user wants the data
   * @param portfolioName the name of the portfolio to get performance of
   */
  StringBuilder getPerformanceChart(String startDate, String endDate, String portfolioName)
          throws IOException;

  void setPortfolioName(String s);

  void createStrategy(int total, HashMap<String, Integer> proportions, String start,
                      String end, int days, String name);

  void updateStrategy() throws IOException;


}
