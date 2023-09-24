package controller.operationsportfolio;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import utility.Validations;
import modal.InvestmentStrategyImpl;
import modal.StockNode;
import setup.classes.APIStockData;
import setup.interfaces.SetUpStockData;
import view.FlexiblePortfolioViewImpl;

/**
 * Class that handles the command provided to rebalance a portfolio on a specific date.
 */
public class BalancePortfolio implements Operations {
  final InputStream in;
  final PrintStream out;
  FlexiblePortfolioViewImpl view;
  InvestmentStrategyImpl modal;
  SetUpStockData stockObject;
  Scanner scan;

  /**
   * Constructor to perform re-balance of a portfolio.
   *
   * @param in          input stream object
   * @param out         output stream object
   * @param view        view object
   * @param modal       model object
   * @param stockObject stock data object
   */
  public BalancePortfolio(InputStream in, PrintStream out, FlexiblePortfolioViewImpl view,
                          InvestmentStrategyImpl modal, SetUpStockData stockObject) {
    this.in = in;
    this.out = out;
    this.view = view;
    this.scan = new Scanner(this.in);
    this.stockObject = new APIStockData();
    this.modal = modal;
  }

  @Override
  public void executeOperation(String fileName) {
    try {

      view.displayPortfolioDate();
      String inputDate = scan.nextLine();
      while (!Validations.checkDateFormat(inputDate) ||
              Validations.checkDateGreaterThanToday(inputDate)) {
        view.displayDateIncorrect();
        view.displayPortfolioDate();
        inputDate = scan.nextLine();
      }
      HashMap<String, Integer> stockWeightages = new HashMap<>();

      ArrayList<StockNode> composition = modal.fetchCompositionList(fileName, inputDate,
              stockObject);
      boolean run = true;
      while (run) {
        for (StockNode node : composition) {
          String tickerName = node.getTicker();
          view.displayBalanceTicker(tickerName);
          view.displayTotalStocksInComposition(composition.size());
          String amountWeightage = getInputFromConsole(in, out);
          while (!Validations.checkValidNumberOption(amountWeightage,
                  1, 100)) {
            view.showGenericMessage("\nInvalid amount for percentage entered. " +
                    "Enter a whole number between 1 and 100\n");
            amountWeightage = getInputFromConsole(in, out);
          }
          stockWeightages.put(tickerName, Integer.parseInt(amountWeightage));
        }
        boolean checkValidTotalPercent = Validations.checkValidTotalPercent(stockWeightages);
        if (!checkValidTotalPercent) {
          stockWeightages.clear();
          view.showGenericMessage("\nInvalid entries! Total percent of all stocks" +
                  " did not add up to 100. Please enter again\n");
        } else {
          run = false;
        }
      }

      modal.balancePortfolioModel(fileName, inputDate, stockWeightages, composition, stockObject);
      view.showGenericMessage("\nBalancing done!\n");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  String getInputFromConsole(InputStream in, PrintStream out) {
    try {
      return scan.nextLine();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
