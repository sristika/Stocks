package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import enums.stockTicker;
import jdk.swing.interop.SwingInterOpUtils;
import utility.WorkWithXML;

/**
 * This class has the implementation of all the operations that the
 * user can carry out on flexible portfolios.
 */
public class PortfolioFlexible extends AbstractPortfolio implements PortfolioFlexibleInterface {

  private final String absolutePath = System.getProperty("user.dir");
  private final String osSeperator = System.getProperty("file.separator");
  protected final String finalPath = absolutePath + osSeperator + "allUserPortfolios" + osSeperator
          + "flexiblePortfolios" + osSeperator;
  private float commission;

  protected String portfolioName;

  /**
   * This constructor loads commission data from files and saves it in a private variable.
   */
  public PortfolioFlexible() {
    getCommissionFromFile();
  }

  public void setPortfolioName(String s){
    this.portfolioName = s;
  }

  @Override
  public float getCommissionFromFile() {
    this.commission = 0;
    String path = absolutePath + osSeperator + "Commission.txt";
    try {
      File myObj = new File(path);
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        this.commission = Float.parseFloat(data);
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      this.commission = 0;
    }
    return this.commission;
  }

  @Override
  public void createPortfolio(List<Stocks> stocks, String portfolioName, String creationDate) {
    ArrayList<HashMap<String, String>> stocksList =
            createPortfolioAbs(stocks, creationDate, commission);
    WorkWithXML p = new WorkWithXML(this.finalPath + this.portfolioName + ".xml", this.portfolioName);
    p.create(stocksList, creationDate);
  }


  @Override
  public HashMap<String, Double> examinePortfolio(String portfolioName) throws IOException {
    return examinePortfolioAbs(this.portfolioName, this.finalPath + this.portfolioName + ".xml");
  }

  @Override
  public boolean purchaseStocks(List<HashMap<String, String>> stocks, String portfolioName) {
    for (HashMap<String, String> i : stocks) {
      i.put("Commission", String.valueOf(commission));
    }
    WorkWithXML p = new WorkWithXML(this.finalPath + this.portfolioName + ".xml", this.portfolioName);
    return p.update(stocks);
  }


  @Override
  public boolean sellStocks(stockTicker tick, int numOfShares, String date, String portfolioName) {
    HashMap<String, String> stock = new HashMap<>();
    stock.put("Stock-ticker", String.valueOf(tick));
    stock.put("Number-of-shares", String.valueOf(numOfShares * -1));
    stock.put("Date", date);
    stock.put("Commission", String.valueOf(commission));

    List<HashMap<String, String>> stocks = new ArrayList<>();
    stocks.add(stock);

    WorkWithXML p = new WorkWithXML(this.finalPath + this.portfolioName + ".xml", this.portfolioName);

    return p.update(stocks);
  }

  @Override
  public double findCostBasis(String date, String portfolioName) throws IOException {
    WorkWithXML p = new WorkWithXML(this.finalPath + this.portfolioName + ".xml", this.portfolioName);
    double totalCommission = 0;
    List<HashMap<String, String>> stocks = p.read();
    LocalDate dateFormatted = LocalDate.parse(date);
    double sumOfAllPurchased = 0;
    for (int i = 0; i < stocks.size(); i++) {
      int isBefore = dateFormatted.compareTo(LocalDate.parse(stocks.get(i).
              get("Date of transaction")));
      if (isBefore >= 0) {
        totalCommission = totalCommission + Double.parseDouble(stocks.get(i).get("Commission"));
        if (Double.parseDouble(stocks.get(i).get("Number of shares")) > 0) {
          Stocks stock = new Stocks(stockTicker.valueOf(stocks.get(i).get("Stock ticker")),
                  Double.parseDouble(stocks.get(i).get("Number of shares")));
          stock.fillStockData(stocks.get(i).get("Date of transaction"), true);
          sumOfAllPurchased = sumOfAllPurchased + (stock.getNumberOfShares()
                  * Double.parseDouble(stock.getValueOfShare()));
        }
      }
    }
    return totalCommission + sumOfAllPurchased;
  }


  @Override
  public Double getTotalValue(String portfolioName, String date) throws IOException {
    double totalValue = 0;
    WorkWithXML p = new WorkWithXML(this.finalPath + this.portfolioName + ".xml", this.portfolioName);
    List<HashMap<String, String>> allStocksData = p.read();
    HashMap<String, Double> allTickerQuant = new HashMap<>();

    LocalDate dateFormatted = LocalDate.parse(date);

    for (HashMap<String, String> s : allStocksData) {
      if (dateFormatted.compareTo(LocalDate.parse(s.get("Date of transaction"))) >= 0) {
        if (allTickerQuant.containsKey(s.get("Stock ticker"))) {
          allTickerQuant.put(s.get("Stock ticker"), allTickerQuant.get(s.get("Stock ticker"))
                  + Double.parseDouble(s.get("Number of shares")));
        } else {
          allTickerQuant.put(s.get("Stock ticker"), Double.parseDouble(s.get("Number of shares")));
        }
      }
    }
    for (Map.Entry<String, Double> entry : allTickerQuant.entrySet()) {
      Stocks stock = new Stocks(stockTicker.valueOf(entry.getKey()), entry.getValue());
      stock.fillStockData(date, true);
      totalValue += Double.parseDouble(stock.getValueOfShare()) * stock.getNumberOfShares();
    }
    return totalValue;
  }

  @Override
  public StringBuilder getPerformanceChart(String startDate, String endDate, String portfolioName)
          throws IOException {
    PortfolioPerformanceChart c =
            new PortfolioPerformanceChart(startDate, endDate, this.portfolioName);
    return c.createBarchart();
  }


  @Override
  public boolean changeCommission(float c) {
    String path = absolutePath + osSeperator + "Commission.txt";
    try {
      FileWriter myWriter = new FileWriter(path);
      myWriter.write(String.valueOf(c));
      myWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    getCommissionFromFile();
    return true;
  }

  public void createStrategy(int total, HashMap<String, Integer> proportions, String start,
                             String end, int days, String name){
    PortfolioDCAImpl d = new PortfolioDCAImpl();
    d.portfolioName = portfolioName;
    d.setStrategyName(name);
    d.createDCAStrategy(total, proportions, start, end, days);
  }

  public void updateStrategy() throws IOException {
    PortfolioDCAImpl d = new PortfolioDCAImpl();
    d.portfolioName = portfolioName;
    d.updateDCAStrategyToPortfolio();
  }

}
