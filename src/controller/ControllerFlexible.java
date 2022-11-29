package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import enums.stockTicker;
import model.PortfolioFlexibleInterface;
import model.Stocks;
import utility.UtilityClass;
import view.View;
import view.ViewImpl;

/**
 * The controller bridges the gap between a model and view.
 * It delegates the task of showing output for the user to the view
 * and the working of the functionalities like(in this case) getting
 * total value, examining portfolio, getting cost basis, purchasing and selling stocks
 * and creating portfolios to the model.
 */
public class ControllerFlexible {
  private final Appendable out;
  Scanner scan;

  /**
   * This is the constructor of the ControllerFlexible class that initializes
   * the Readable, Appendable and Scanner object.
   */
  public ControllerFlexible(Readable in, Appendable out) {
    this.scan = new Scanner(in);
    this.out = out;
  }


  /**
   * Method that starts the operations of the Inflexible portfolio from the controller.
   * It asks the view to show the Main menu which
   * contains all the functionalities that are supported by this program and
   * then handles whatever the user replies according to different scenarios.
   */
  public void start(PortfolioFlexibleInterface portfolio) throws IOException {
    View view = new ViewImpl();
//    boolean run = true;
//    while (run) {
      out.append(view.showMenu());
      String stringMenuChoice = scan.next();
      while (!UtilityClass.checkValidNumberOption(stringMenuChoice, 1, 3)) {
        out.append(view.displayErrorMessage("Invalid entry. Please choose "
                + "a valid number to enter your choice.\n"));
        stringMenuChoice = scan.next();
      }
      int menuChoice = Integer.parseInt(stringMenuChoice);
      switch(menuChoice){
        case 1: {
          this.out.append(view.inputPortfolioName());
          String portfolioName = scan.next();
          if (UtilityClass.checkFileExists(portfolioName, true)) {
            out.append(view.displayErrorMessage("Portfolio with name "
                    + "already exists. Please choose another name."));
          } else {
            portfolio.setPortfolioName(portfolioName);
            out.append(view.showGenericMessage("\nEnter date of portfolio creation in" +
                    " YYYY-MM-DD: \n"));
            String creationDate = scan.next();
            while (!UtilityClass.checkDateFormat(creationDate) ||
                    UtilityClass.checkDateGreaterThanToday(creationDate)) {
              out.append(view.showGenericMessage("\n Invalid date enetered. " +
                      "Enter date in YYYY-MM-DD format, which is not after today"));
              creationDate = scan.next();
            }
            List<Stocks> stocks = createPortfolioController();
//            if (stocks.size() > 0) {
              portfolio.createPortfolio(stocks, portfolioName, creationDate);
              out.append(view.createSuccessfulMessage());
//            } else {
//              out.append(view.createUnsuccessfulMessage());
//            }

          }
          break;
        }

        case 2: {
          this.out.append(view.inputPortfolioName());
          String portfolioName = scan.next();
          while(!UtilityClass.checkFileExists(portfolioName, true)){
            out.append(view.displayErrorMessage("Portfolio with name "
                    + portfolioName + " doesn't exist. Enter portfolio name again.\n"));
            portfolioName = scan.next();
          }
          portfolio.setPortfolioName(portfolioName);
          portfolio.updateStrategy(); // To load strategy investments into the portfolio.
          boolean run = true;
          while(run){

            out.append(view.showExistingFlexiblePortfolioMenu());
            int choice;

            String stringChoice = scan.next();
            while (!UtilityClass.checkValidNumberOption(stringChoice, 1, 9)) {
              out.append(view.displayErrorMessage("Invalid entry. Please choose "
                      + "a number to enter your choice.\n"));
              stringChoice = scan.next();
            }
            choice = Integer.parseInt(stringChoice);
            switch (choice) {
              case 1: {
                purchaseSharesController(portfolio, portfolioName);
                break;
              }

              case 2: {
                sellSharesController(portfolio, portfolioName);
                break;
              }

              case 3 : {
                // Create investment strategy in this portfolio.
                createInvestmentStrategy(portfolio, portfolioName);
                break;
              }

              case 4: {
                  examinePortfolioController(portfolioName, portfolio);
                break;
              }

              case 5: {
                // Get total value of a portfolio

                getTotalValueFlexiblePortfolio(portfolio, portfolioName);
                break;
              }

              case 6: {
                // Find cost basis
                findCostBasis(portfolio, portfolioName);
                break;

              }

              case 7: {
                // View composition of a flexible portfolio.
                changeCommissionValue(portfolio);
                break;
              }

              case 8: {
                getPerformanceChart(portfolio, portfolioName);
                break;
              }

              case 9: {
                run = false;
                break;
              }


              default: {
                out.append(view.displayErrorMessage("Invalid entry. Please choose "
                        + "a number to enter your choice."));
              }
            }

          }

        }
      }


  }

  private List<Stocks> createPortfolioController() throws IllegalArgumentException, IOException {
    boolean run = true;
    List<Stocks> stocks = new ArrayList<>();
    HashMap<stockTicker, Integer> uniqueTickers = new HashMap<>();
    while (run) {
      View view = new ViewImpl();
      this.out.append(view.showStockOptions());
      this.out.append(view.showChangeCommissionMessage());
      stockTicker stockChoice;
      String stringStockChoice = scan.next();

      int choice = UtilityClass.checkValidStock(stringStockChoice, "Quit");
      int isNum = 0;
      String stringNumberOfShares = "";
      if (choice == 1) {
        out.append(view.showNumberOfSharesMessage());
        stringNumberOfShares = scan.next();
        isNum = UtilityClass.checkIfPositiveInteger(stringNumberOfShares, "Quit");
      }
      switch (choice) {
        case 1: {
          if (isNum == 1) {
            stockChoice = stockTicker.valueOf(stringStockChoice);
            getUniqTicks(uniqueTickers, stockChoice, Integer.parseInt(stringNumberOfShares));
          } else if (isNum == 2) {
            run = false;
          } else {
            out.append(view.displayErrorMessage("Please enter a valid integer value for number of "
                    + "stocks. Enter ticker again.\n"));
          }
          break;
        }
        case 2: {
          run = false;
          break;
        }
        case 0: {
          out.append(view.displayErrorMessage("Provided ticker is invalid\n"));
          break;
        }
        default: {
          out.append(view.displayErrorMessage("Invalid ticker.\n"));
        }
      }
    }
    for (Map.Entry<stockTicker, Integer> entry : uniqueTickers.entrySet()) {
      Stocks stock = new Stocks(entry.getKey(), entry.getValue());
      stocks.add(stock);
    }


    return stocks;
  }

  private void examinePortfolioController(String portfolioName,
                                          PortfolioFlexibleInterface portfolio) throws IOException {
    HashMap<String, Double> stocks = portfolio.examinePortfolio(portfolioName);

    View view = new ViewImpl();
    out.append(view.showPortfolio(stocks));
  }


  private void getUniqTicks(HashMap<stockTicker,
          Integer> u, stockTicker ticker, int numOfShares) {
    if (u.containsKey(ticker)) {
      int n = u.get(ticker);
      u.put(ticker, n + numOfShares);
    } else {
      u.put(ticker, numOfShares);
    }
  }

  private void purchaseSharesController(PortfolioFlexibleInterface portfolio, String portfolioName)
          throws IOException {

    boolean run = true;
    View view = new ViewImpl();
      List<HashMap<String, String>> stocksAppended = new ArrayList<>();
      while (run) {

        this.out.append(view.showStockOptions());
        this.out.append(view.showChangeCommissionMessage());
        String stringStockChoice = scan.next();

        int choice = UtilityClass.checkValidStock(stringStockChoice, "Quit");
        int isNum = 0;
        String stringNumberOfShares = "";
        if (choice == 1) {
          out.append(view.showNumberOfSharesMessage());
          stringNumberOfShares = scan.next();
          isNum = UtilityClass.checkIfPositiveInteger(stringNumberOfShares, "Quit");
        }
        switch (choice) {
          case 1: {
            if (isNum == 1) {
              out.append(view.showGenericDateMessage());
              String stringDate = scan.next();
              while (!UtilityClass.checkDateFormat(stringDate)) {
                out.append(view.showGenericDateMessage());
                stringDate = scan.next();
              }
              if (UtilityClass.checkDateChronology(stringStockChoice, stringDate,
                      portfolioName)) {
                if ((UtilityClass.checkDateBeforeCreatedDate(portfolioName, stringDate))) {
                  out.append("Cannot purchase share before the creation date of portfolio.\n");
                } else {
                  HashMap<String, String> stockDetail = new HashMap<>();
                  stockDetail.put("Date", stringDate);
                  stockDetail.put("Stock-ticker", stringStockChoice);
                  stockDetail.put("Number-of-shares", stringNumberOfShares);
                  stocksAppended.add(stockDetail);
                }
              } else {
                out.append(view.showGenericMessage("A share can be bought only for a date after" +
                        " the most recent purchase/sale of stock.\n"));
              }
            } else if (isNum == 2) {
              run = false;
            } else {
              out.append(view.displayErrorMessage("Please enter a valid integer value for " +
                      "number of stocks. Enter ticker again.\n"));
            }
            break;
          }
          case 2: {
            run = false;
            break;
          }
          case 0: {
            out.append(view.displayErrorMessage("Provided ticker is invalid\n"));
            break;
          }
          default: {
            out.append(view.displayErrorMessage("Invalid ticker.\n"));
          }
        }

      }
      if (stocksAppended.size() > 0) {
        boolean purchase = portfolio.purchaseStocks(stocksAppended, portfolioName);
        if (purchase) {
          out.append(view.showSuccessfulTransaction("Purchase"));
        } else {
          out.append(view.showUnsuccessfulTransaction("Purchase"));
        }

      } else {
        out.append(view.createUnsuccessfulMessage());
      }
//    }
  }

  private void sellSharesController(PortfolioFlexibleInterface portfolio, String portfolioName)
          throws IOException {
    boolean run = true;
    View view = new ViewImpl();
      while (run) {
        this.out.append(view.showStockOptions());
        this.out.append(view.showChangeCommissionMessage());
        String stringStockChoice = scan.next();
        int choice = UtilityClass.checkValidStock(stringStockChoice, "Quit");
        int isNum = 0;
        String stringNumberOfShares = "";
        if (choice == 1) {
          out.append(view.showGenericMessage("Enter number of shares you want to sell of this " +
                  "stock. \n " + "Note: You can only sell whole number amount of shares.\n"));
          stringNumberOfShares = scan.next();
          isNum = UtilityClass.checkIfPositiveInteger(stringNumberOfShares, "Quit");
        }
        switch (choice) {
          case 1: {
            if (isNum == 1) {
              stockTicker stockChoice = stockTicker.valueOf(stringStockChoice);
              out.append(view.showGenericDateMessage());
              String stringDate = scan.next();
              while (!UtilityClass.checkDateFormat(stringDate)) {
                out.append(view.showGenericDateMessage());
                stringDate = scan.next();
              }
              if (UtilityClass.checkDateChronology(stringStockChoice, stringDate,
                      portfolioName)) {
                boolean sell = false;
                if (UtilityClass.checkSellIsValid(portfolioName,
                        stringNumberOfShares, stringStockChoice)) {
                  sell = portfolio.sellStocks(stockChoice,
                          Integer.parseInt(stringNumberOfShares), stringDate, portfolioName);
                } else {
                  out.append(view.displayErrorMessage("\nNot enough shares present to sell.\n"));
                }
                if (sell) {
                  out.append(view.showSuccessfulTransaction("Sell"));
                } else {
                  out.append(view.showUnsuccessfulTransaction("Sell"));
                }
              } else {
                // Add logic to stop program from ending.
                out.append("A share can be sold only for a date after the most recent " +
                        "purchase/sale of stock.\n");
              }
            } else if (isNum == 2) {
              run = false;
            } else {
              out.append(view.displayErrorMessage("Please enter a valid integer value for number" +
                      " of stocks. Enter ticker again.\n"));
            }
            break;
          }
          case 2: {
            run = false;
            break;
          }
          case 0: {
            out.append(view.displayErrorMessage("Provided ticker is invalid\n"));
            break;
          }
          default: {
            out.append(view.displayErrorMessage("Invalid ticker.\n"));
          }
        }

      }
//    }
  }

  private void changeCommissionValue(PortfolioFlexibleInterface portfolio) throws IOException {
    View view = new ViewImpl();
    float comm = portfolio.getCommissionFromFile();

    out.append(view.showGenericMessage("Current commission value is: " + comm + "\n"));

    out.append(view.showGenericMessage("\nEnter new positive amount of commission\n"));
    String stringCommission = scan.next();
    if (!UtilityClass.checkIfPositiveFloat(stringCommission)) {
      out.append(view.displayErrorMessage("\nInvalid value of commission entered. " +
              "Please enter a positive decimal(float) number:\n "));
      stringCommission = scan.next();
    }
    float commission = Float.parseFloat(stringCommission);
    boolean operation = portfolio.changeCommission(commission);
    if (operation) {
      out.append(view.showGenericMessage("\nSuccessfully changed commission.\n"));
    } else {
      out.append(view.showGenericMessage("\nCommission could not be changed.\n"));
    }
  }

  private void findCostBasis(PortfolioFlexibleInterface portfolio, String portfolioName)
          throws IOException {
    View view = new ViewImpl();
      out.append(view.showGenericDateMessage());
      String stringDate = scan.next();
      while (!UtilityClass.checkDateFormat(stringDate)) {
        out.append(view.showGenericDateMessage());
        stringDate = scan.next();
      }
      if (UtilityClass.checkDateBeforeCreatedDate(portfolioName, stringDate) ||
              UtilityClass.checkDateAfterToday(portfolioName, stringDate)) {
        this.out.append("\nProvided date is not between created portfolio date and today\n");
      } else {
        double cost = portfolio.findCostBasis(stringDate,
                portfolioName);
        this.out.append(view.concatenateStrings("Cost basis is: $", String.valueOf(cost)));
      }
//    }
  }

  private void getTotalValueFlexiblePortfolio(PortfolioFlexibleInterface portfolio,
                                              String portfolioName)
          throws IOException {
    View view = new ViewImpl();
      out.append(view.showGenericDateMessage());
      double totalValue;
      String stringDate = scan.next();
      while (!UtilityClass.checkDateFormat(stringDate)) {
        out.append(view.showGenericDateMessage());
        stringDate = scan.next();
      }
      if (UtilityClass.checkDateAfterToday(portfolioName, stringDate)) {
        this.out.append(view.displayErrorMessage("\nProvided date is after today's date\n"));
        return;
      }

      totalValue = portfolio.getTotalValue(portfolioName, stringDate);
      this.out.append(view.concatenateStrings("Total value: $", String.valueOf(totalValue)));
//    }
  }

  private void getPerformanceChart(PortfolioFlexibleInterface portfolio, String portfolioName)
          throws IOException {
    View view = new ViewImpl();
    out.append(view.showGenericMessage("Enter starting date in YYYY-MM-DD\n"));
    String startingDate = scan.next();
    while (!UtilityClass.checkDateFormat(startingDate) ||
            UtilityClass.checkDateGreaterThanToday(startingDate)) {
      out.append("Enter correct starting date in the format YYYY-MM-DD " +
              "which is no later than today\n");
      startingDate = scan.next();
    }
    out.append(view.showGenericMessage("Enter last date in YYYY-MM-DD\n"));
    String endingDate = scan.next();
    while (!UtilityClass.checkDateFormat(endingDate) ||
            UtilityClass.checkDateGreaterThanToday(endingDate)) {
      out.append("Enter correct ending date in the format YYYY-MM-DD " +
              "which is no later than today\n");
      endingDate = scan.next();
    }
    if (!UtilityClass.checkIntervalBetweenDates(startingDate, endingDate, 5)) {
      out.append(view.displayErrorMessage("Need minimum of 5 days between " +
              "starting and ending date.\n"));
      return;
    }
    out.append(view.showGenericMessage("Loading performance chart...\n\n"));
    StringBuilder performance = portfolio.getPerformanceChart(startingDate, endingDate,
            portfolioName);
    out.append(view.showPerformanceChart(performance));
  }

  private void createInvestmentStrategy(PortfolioFlexibleInterface portfolio,
                                        String portfolioName) throws IOException {
    View view = new ViewImpl();
    out.append(view.showGenericMessage("\nWe currently support only Dollar Cost Averaging" +
            " Investment, which will be the default strategy for this portfolio.\n"));
    out.append(view.showGenericMessage("\nEnter a name for your investment strategy:\n"));
    String strategyName = scan.next();
    // Add condition to check if file exists
    out.append(view.showGenericMessage("\nEnter the amount you want to invest in dollars.\n"));
    String stringInvestAmount = scan.next();

    while(UtilityClass.checkIfPositiveInteger(stringInvestAmount, "quit") != 1){
      out.append(view.showGenericMessage("\nInvalid amount entered for investment amount." +
              "Enter the amount again:\n"));
      stringInvestAmount = scan.next();
    }
    int investAmount = Integer.parseInt(stringInvestAmount);
    out.append(view.showGenericMessage("\n Choose the stocks you want to " +
            "create a strategy with, followed by the percentage of amount you " +
            "want to invest in it (total of 100%):\n"));

    HashMap<String,Integer> stockList = generateListOfStocksForInvestmentStrategy();
    out.append(view.showGenericMessage("\nEnter the starting date for your investment " +
            "in YYYY-MM-DD format:\n"));
    String startingDate, endingDate;
    startingDate = scan.next();
    while(!UtilityClass.checkDateFormat(startingDate)){
      out.append("\nEnter valid date format in YYYY-MM-DD format.\n");
      startingDate = scan.next();
    }
    out.append(view.showGenericMessage("\nEnter the ending date for your investment in YYYY-MM-DD " +
            "format. \nIf you want the investment to be made only once, " +
            "enter the starting date here again.\n" +
            "If you do not wish to specify any end date, enter 'none' here:\n"));
    endingDate = scan.next();
    while(!UtilityClass.checkDateFormatWithQuitter(endingDate,"none")){
      out.append("\nEnter valid date format in YYYY-MM-DD format or enter 'none'.\n");
      endingDate = scan.next();
    }
    if(endingDate.equalsIgnoreCase("none")){
      endingDate = "2300-10-20";
    }
    int days;
    out.append(view.showGenericMessage("\nHow often do you want this investment to recur?" +
            "\n(Enter in number of days):\n"));
    String stringDays = scan.next();
    while(UtilityClass.checkIfPositiveInteger(stringDays,"quit") != 1){
      out.append(view.showGenericMessage("\nEnter valid positive number for days:\n"));
      stringDays = scan.next();
    }
    days = Integer.parseInt(stringDays);
    portfolio.createStrategy(investAmount, stockList, startingDate, endingDate, days,strategyName);
    portfolio.updateStrategy();
  }

  private HashMap<String,Integer> generateListOfStocksForInvestmentStrategy() throws IOException {
    View view = new ViewImpl();
    boolean run = true;
    HashMap<String, Integer> stockList = new HashMap<>();
    while (run) {
      out.append(view.showStockOptions());
      stockTicker stockChoice;
      String stringStockChoice = scan.next();

      int choice = UtilityClass.checkValidStock(stringStockChoice, "Quit");
      switch(choice){
        case 1: {
          out.append(view.showGenericMessage("\nEnter the percentage of total amount you want " +
                  "to invest in it (out of 100):\n"));
          String stringPercentage = scan.next();
          while (!UtilityClass.checkValidNumberOption(stringPercentage, 1, 100)) {
            out.append(view.showGenericMessage("\nInvalid amount for percentage entered. " +
                    "Enter a whole number between 1 and 100\n"));
            stringPercentage = scan.next();
          }
          stockList.put(stringStockChoice, Integer.parseInt(stringPercentage));
          break;
        }

        case 2: {
          run = false;
          break;
        }
      }
    }
    return stockList;
  }
}

