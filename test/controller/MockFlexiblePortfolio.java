//package controller;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//
//import enums.stockTicker;
//import model.PortfolioFlexibleInterface;
//import model.Stocks;
//
///**
// * Mock Portfolio class to test the flexible controller in isolation.
// */
//public class MockFlexiblePortfolio implements PortfolioFlexibleInterface {
//  private final StringBuilder log;
//  private final int uniqueCode;
//
//  public MockFlexiblePortfolio(StringBuilder log, int uniqueCode) {
//    this.log = log;
//    this.uniqueCode = uniqueCode;
//  }
//
//  @Override
//  public void createPortfolio(List<Stocks> stocks, String portfolioName, String creationDate) {
//    this.log.append(portfolioName);
//    this.log.append(this.uniqueCode);
//  }
//
//  @Override
//  public HashMap<String, Integer> examinePortfolio(String portfolioName) throws IOException {
//    this.log.append(portfolioName);
//    this.log.append(this.uniqueCode);
//    HashMap<String, Integer> hm = new HashMap<>();
//    return hm;
//  }
//
//  @Override
//  public boolean purchaseStocks(List<HashMap<String, String>> stocks, String portfolioName) {
//    this.log.append(portfolioName);
//    this.log.append(this.uniqueCode);
//    return true;
//  }
//
//  @Override
//  public boolean sellStocks(stockTicker tick, int numOfShares, String date, String portfolioName) {
//    this.log.append(portfolioName);
//    this.log.append(this.uniqueCode);
//    return true;
//  }
//
//  @Override
//  public double findCostBasis(String date, String portfolioName) throws IOException {
//    this.log.append(portfolioName);
//    this.log.append(this.uniqueCode);
//    return 0;
//  }
//
//  @Override
//  public Double getTotalValue(String portfolioName, String date) throws IOException {
//    this.log.append(portfolioName);
//    this.log.append(this.uniqueCode);
//    return 0.0;
//  }
//
//  @Override
//  public boolean changeCommission(float c) {
//    this.log.append(c);
//    this.log.append(this.uniqueCode);
//    return true;
//  }
//
//  @Override
//  public float getCommissionFromFile() {
//    this.log.append(this.uniqueCode);
//    return (float) 2.3;
//  }
//
//  @Override
//  public StringBuilder getPerformanceChart(String startDate, String endDate,
//                                           String portfolioName) throws IOException {
//    this.log.append(startDate).append(endDate).append(portfolioName).append(this.uniqueCode);
//    return log;
//  }
//}
