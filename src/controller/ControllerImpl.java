package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import enums.stockTicker;
import model.Portfolio;
import model.Stocks;
import utility.UtilityClass;
import view.View;
import view.ViewImpl;

/**
 * The controller bridges the gap between a model and view.
 * It delegates the task of showing output for the user to the view
 * and the working of the functionalities like(in this case) getting
 * total value, examining portfolio, and creating portfolios to the model.
 */
public class ControllerImpl implements Controller {
  private static final LocalDate dateToday = LocalDate.parse("2022-11-02");
  private static final LocalDate lastHistoricDate = LocalDate.parse("2022-06-13");
  private final Appendable out;
  Scanner scan;

  /**
   * This is the constructor of the ControllerImpl class that initializes
   * the Readable, Appendable and Scanner object.
   */
  public ControllerImpl(Readable in, Appendable out) {
    this.out = out;
    this.scan = new Scanner(in);
  }

  @Override
  public void start(Portfolio portfolio) throws IOException {
    View view = new ViewImpl();
    boolean run = true;
    while (run) {
      out.append(view.showInflexiblePortfolioMenu());
      int choice;

      String stringChoice = scan.next();
      while (!UtilityClass.checkValidNumberOption(stringChoice, 1, 11)) {
        out.append(view.displayErrorMessage("Invalid entry. Please choose "
                + "a number to enter your choice."));
        stringChoice = scan.next();
      }
      choice = Integer.parseInt(stringChoice);
      switch (choice) {
        case 1: {
          this.out.append(view.inputPortfolioName());
          String portfolioName = scan.next();
          if (UtilityClass.checkFileExists(portfolioName, false)) {
            out.append(view.displayErrorMessage("Portfolio with name "
                    + "already exists. Please choose another name."));
          } else {
            List<Stocks> stocks = createPortfolioController();
            if (stocks.size() > 0) {
              portfolio.createPortfolio(stocks, portfolioName);
              out.append(view.createSuccessfulMessage());
            } else {
              out.append(view.createUnsuccessfulMessage());
            }
          }
          break;
        }


        case 2: {
          this.out.append(view.inputPortfolioName());
          String portfolioName = scan.next();
          if (!UtilityClass.checkFileExists(portfolioName, false)) {
            out.append(view.displayErrorMessage("Portfolio with name "
                    + portfolioName + " doesn't exist"));
          } else {
            examinePortfolioController(portfolioName, portfolio);
          }
          break;
        }

        case 3: {
          out.append(view.inputPortfolioName());
          String portfolioName = scan.next();
          if (!UtilityClass.checkFileExists(portfolioName, false)) {
            out.append(view.displayErrorMessage("Portfolio with name "
                    + portfolioName + " doesn't exist"));
          } else {
            out.append(view.showDateMessage(dateToday, lastHistoricDate));
            String date = scan.next();
            while (!UtilityClass.checkDateValidity(date)) {
              out.append(view.showDateMessage(dateToday, lastHistoricDate));
              date = scan.next();
              if (date.equalsIgnoreCase("Quit")) {
                break;
              }
            }
            this.getTotalPortfolioValueController(portfolioName, date, portfolio);
          }
          break;
        }
        case 4: {
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

  /**
   * This function handles the case where the user wants to create a portfolio.
   * It asks the view to show the list of stocks supported by this program, and further delegates
   * control to the view or model according to if the user had entered a correct value,
   * wants to quit entering, or has put an incorrect value.
   */
  private List<Stocks> createPortfolioController() throws IllegalArgumentException, IOException {
    boolean run = true;
    List<Stocks> stocks = new ArrayList<>();
    HashMap<stockTicker, Integer> uniqueTickers = new HashMap<>();
    while (run) {
      View view = new ViewImpl();
      this.out.append(view.showStockOptions());
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
                    + "stocks. Enter ticker again."));
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

  /**
   * This function provides a HashMap which helps in getting the unique stocks entered by the
   * user even if user has entered a single stock twice.
   */
  private void getUniqTicks(HashMap<stockTicker,
          Integer> u, stockTicker ticker, int numOfShares) {
    if (u.containsKey(ticker)) {
      int n = u.get(ticker);
      u.put(ticker, n + numOfShares);
    } else {
      u.put(ticker, numOfShares);
    }
  }

  /**
   * This function helps in delegating tasks to the model and view when user wants to
   * view a portfolio.
   */
  private void examinePortfolioController(String portfolioName,
                                          Portfolio portfolio) throws IOException {
    HashMap<String, Double> stocks = portfolio.examinePortfolio(portfolioName);

    View view = new ViewImpl();
    out.append(view.showPortfolio(stocks));
  }

  /**
   * This function helps in delegating tasks to the model and view when user wants to
   * get the total value of a portfolio.
   */
  private void getTotalPortfolioValueController(String portfolioName, String date,
                                                Portfolio portfolio) throws IOException {
    boolean isValidDate = UtilityClass.checkDateValidity(date);
    View view = new ViewImpl();
    if (isValidDate) {
      HashMap<String, Double> stocks = portfolio.examinePortfolio(portfolioName);
      Double totalValue = portfolio.getTotalValue(portfolioName, date);

      out.append(view.showTotalValue(portfolioName, date, totalValue));
    } else {
      if (!date.equalsIgnoreCase("quit")) {
        out.append(view.showDateMessage(dateToday, lastHistoricDate));
      }
    }
  }
}
