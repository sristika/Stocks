import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import controller.Controller;
import controller.ControllerImpl;
import model.PortfolioImpl;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the working of the entire application
 * integrated as a whole by testing the outputs of
 * create, examine and getValue functionalities.
 */
public class ControllerImplTest {

  @Test
  public void examinePortfolioTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 Trial19 4");
    Controller cnt = new ControllerImpl(in, out);
    cnt.start(new PortfolioImpl());
    String[] output = out.toString().split("\n");
    assertEquals("Stock Name: Microsoft", output[12]);
    assertEquals("Number of shares purchased: 4", output[13]);
    assertEquals("Value purchased at: 235.8700", output[14]);
    assertEquals("Date purchased on: 2022-10-28", output[15]);
  }

  @Test
  public void examineMultipleStocksPortfolio() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 Trial15 4");
    Controller cnt = new ControllerImpl(in, out);
    cnt.start(new PortfolioImpl());
    String[] output = out.toString().split("\n");
    assertEquals("Stock Name: Morgan Stanley", output[12]);
    assertEquals("Number of shares purchased: 5", output[13]);
    assertEquals("Value purchased at: 82.2200", output[14]);
    assertEquals("Date purchased on: 2022-10-28", output[15]);

    assertEquals("Stock Name: Apple", output[17]);
    assertEquals("Number of shares purchased: 4", output[18]);
    assertEquals("Value purchased at: 155.7400", output[19]);
    assertEquals("Date purchased on: 2022-10-28", output[20]);

    assertEquals("Stock Name: Google", output[22]);
    assertEquals("Number of shares purchased: 2", output[23]);
    assertEquals("Value purchased at: 96.2900", output[24]);
    assertEquals("Date purchased on: 2022-10-28", output[25]);
  }

  @Test
  public void createSinglePortfolio() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 newTest GOOGL 22 MSFT 5 " +
            "IBM 10 MS 23 Quit 4");
    Controller cnt = new ControllerImpl(in, out);
    cnt.start(new PortfolioImpl());
    String[] output = out.toString().split("\n");
    assertEquals("Successfully created portfolio.", output[117]);
  }

  @Test
  public void createMultiplePortfolios() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 multPortf1 AAPL 5 IBM 2 Quit " +
            "1 multPortf2 GOOGL 7 MS 2 Quit " +
            "1 multPortf3 IBM 10 MSFT 23 Quit " +
            "2 multPortf2 " +
            "2 multPortf3 4");
    Controller cnt = new ControllerImpl(in, out);
    cnt.start(new PortfolioImpl());
    String[] output = out.toString().split("\n");
    assertEquals("Successfully created portfolio.", output[73]);
    assertEquals("Successfully created portfolio.", output[147]);
    assertEquals("Successfully created portfolio.", output[221]);

    assertEquals("Stock Name: Google", output[234]);
    assertEquals("Number of shares purchased: 7", output[235]);
    assertEquals("Value purchased at: 86.9700", output[236]);
    assertEquals("Date purchased on: 2022-11-02", output[237]);

    assertEquals("Stock Name: Morgan Stanley", output[239]);
    assertEquals("Number of shares purchased: 2", output[240]);
    assertEquals("Value purchased at: 84.3700", output[241]);
    assertEquals("Date purchased on: 2022-11-02", output[242]);
  }


  @Test
  public void getValueTest1() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3 multPortf1 2022-10-27 4");
    Controller cnt = new ControllerImpl(in, out);
    cnt.start(new PortfolioImpl());
    String[] output = out.toString().split("\n");
    assertEquals("Total value of portfolio multPortf1 on 2022-10-27 is: 993.54",
            output[11]);
  }

  @Test
  public void getValueTest2() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3 multPortf1 2022-10-26 4");
    Controller cnt = new ControllerImpl(in, out);
    cnt.start(new PortfolioImpl());
    String[] output = out.toString().split("\n");
    assertEquals("Total value of portfolio multPortf1 on 2022-10-26 is: 992.27",
            output[11]);
  }

  @Test
  public void getValueTest3() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3 multPortf1 2022-10-21 4");
    Controller cnt = new ControllerImpl(in, out);
    cnt.start(new PortfolioImpl());
    String[] output = out.toString().split("\n");
    assertEquals("Total value of portfolio multPortf1 on 2022-10-21 is: 990.35",
            output[11]);
  }

  @Test
  public void invalidMenuChoice() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("7 4");
    Controller cnt = new ControllerImpl(in, out);
    cnt.start(new PortfolioImpl());
    String[] output = out.toString().split("\n");
    assertEquals("Invalid entry. " +
            "Please choose a number to enter your choice.", output[8]);
  }

  @Test
  public void invalidAndValidMenuChoice() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("7 3 multPortf1 2022-10-27 4");
    Controller cnt = new ControllerImpl(in, out);
    cnt.start(new PortfolioImpl());
    String[] output = out.toString().split("\n");
    assertEquals("Invalid entry. " +
            "Please choose a number to enter your choice.", output[8]);
    assertEquals("Total value of portfolio multPortf1" +
            " on 2022-10-27 is: 993.54", output[11]);
  }

  @Test
  public void noStocksEnteredTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 PortName Quit 4");
    Controller cnt = new ControllerImpl(in, out);
    cnt.start(new PortfolioImpl());
    String[] output = out.toString().split("\n");
    assertEquals("No stocks entered.", output[29]);
  }

  @Test
  public void invalidStockTicker() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 PortName XYZZY Quit 4");
    Controller cnt = new ControllerImpl(in, out);
    cnt.start(new PortfolioImpl());
    String[] output = out.toString().split("\n");
    assertEquals("Provided ticker is invalid", output[28]);
  }

  @Test
  public void invalidStockAmountString() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 PortName MSFT hsafbd Quit 4");
    Controller cnt = new ControllerImpl(in, out);
    cnt.start(new PortfolioImpl());
    String[] output = out.toString().split("\n");
    assertEquals("Please enter a valid integer value for number of stocks",
            output[31]);
  }

  @Test
  public void invalidStockAmountDouble() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 PortName MSFT 3.5 Quit 4");
    Controller cnt = new ControllerImpl(in, out);
    cnt.start(new PortfolioImpl());
    String[] output = out.toString().split("\n");
    assertEquals("Please enter a valid integer value for number of stocks",
            output[31]);
  }

  @Test
  public void createPortfolioFileExists() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 portf1 4");
    Controller cnt = new ControllerImpl(in, out);
    cnt.start(new PortfolioImpl());
    String[] output = out.toString().split("\n");
    assertEquals("Portfolio with name already exists. " +
            "Please choose another name.", output[10]);
  }

  @Test
  public void examinePortfolioInvalidName() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 somexyzzyy 4");
    Controller cnt = new ControllerImpl(in, out);
    cnt.start(new PortfolioImpl());
    String[] output = out.toString().split("\n");
    assertEquals("Portfolio with name somexyzzyy doesn't exist",
            output[10]);
  }

  @Test
  public void getInvalidDate() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3 portf1 2022-10-23 2022-10-27 4");
    Controller cnt = new ControllerImpl(in, out);
    cnt.start(new PortfolioImpl());
    String[] output = out.toString().split("\n");
    assertEquals("Please enter a valid date in YYYY-MM-DD format between" +
            " 2022-06-13 and 2022-11-02 (Must be a business day)", output[10]);
  }

  @Test
  public void getValueInvalidDateFormat() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3 trial8 xzjsadh 2022-10-27 4");
    Controller cnt = new ControllerImpl(in, out);
    cnt.start(new PortfolioImpl());
    String[] output = out.toString().split("\n");
    assertEquals("Please enter a valid date in YYYY-MM-DD format between" +
            " 2022-06-13 and 2022-11-02 (Must be a business day)", output[10]);
  }

  @Test
  public void getValueFutureDate() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3 trial8 2022-12-25 2022-10-27 4");
    Controller cnt = new ControllerImpl(in, out);
    cnt.start(new PortfolioImpl());
    String[] output = out.toString().split("\n");
    assertEquals("Please enter a valid date in YYYY-MM-DD format between" +
            " 2022-06-13 and 2022-11-02 (Must be a business day)", output[10]);
  }

  @Test
  public void getValuePastDate() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3 trial8 2008-12-25 2022-10-27 4");
    Controller cnt = new ControllerImpl(in, out);
    cnt.start(new PortfolioImpl());
    String[] output = out.toString().split("\n");
    assertEquals("Please enter a valid date in YYYY-MM-DD format between" +
            " 2022-06-13 and 2022-11-02 (Must be a business day)", output[10]);
  }

  @Test
  public void getValueInvalidFileName() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3 newNew 4");
    Controller cnt = new ControllerImpl(in, out);
    cnt.start(new PortfolioImpl());
    String[] output = out.toString().split("\n");
    assertEquals("Portfolio with name newNew doesn't exist", output[10]);
  }


}