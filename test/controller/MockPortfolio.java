package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Portfolio;
import model.Stocks;

/**
 * Mock class that mocks the working of a model.
 * It logs the input being provided to the controller
 * and the output being provided to the controller.
 * This is done to check the functionality of controller
 * in isolation.
 */
class MockPortfolio implements Portfolio {

  private final StringBuilder log;
  private final int uniqueCode;

  /**
   * Constructor that takes a log and the unique code
   * which it returns to the controller to check for.
   *
   * @param log        a logger checks the input provided.
   * @param uniqueCode a unique code that is logged to test.
   */
  public MockPortfolio(StringBuilder log, int uniqueCode) {
    this.log = log;
    this.uniqueCode = uniqueCode;
  }

  @Override
  public void createPortfolio(List<Stocks> stocks, String portfolioName) {
    log.append(portfolioName);
    log.append(this.uniqueCode);
  }


  public HashMap<String, Double> examinePortfolio(String portfolioName) {
    log.append(portfolioName);
    log.append(uniqueCode);
    List<String[]> ls = new ArrayList<>();
    String[] s1 = new String[5];
    s1[0] = "a";
    s1[1] = "b";
    s1[2] = "c";
    s1[3] = "d";
    s1[4] = "e";

    String[] s2 = new String[5];
    s2[0] = "x";
    s2[1] = "y";
    s2[2] = "z";
    s2[3] = "p";
    s2[4] = "q";

    ls.add(s1);
    ls.add(s2);
    return new HashMap<String, Double>();
  }

  @Override
  public Double getTotalValue(String portfolioName, String date) {
    log.append(date);
    log.append(uniqueCode);
    return 456.023;
  }
}

