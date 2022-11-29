package model;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import enums.stockTicker;
import utility.WorkWithXML;

import static org.junit.Assert.assertEquals;

/**
 * This is the isolation testing for model of flexible portfolio.
 */
public class PortfolioFlexTest {

  private final String finalPath = "src/allUserPortfolios/flexiblePortfolios/";

  @Test
  public void testCreatePortfolio() {
    Stocks stock1 = new Stocks(stockTicker.AAPL, 30);
    Stocks stock2 = new Stocks(stockTicker.AMZN, 40);
    Stocks stock3 = new Stocks(stockTicker.BAC, 10);
    List<Stocks> stocks = new ArrayList<>();
    stocks.add(stock1);
    stocks.add(stock2);
    stocks.add(stock3);
    LocalDate dateObj = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String dateToday = dateObj.format(formatter);

    String uniqueID = UUID.randomUUID().toString();
    PortfolioFlexibleInterface p = new PortfolioFlexible();
    p.createPortfolio(stocks, uniqueID, "2022-11-02");
    WorkWithXML x = new WorkWithXML(finalPath + uniqueID + ".xml", uniqueID);
    List<HashMap<String, String>> allStocksData = x.read();
    HashMap<String, String> stockMap1 = new HashMap<>();
    stockMap1.put("Stock ticker", String.valueOf(stockTicker.AAPL));
    stockMap1.put("Number of shares", "30");
    stockMap1.put("Date of transaction", dateToday);
    HashMap<String, String> stockMap2 = new HashMap<>();
    stockMap2.put("Stock ticker", String.valueOf(stockTicker.AMZN));
    stockMap2.put("Number of shares", "40");
    stockMap2.put("Date of transaction", dateToday);
    HashMap<String, String> stockMap3 = new HashMap<>();
    stockMap3.put("Stock ticker", String.valueOf(stockTicker.BAC));
    stockMap3.put("Number of shares", "10");
    stockMap3.put("Date of transaction", dateToday);
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
            = new File("src/allUserPortfolios/flexiblePortfolios/" + uniqueID + ".xml");
  }

  @Test
  public void testViewPortfolio() throws IOException {
    PortfolioFlexibleInterface p = new PortfolioFlexible();
    HashMap<String, Double> letsTest = p.examinePortfolio("junitTesting");
    System.out.println(letsTest.get("AAPL"));

    assertEquals(String.valueOf(letsTest.get("AAPL")), String.valueOf(30));
    assertEquals(String.valueOf(letsTest.get("AMZN")), String.valueOf(40));
    assertEquals(String.valueOf(letsTest.get("BAC")), String.valueOf(10));
  }

  @Test
  public void testPurchaseStock() throws IOException {
    PortfolioFlexibleInterface p = new PortfolioFlexible();
    List<HashMap<String, String>> stocksAppended = new ArrayList<>();
    HashMap<String, String> stockDetail1 = new HashMap<>();
    stockDetail1.put("Date", "2018-05-01");
    stockDetail1.put("Stock-ticker", "GOOGL");
    stockDetail1.put("Number-of-shares", "30");
    HashMap<String, String> stockDetail2 = new HashMap<>();
    stockDetail2.put("Date", "2019-09-10");
    stockDetail2.put("Stock-ticker", "AAPL");
    stockDetail2.put("Number-of-shares", "20");
    HashMap<String, String> stockDetail3 = new HashMap<>();
    stockDetail3.put("Date", "2021-01-31");
    stockDetail3.put("Stock-ticker", "BAC");
    stockDetail3.put("Number-of-shares", "10");
    stocksAppended.add(stockDetail1);
    stocksAppended.add(stockDetail2);
    stocksAppended.add(stockDetail3);
    p.purchaseStocks(stocksAppended, "junitTesting");

    WorkWithXML x = new WorkWithXML(finalPath + "junitTesting" + ".xml", "junitTesting");
    List<HashMap<String, String>> allStocksData = x.read();
    HashMap<String, String> stockMap1 = new HashMap<>();
    stockMap1.put("Stock ticker", String.valueOf(stockTicker.AAPL));
    stockMap1.put("Number of shares", "30");
    stockMap1.put("Date of transaction", "2007-10-07");
    HashMap<String, String> stockMap2 = new HashMap<>();
    stockMap2.put("Stock ticker", String.valueOf(stockTicker.AMZN));
    stockMap2.put("Number of shares", "40");
    stockMap2.put("Date of transaction", "2007-10-07");
    HashMap<String, String> stockMap3 = new HashMap<>();
    stockMap3.put("Stock ticker", String.valueOf(stockTicker.BAC));
    stockMap3.put("Number of shares", "10");
    stockMap3.put("Date of transaction", "2007-10-07");
    HashMap<String, String> stockMap4 = new HashMap<>();
    stockMap4.put("Stock ticker", String.valueOf(stockTicker.GOOGL));
    stockMap4.put("Number of shares", "30");
    stockMap4.put("Date of transaction", "2018-05-01");
    HashMap<String, String> stockMap5 = new HashMap<>();
    stockMap5.put("Stock ticker", String.valueOf(stockTicker.AAPL));
    stockMap5.put("Number of shares", "20");
    stockMap5.put("Date of transaction", "2019-09-10");
    HashMap<String, String> stockMap6 = new HashMap<>();
    stockMap6.put("Stock ticker", String.valueOf(stockTicker.BAC));
    stockMap6.put("Number of shares", "10");
    stockMap6.put("Date of transaction", "2021-01-31");
    List<HashMap<String, String>> stocksReadList = new ArrayList<>();
    stocksReadList.add(stockMap1);
    stocksReadList.add(stockMap2);
    stocksReadList.add(stockMap3);
    stocksReadList.add(stockMap4);
    stocksReadList.add(stockMap5);
    stocksReadList.add(stockMap6);

    for (int i = 0; i < allStocksData.size(); i++) {
      assertEquals(allStocksData.get(i).get("Stock ticker"),
              stocksReadList.get(i).get("Stock ticker"));
      assertEquals(allStocksData.get(i).get("Number of shares"),
              stocksReadList.get(i).get("Number of shares"));
      assertEquals(allStocksData.get(i).get("Date of transaction"),
              stocksReadList.get(i).get("Date of transaction"));
    }
  }

  @Test
  public void testSellStock() throws IOException {
    //remove particular data from xml before testing each time
    List<HashMap<String, String>> stocksAppended = new ArrayList<>();
    HashMap<String, String> stockDetail1 = new HashMap<>();
    stockDetail1.put("Date", "2021-11-30");
    stockDetail1.put("Stock-ticker", "GOOGL");
    stockDetail1.put("Number-of-shares", "10");
    stocksAppended.add(stockDetail1);
    PortfolioFlexibleInterface p = new PortfolioFlexible();
    p.purchaseStocks(stocksAppended, "junitTesting");
    p.sellStocks(stockTicker.valueOf("GOOGL"), 10,
            "2021-11-30", "junitTesting");

    WorkWithXML x = new WorkWithXML(finalPath + "junitTesting" + ".xml", "junitTesting");
    List<HashMap<String, String>> allStocksData = x.read();
    HashMap<String, String> stockMap1 = new HashMap<>();
    stockMap1.put("Stock ticker", String.valueOf(stockTicker.AAPL));
    stockMap1.put("Number of shares", "30");
    stockMap1.put("Date of transaction", "2007-10-07");
    HashMap<String, String> stockMap2 = new HashMap<>();
    stockMap2.put("Stock ticker", String.valueOf(stockTicker.AMZN));
    stockMap2.put("Number of shares", "40");
    stockMap2.put("Date of transaction", "2007-10-07");
    HashMap<String, String> stockMap3 = new HashMap<>();
    stockMap3.put("Stock ticker", String.valueOf(stockTicker.BAC));
    stockMap3.put("Number of shares", "10");
    stockMap3.put("Date of transaction", "2007-10-07");
    HashMap<String, String> stockMap4 = new HashMap<>();
    stockMap4.put("Stock ticker", String.valueOf(stockTicker.GOOGL));
    stockMap4.put("Number of shares", "30");
    stockMap4.put("Date of transaction", "2018-05-01");
    HashMap<String, String> stockMap5 = new HashMap<>();
    stockMap5.put("Stock ticker", String.valueOf(stockTicker.AAPL));
    stockMap5.put("Number of shares", "20");
    stockMap5.put("Date of transaction", "2019-09-10");
    HashMap<String, String> stockMap6 = new HashMap<>();
    stockMap6.put("Stock ticker", String.valueOf(stockTicker.BAC));
    stockMap6.put("Number of shares", "10");
    stockMap6.put("Date of transaction", "2021-01-31");
    HashMap<String, String> stockMap7 = new HashMap<>();
    stockMap7.put("Stock ticker", String.valueOf(stockTicker.GOOGL));
    stockMap7.put("Number of shares", "10");
    stockMap7.put("Date of transaction", "2021-11-30");
    HashMap<String, String> stockMap8 = new HashMap<>();
    stockMap8.put("Stock ticker", String.valueOf(stockTicker.GOOGL));
    stockMap8.put("Number of shares", "-10");
    stockMap8.put("Date of transaction", "2021-11-30");
    List<HashMap<String, String>> stocksReadList = new ArrayList<>();
    stocksReadList.add(stockMap1);
    stocksReadList.add(stockMap2);
    stocksReadList.add(stockMap3);
    stocksReadList.add(stockMap4);
    stocksReadList.add(stockMap5);
    stocksReadList.add(stockMap6);
    stocksReadList.add(stockMap7);
    stocksReadList.add(stockMap8);

    for (int i = 0; i < allStocksData.size(); i++) {
      assertEquals(allStocksData.get(i).get("Stock ticker"),
              stocksReadList.get(i).get("Stock ticker"));
      assertEquals(allStocksData.get(i).get("Number of shares"),
              stocksReadList.get(i).get("Number of shares"));
      assertEquals(allStocksData.get(i).get("Date of transaction"),
              stocksReadList.get(i).get("Date of transaction"));
    }

  }

  @Test
  public void testCostBasis() throws IOException {
    PortfolioFlexibleInterface p = new PortfolioFlexible();
    double d = p.findCostBasis("2022-11-01", "junitTesting");
    assertEquals(d, 73500.29699999999, 0.1);
  }

  @Test
  public void testGetTotalValue() throws IOException {
    PortfolioFlexibleInterface p = new PortfolioFlexible();
    double d = p.getTotalValue("junitTesting", "2022-11-01");
    assertEquals(14842.2, d, 0.1);
  }

  @Test
  public void testPerformanceChart() throws IOException {
    PortfolioFlexibleInterface p = new PortfolioFlexible();
    StringBuilder s = p.getPerformanceChart("2022-09-07", "2022-11-16",
            "junitTesting");
    String out = "Performance of portfolio junitTesting from 2022-09-07 to 2022-11-16\n" +
            "\n" +
            "09 Sep 2022 : ****************************************************\n" +
            "16 Sep 2022 : *************************************\n" +
            "23 Sep 2022 : ****************************\n" +
            "30 Sep 2022 : *****************\n" +
            "07 Oct 2022 : *********************\n" +
            "14 Oct 2022 : **************\n" +
            "21 Oct 2022 : *******************************\n" +
            "28 Oct 2022 : **************************\n" +
            "04 Nov 2022 : *\n" +
            "11 Nov 2022 : *********************\n" +
            "\n" +
            "Scale: * = $66  Base: $13825";
    assertEquals(out, s.toString());
  }

  private float getCommissionFromFile() {
    float commission = 0;
    String path = "src/Commission.txt";
    try {
      File myObj = new File(path);
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        commission = Float.parseFloat(data);
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      commission = 0;
    }
    return commission;
  }

  @Test
  public void testChangeCommission() throws IOException {
    String path = "src/Commission.txt";
    float commission = 0;
    commission = getCommissionFromFile();
    PortfolioFlexibleInterface p = new PortfolioFlexible();

    p.changeCommission(commission + 10);
    assertEquals(commission + 10, getCommissionFromFile(), 0.1);
    try {
      FileWriter myWriter = new FileWriter(path);
      myWriter.write(String.valueOf(commission));
      myWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @Test
  public void testCreationWithDCA() {
    String stratName = "strat2";
    int totalVal = 1000;
    HashMap<String, Integer> proportions = new HashMap<>();
    proportions.put("MSFT", 70);
    proportions.put("DASH", 30);
    String start = "2022-11-13";
    String end = "2022-11-30";
    int freq = 7;

    PortfolioFlexibleInterface d = new PortfolioFlexible();
    d.setPortfolioName("srisTesting2Flex");
    d.createStrategy(totalVal, proportions, start, end, freq, stratName);
  }

  @Test
  public void testUpdateDCA() throws IOException {
    PortfolioFlexibleInterface d = new PortfolioFlexible();
    d.setPortfolioName("srisTesting2Flex");
    d.updateStrategy();
  }

}
