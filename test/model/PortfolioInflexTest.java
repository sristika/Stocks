package model;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import enums.stockTicker;
import utility.WorkWithXML;

import static org.junit.Assert.assertEquals;

/**
 * This is the isolation testing for model of inflexible portfolio.
 */
public class PortfolioInflexTest {

  @Test
  public void testCreatePortfolio() {
    Stocks stock1 = new Stocks(stockTicker.AAPL, 30);
    Stocks stock2 = new Stocks(stockTicker.AMZN, 40);
    Stocks stock3 = new Stocks(stockTicker.BAC, 10);
    List<Stocks> stocks = new ArrayList<>();
    stocks.add(stock1);
    stocks.add(stock2);
    stocks.add(stock3);
    String finalPath = "src/allUserPortfolios/inflexiblePortfolios/";

    String uniqueID = UUID.randomUUID().toString();
    Portfolio p = new PortfolioImpl();
    p.createPortfolio(stocks, uniqueID);
    WorkWithXML x = new WorkWithXML(finalPath + uniqueID + ".xml", uniqueID);
    List<HashMap<String, String>> allStocksData = x.read();
    HashMap<String, String> stockMap1 = new HashMap<>();
    stockMap1.put("Stock ticker", String.valueOf(stockTicker.AAPL));
    stockMap1.put("Number of shares", "30");
    stockMap1.put("Date of transaction", "2022-11-02");
    HashMap<String, String> stockMap2 = new HashMap<>();
    stockMap2.put("Stock ticker", String.valueOf(stockTicker.AMZN));
    stockMap2.put("Number of shares", "40");
    stockMap2.put("Date of transaction", "2022-11-02");
    HashMap<String, String> stockMap3 = new HashMap<>();
    stockMap3.put("Stock ticker", String.valueOf(stockTicker.BAC));
    stockMap3.put("Number of shares", "10");
    stockMap3.put("Date of transaction", "2022-11-02");
    List<HashMap<String, String>> stocksReadList = new ArrayList<>();
    stocksReadList.add(stockMap1);
    stocksReadList.add(stockMap2);
    stocksReadList.add(stockMap3);
    for (int i = 0; i < allStocksData.size(); i++) {
      assertEquals(allStocksData.get(i).get("Stock ticker"),
              stocksReadList.get(i).get("Stock ticker"));
      assertEquals(allStocksData.get(i).get("Number of shares"),
              stocksReadList.get(i).get("Number of shares"));
      assertEquals(allStocksData.get(i).get("Date of transaction"),
              stocksReadList.get(i).get("Date of transaction"));
    }
    File file
            = new File(finalPath + uniqueID + ".xml");
    file.delete();
  }

  @Test
  public void testViewPortfolio() throws IOException {
    Portfolio p = new PortfolioImpl();
    HashMap<String, Double> letsTest = p.examinePortfolio("junitTesting");

    assertEquals(String.valueOf(letsTest.get("AAPL")), String.valueOf(30));
    assertEquals(String.valueOf(letsTest.get("AMZN")), String.valueOf(40));
    assertEquals(String.valueOf(letsTest.get("BAC")), String.valueOf(10));
  }

  @Test
  public void testGetTotalValue() throws IOException {
    Portfolio p = new PortfolioImpl();
    double d = p.getTotalValue("junitTesting", "2022-11-01");
    System.out.println(d);
    assertEquals(8753.1, d, 0.1);
  }
}
