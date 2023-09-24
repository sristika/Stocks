package modal;

import java.util.ArrayList;
import java.util.HashMap;

import setup.interfaces.SetUpStockData;

/**
 * Interface constructs methods to perform operations on Flexible Portfolio.
 */
public interface FlexiblePortfolioModal {

  /**
   * This method is used to upload the csv for a portfolio.
   *
   * @param fileName        represents the filename to be uploaded
   * @param path            represents the path of the portfolio
   * @param stockDataObject represents the stockdata object
   * @return the csv for a portfolio
   */
  boolean uploadCsv(String fileName, String path, SetUpStockData stockDataObject);

  /**
   * This method is used to take the ticker values from the user.
   *
   * @param fileName        represents the filename to be uploaded
   * @param tickerValues    represents the tickervalues for the portfolio
   * @param stockDataObject represents the stockdata object
   * @return the ticker values from the user
   */
  boolean enterTickerValues(String fileName, String tickerValues, SetUpStockData stockDataObject);

  /**
   * This method is used to retrieve the composition of the portfolio.
   *
   * @param fileName        represents the filename to be uploaded
   * @param stockDataObject represents the stockdata object
   * @param date            represents the date which needs to be entered to get a specific
   *                        portfolio
   * @return the composition of the portfolio
   */
  String compositionOfPortfolio(String fileName, SetUpStockData stockDataObject, String date);

  /**
   * This method represents the value of the portfolio.
   *
   * @param fileName        represents the filename to be uploaded
   * @param stockDataObject represents the stockdata object
   * @param date            represents the date which needs to be entered to get a specific
   *                        portfolio
   * @return value of the portfolio
   */
  String valueOfPortfolio(String fileName, SetUpStockData stockDataObject, String date);

  /**
   * This method retrieves the potrfolio of the user.
   *
   * @param fileName        represents the filename to be uploaded
   * @param stockDataObject represents the stockdata object
   * @return the potrfolio of the user.
   */
  boolean getPortfolio(String fileName, SetUpStockData stockDataObject);

  /**
   * This method is used to check if the filename entered by the user is right.
   *
   * @param fileName represents the filename to be uploaded
   * @return filename entered by the user is right.
   */
  boolean checkFileName(String fileName);

  /**
   * This method is  used to sell the shares of a specific portfolio.
   *
   * @param fileName        represents the filename to be uploaded
   * @param tickerValues    represents the tickervalues for the portfolio
   * @param stockDataObject represents the stockdata object
   * @param date            represents the date which needs to be entered to get a specific
   *                        portfolio
   * @return the shares of a specific portfolio.
   */
  boolean sellShares(String fileName, String tickerValues,
                     SetUpStockData stockDataObject, String date);

  /**
   * This method is used to retrieve the value of portfolio on specificdate.
   *
   * @param fileName        represents the filename to be uploaded
   * @param stockDataObject represents the stockdata object
   * @param date            represents the date which needs to be entered to get a specific
   *                        portfolio
   * @return the value of portfolio on specificdate.
   */
  String valueOfPortfolioOnSpecificDate(String fileName,
                                        SetUpStockData stockDataObject, String date);

  /**
   * This method represents the costbasis of portfolio.
   *
   * @param fileName        represents the filename to be uploaded
   * @param stockDataObject represents the stockdata object
   * @param date            represents the date which needs to be entered to get a specific
   *                        portfolio
   * @return the costbasis of portfolio.
   */
  String costBasisOfPortfolio(String fileName,
                              SetUpStockData stockDataObject, String date);

  /**
   * This method represents the performance chart of the portfolio.
   *
   * @param fileName        represents the filename to be uploaded
   * @param stockDataObject represents the stockdata object
   * @param fDate           represents the start date of portfolio
   * @param tDate           represents the end date of portfolio
   * @param datesString     represents the date which needs to be entered to get a specific
   *                        portfolio
   * @return the performance chart of the portfolio
   */
  String performanceChart(String fileName, SetUpStockData stockDataObject,
                          String fDate, String tDate, String datesString);

  /**
   * represents the portfolio to be read by the user.
   *
   * @param path represents the portfolio path
   * @return the portfolio
   */
  String readPortfolio(String path);

  /**
   * This method re-balances the composition of a portfolio on a particular date
   * based on the total amount weightage of stocks entered by the user.
   *
   * @param fileName        name of the portfolio.
   * @param date            date to re-balance on.
   * @param stockWeightages desired total amount weightage of every stock in the portfolio.
   * @param composition     composition of the portfolio on that date.
   * @param stockDataObject stock data object to perform this operation.
   */

  void balancePortfolioModel(String fileName, String date, HashMap<String, Integer> stockWeightages,
                             ArrayList<StockNode> composition, SetUpStockData stockDataObject);

  /**
   * Fetches the composition of a portfolio on a particular date and
   * returns it as StockNode array list.
   *
   * @param fileName        name of the portfolio.
   * @param date            date on which the composition needs to be fetched
   * @param stockDataObject stock data object to perform this operation.
   * @return the composition of portfolio as a StockNode list.
   */
  ArrayList<StockNode> fetchCompositionList(String fileName, String date,
                                            SetUpStockData stockDataObject);

}
