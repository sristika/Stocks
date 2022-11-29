package controller;

import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;

/**
 * This class contains the test that are used by
 * the mock model to test the correctness of the controller
 * in isolation.
 */
public class MockPortfolioTest {

  @Test
  public void createPortfolioTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 mockTest MSFT 5 Quit 4");
    Controller cnt = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    int code = 1234621;
    cnt.start(new MockPortfolio(log, code));
    assertEquals("mockTest1234621", log.toString());
  }

  @Test
  public void examinePortfolioTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2 Trial10 4");
    Controller cnt = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    int code = 8237;
    cnt.start(new MockPortfolio(log, code));
    assertEquals("Trial108237", log.toString());
  }

  @Test
  public void getTotalValueTest() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3 Trial11 2022-10-27 4");
    Controller cnt = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    int code = 941324;
    cnt.start(new MockPortfolio(log, code));
    assertEquals("Trial119413242022-10-27941324", log.toString());
  }

}