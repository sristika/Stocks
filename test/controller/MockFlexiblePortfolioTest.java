//package controller;
//
//import org.junit.Test;
//
//import java.io.IOException;
//import java.io.Reader;
//import java.io.StringReader;
//
//import static org.junit.Assert.assertEquals;
//
///**
// * Testing class that has test cases to test the flexible controller in isolation.
// */
//public class MockFlexiblePortfolioTest {
//
//  @Test
//  public void createPortfolioTest() throws IOException {
//    StringBuffer out = new StringBuffer();
//    Reader in = new StringReader("1 test AMZN 30 MSFT 20 GOOGL 10 quit 9 3");
//    ControllerFlexible flx = new ControllerFlexible(in, out);
//    StringBuilder log = new StringBuilder();
//    int code = 123895;
//    flx.start(new MockFlexiblePortfolio(log, code));
//    assertEquals("test123895", log.toString());
//  }
//
//  @Test
//  public void examinePortfolioTest() throws IOException {
//    StringBuffer out = new StringBuffer();
//    Reader in = new StringReader("4 trials 2022-11-15 9 3");
//    ControllerFlexible flx = new ControllerFlexible(in, out);
//    StringBuilder log = new StringBuilder();
//    int code = 84792;
//    flx.start(new MockFlexiblePortfolio(log, code));
//    assertEquals("trials84792", log.toString());
//  }
//
//  @Test
//  public void purchaseStocksTest() throws IOException {
//    StringBuffer out = new StringBuffer();
//    Reader in = new StringReader("2 trials ETSY 10 2022-11-15 quit 9 3");
//    ControllerFlexible flx = new ControllerFlexible(in, out);
//    StringBuilder log = new StringBuilder();
//    int code = 823701;
//    flx.start(new MockFlexiblePortfolio(log, code));
//    assertEquals("trials823701", log.toString());
//  }
//
//  @Test
//  public void sellStocksTest() throws IOException {
//    StringBuffer out = new StringBuffer();
//    Reader in = new StringReader("3 trials ETSY 5 2022-11-15 quit 9 3");
//    ControllerFlexible flx = new ControllerFlexible(in, out);
//    StringBuilder log = new StringBuilder();
//    int code = 5465132;
//    flx.start(new MockFlexiblePortfolio(log, code));
//    assertEquals("trials5465132", log.toString());
//  }
//
//  @Test
//  public void getTotalValueTest() throws IOException {
//    StringBuffer out = new StringBuffer();
//    Reader in = new StringReader("5 twocntrl 2022-11-15 9 3");
//    ControllerFlexible flx = new ControllerFlexible(in, out);
//    StringBuilder log = new StringBuilder();
//    int code = 3476125;
//    flx.start(new MockFlexiblePortfolio(log, code));
//    assertEquals("twocntrl3476125", log.toString());
//  }
//
//  @Test
//  public void costBasisTest() throws IOException {
//    StringBuffer out = new StringBuffer();
//    Reader in = new StringReader("6 twocntrl 2022-11-15 9 3");
//    ControllerFlexible flx = new ControllerFlexible(in, out);
//    StringBuilder log = new StringBuilder();
//    int code = 317954;
//    flx.start(new MockFlexiblePortfolio(log, code));
//    assertEquals("twocntrl317954", log.toString());
//  }
//
//  @Test
//  public void changeCommissionTest() throws IOException {
//    StringBuffer out = new StringBuffer();
//    Reader in = new StringReader("7 5 9 3");
//    ControllerFlexible flx = new ControllerFlexible(in, out);
//    StringBuilder log = new StringBuilder();
//    int code = 9841326;
//    flx.start(new MockFlexiblePortfolio(log, code));
//    assertEquals("5.09841326", log.toString());
//  }
//
//  @Test
//  public void getPerformanceChart() throws IOException {
//    StringBuffer out = new StringBuffer();
//    Reader in = new StringReader("8 prevdate 2022-06-10 2022-11-15 9 3");
//    ControllerFlexible flx = new ControllerFlexible(in, out);
//    StringBuilder log = new StringBuilder();
//    int code = 923114;
//    flx.start(new MockFlexiblePortfolio(log, code));
//    assertEquals("2022-06-102022-11-15prevdate923114", log.toString());
//  }
//
//}
