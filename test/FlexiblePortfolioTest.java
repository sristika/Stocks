import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import controller.ControllerFlexible;
import model.PortfolioFlexible;

import static org.junit.Assert.assertEquals;

/**
 * Testing class to test the overall functionality of a Flexible Portfolio and it's operations.
 */
public class FlexiblePortfolioTest {


  @Test
  public void createPortfolioTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 newTest GOOGL 2 MSFT 5 " +
            "IBM 10 MS 13 JPM 9 BAC 20 USAU 15 AMZN 8 DASH 15 NFLX 7 Quit 9 3");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Successfully created portfolio.", output[290]);
  }

  @Test
  public void createPortfolioTestFileExist() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 newTest 9 3 ");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Portfolio with name already exists. Please choose another name.",
            output[17]);
  }

  @Test
  public void createPortfolioInvalidTicker() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 newTest1 xyzasq quit 9 3 ");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Provided ticker is invalid", output[39]);
  }

  @Test
  public void createPortfolioInvalidShares() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 newTest1 MSFT ioqweb quit 9 3 ");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Please enter a valid integer value for number of stocks. " +
            "Enter ticker again.", output[42]);
  }

  @Test
  public void createPortfolioNegativeShare() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 newTest1 MSFT -2 quit 9 3 ");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Please enter a valid integer value for number of stocks. " +
            "Enter ticker again.", output[42]);
  }

  @Test
  public void examinePortfolio() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("4 newTest 9 3 ");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Portfolio contains the following stocks: ", output[18]);
    assertEquals("NFLX : 7", output[19]);
    assertEquals("MSFT : 5", output[20]);
    assertEquals("GOOGL : 2", output[21]);
    assertEquals("BAC : 20", output[22]);
    assertEquals("USAU : 15", output[23]);
    assertEquals("IBM : 10", output[24]);
  }

  @Test
  public void examinePortfolioFileNotPresent() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("4 newTest1 9 3 ");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Portfolio with name newTest1 doesn't exist", output[17]);
  }


  //Cannot purchase share before the creation date of portfolio. line 43

  @Test
  public void purchaseStocksTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 newTest AMZN 5 2022-11-16 quit 9 3 ");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Purchase transaction was successful.", output[65]);
  }

  @Test
  public void purchaseStockFileInvalid() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 nzyasd AMZN 5 2022-11-16 quit 9 3 ");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Portfolio with name nzyasd doesn't exist", output[17]);
  }

  @Test
  public void purchaseStockFutureDate() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 newTest AMZN 5 2022-11-18 quit 9 3 ");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("A share can be bought only for a date after the most " +
            "recent purchase/sale of stock.", output[43]);
  }

  @Test
  public void sellStock() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3 newTest NFLX 2 2022-11-16 quit 9 3 ");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Sell transaction was successful.", output[42]);
  }

  @Test
  public void sellStockInvalidFile() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3 zyasdh quit 9 3 ");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Portfolio with name zyasdh doesn't exist", output[17]);
  }

  @Test
  public void sellStockFutureDate() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3 newTest AMZN 1 2022-11-18 quit 9 3 ");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("A share can be sold only for a date after the" +
            " most recent purchase/sale of stock.", output[42]);

  }

  @Test
  public void sellStockInvalidAmount() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3 newTest NFLX 30 2022-11-16 quit 9 3 ");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Not enough shares present to sell.", output[43]);
  }

  @Test
  public void getTotalValueTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("5 newTest 2022-11-16 9 3 ");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Total value: $10455.16", output[18]);
  }

  @Test
  public void getTotalValueInvalidFile() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("5 xyzasd quit 9 3 ");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Portfolio with name xyzasd doesn't exist",
            output[17]);
  }

  @Test
  public void getTotalValueBeforeCreation() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("5 newTest 2022-11-13 9 3 ");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Total value: $0.0", output[18]);
  }

  @Test
  public void getTotalValueAfterCreation() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("5 newTest 2022-11-19 9 3 ");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Provided date is after today's date", output[19]);
  }

  @Test
  public void getCostBasis() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("6 newTest 2022-11-16 9 3 ");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Cost basis is: $11205.56", output[18]);
  }

  @Test
  public void getCostBasisPast() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("6 newTest 2022-11-13 9 3 ");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Provided date is not between created portfolio date and today",
            output[19]);
  }

  @Test
  public void getCostBasisFuture() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("6 newTest 2022-11-13 9 3 ");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Provided date is not between created portfolio date and today",
            output[19]);
  }

  @Test
  public void getCostBasisInvalidFile() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("6 xyzasd quit 9 3 ");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Portfolio with name xyzasd doesn't exist",
            output[17]);
  }

  @Test
  public void changeCommissionTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("7 15 9 3");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Successfully changed commission.",
            output[19]);
  }

  @Test
  public void changeCommissionInvalidEntry() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("7 abc 15 9 3");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Invalid value of commission entered. " +
            "Please enter a positive decimal(float) number:", output[19]);
  }

  @Test
  public void performanceChart() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("8 prevdate 2022-06-10 2022-11-15 9 3");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Scale: * = $583", output[45]);
  }

  @Test
  public void entireTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 entireTest 2022-06-10 GOOGL 5 MSFT 10 MS 7 quit " +
            "2 entireTest AAPL 4 2022-06-28 quit " +
            "3 entireTest GOOGL 2 2022-07-05 quit " +
            "4 entireTest " +
            "5 entireTest 2022-07-10 " +
            "6 entireTest 2022-07-15 " +
            "7 5 " +
            "8 entireTest 2022-06-10 2022-07-30 9 3");
    ControllerFlexible cnt = new ControllerFlexible(in, out);
    cnt.start(new PortfolioFlexible());
    String[] output = out.toString().split("\n");
    assertEquals("Successfully created portfolio.", output[117]);
    assertEquals("Purchase transaction was successful.", output[183]);
    assertEquals("Sell transaction was successful.", output[226]);
    assertEquals("Portfolio contains the following stocks: ", output[267]);
    assertEquals("Total value: $10963.080000000002", output[290]);
    assertEquals("Cost basis is: $14762.609999999999", output[308]);
    assertEquals("Successfully changed commission.", output[327]);
    assertEquals("Scale: * = $300", output[363]);
  }
}