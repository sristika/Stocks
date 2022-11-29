package view;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import enums.stockTicker;

/**
 * This view class decides how the user sees outputs produced by the program.
 */
public class ViewImpl implements View {
  @Override
  public String showInflexiblePortfolioMenu() {
    String s = "\n  You can choose the following options for an inflexible portfolio: \n"
            + "1. Create an inflexible portfolio. \n"
            + "2. View composition of a portfolio. \n"
            + "3. Get total value of an inflexible portfolio on a specified date. \n"
            + "\n4. Exit from this menu. \n"
            + "\n"
            + "Choose an option number: \n";
    return s;
  }

  @Override
  public String showStockOptions() {
    int i = 1;
    StringBuilder s = new StringBuilder();
    s.append("\nType the stock TICKER\n");
    for (stockTicker st : stockTicker.values()) {
      s.append(st.getStockName()).
              append("( Ticker: ").
              append(st).
              append(") \n");
    }
    s.append("\nIf you have finished choosing stocks, perform action now. (Type Quit)\n");
    return s.toString();
  }


  @Override
  public String showNumberOfSharesMessage() {
    String s = "\nEnter the number of shares you want to buy of this stock: \n"
            + " (Note: You can only buy whole number amount of shares) \n";
    return s;
  }


  @Override
  public String showDateMessage(LocalDate dateToday,
                                LocalDate lastHistoricDate) {
    return ("Please enter a valid date in YYYY-MM-DD format between "
            + lastHistoricDate + " and " + dateToday + " (Must be a business day)\n");
  }

  @Override
  public String showPortfolio(HashMap<String, Double> stocks) {

    StringBuilder sb = new StringBuilder();
    sb.append("\nPortfolio contains the following stocks: \n");
    for (Map.Entry<String, Double> entry : stocks.entrySet()) {
      sb.append(entry.getKey() + " : " + entry.getValue() + "\n");
    }

    return sb.toString();
  }

  @Override
  public String inputPortfolioName() {
    return "\nEnter the name of portfolio: \n";
  }

  @Override
  public String showTotalValue(String portfolioName, String date, Double totalValue) {
    return "\nTotal value of portfolio " + portfolioName
            + " on " + date + " is: $" + totalValue + "\n";
  }


  @Override
  public String createSuccessfulMessage() {
    return "\nSuccessfully created portfolio.\n";
  }

  @Override
  public String createUnsuccessfulMessage() {
    return "\nNo stocks entered.\n";
  }


  @Override
  public String displayErrorMessage(String error) {

    return error;
  }

  @Override
  public String showPortfolioOptions() {
    return null;
  }

  @Override
  public String showExistingFlexiblePortfolioMenu() {
    String s = "\n\nYou can choose the following options for an flexible portfolio: \n"
            + "1. Buy shares in flexible portfolio. \n"
            + "2. Sell shares of flexible portfolio. \n"
            + "3. Create an investment strategy in this portfolio. \n"
            + "4. View composition of flexible portfolio. \n"
            + "5. Get total value of flexible portfolio on a specified date. \n"
            + "6. View cost basis of portfolio. \n"
            + "7. Change commission value. \n"
            + "8. Show performance chart.\n"
            + "\n9. Exit from this menu. \n"
            + "\n"
            + "Choose an option number: \n";
    return s;
  }

  public String showGenericDateMessage() {
    return "Please enter a valid date in YYYY-MM-DD format\n";
  }

  @Override
  public String showPerformanceChart(StringBuilder performanceChart) {
    return performanceChart.toString();
  }

  @Override
  public String showSuccessfulTransaction(String transaction) {
    return (transaction + " transaction was successful.\n");
  }

  @Override
  public String showUnsuccessfulTransaction(String transaction) {
    return (transaction + " transaction was unsuccessful.\n");
  }

  @Override
  public String showChangeCommissionMessage() {
    return "\n(Note: If you want to edit commission for this transaction, " +
            "type 'quit' and choose to change commission now from the Flexible portfolio menu.)\n";
  }

  @Override
  public String showGenericMessage(String message) {
    return message;
  }

  @Override
  public String concatenateStrings(String first, String second) {
    return first + second;
  }

  @Override
  public String showMenu() {
    return "\n Choose an operation you want to perform?\n" +
            "\n1. Create a new portfolio." +
            "\n2. Work with an existing portfolio." +
            "\n3. Exit from this menu." +
            "\n\n Choose a whole number choice from the options provided above.\n";
  }
}
