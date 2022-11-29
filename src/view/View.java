package view;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * Interface that handles the view of the program.
 * Displays the options available and the results to the user.
 */
public interface View {
  /**
   * Method that displays the main menu to the client.
   */
  String showInflexiblePortfolioMenu();

  /**
   * Method that shows the options of stocks available in this program.
   */
  String showStockOptions();

  /**
   * Method to intimate user to enter the integer amount
   * of shares to be bought.
   */
  String showNumberOfSharesMessage();

  /**
   * Displays the contents of a portfolio.
   * Portfolio data is received as a list of string array.
   * Each String array contains the following information on its indices
   * [0] - Name of the stock.
   * [1] - Number of shares purchased.
   * [2] - Value of a share when purchased.
   * [3] - Date when share was purchased.
   *
   * @param stocks list of stocks contained in the portfolio.
   */
  String showPortfolio(HashMap<String, Double> stocks);

  /**
   * Method to intimate user to enter portfolio name
   * which is to be scanned for further operations.
   */
  String inputPortfolioName();

  /**
   * Method to display the total value of a portfolio
   * on a particular date to the user.
   */
  String showTotalValue(String portfolioName, String date, Double totalValue);

  /**
   * Method that intimates the user to display a valid date
   * between the two LocalDate values provided.
   *
   * @param dateToday        most recent local date user can provide.
   * @param lastHistoricDate oldest date user can provide.
   */
  String showDateMessage(LocalDate dateToday,
                         LocalDate lastHistoricDate);

  /**
   * Method that intimates the user that a portfolio has been
   * successfully created.
   */
  String createSuccessfulMessage();

  /**
   * Method that intimates the user when a portfolio
   * could not be created.
   */
  String createUnsuccessfulMessage();

  /**
   * Method that takes an error message as input from the controller
   * and displays it in the view component.
   *
   * @param error the error message to be displayed.
   */
  String displayErrorMessage(String error);

  /**
   * Method that displays the different Portfolio types a user
   * can work with in this program.
   *
   * @return the options of type of portfolios supported.
   */
  String showPortfolioOptions();

  /**
   * Method that displays the Portfolio menu options of flexible
   * portfolio.
   *
   * @return the menu options for flexible portfolio
   */
  String showExistingFlexiblePortfolioMenu();

  /**
   * Method that displays a generic date message indicating the date format accepted.
   *
   * @return the generic date message
   */
  String showGenericDateMessage();

  /**
   * Method that shows the performance chart to the user.
   *
   * @return the performance chart
   */
  String showPerformanceChart(StringBuilder performanceChart);

  /**
   * Method that displays that the transation was successful.
   *
   * @return the success message
   */
  String showSuccessfulTransaction(String transaction);

  /**
   * Method that displays that the transation was unsuccessful.
   *
   * @return the failure message
   */
  String showUnsuccessfulTransaction(String transaction);

  /**
   * Method that displays that the commission was changed.
   */
  String showChangeCommissionMessage();

  /**
   * Method that displays any generic message.
   */
  String showGenericMessage(String message);

  /**
   * Method that concatenates two strings.
   */
  String concatenateStrings(String first, String second);

  String showMenu();
}
