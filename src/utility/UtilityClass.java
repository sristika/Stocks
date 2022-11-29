package utility;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import enums.stockTicker;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * This class contains all data validation functions that are used by the Stocks project.
 * The methods are kept public to allow access from all classes in the project.
 */
public final class UtilityClass {

  private static final LocalDate dateToday = LocalDate.parse("2022-11-02");
  private static final LocalDate lastHistoricDate = LocalDate.parse("2022-06-13");

  /**
   * This function checks if a string is an integer or a quitter string (A quitter
   * string is entered by the user when he does not want to proceed).
   *
   * @param num     the number to check
   * @param quitter the quitter string
   */
  public static int checkIfPositiveInteger(String num, String quitter) {
    try {
      Integer.parseInt(num);
    } catch (Exception e) {
      if (num.equalsIgnoreCase(quitter)) {
        return 2;
      }
      return 0;
    }
    if (Integer.parseInt(num) <= 0) {
      return 0;
    } else {
      return 1;
    }
  }

  /**
   * This function checks if a String is a positive float value.
   *
   * @param num the String to check
   */
  public static boolean checkIfPositiveFloat(String num) {
    try {
      Float.parseFloat(num);
    } catch (Exception e) {
      return false;
    }
    return Float.parseFloat(num) >= 0;
  }

  /**
   * This function checks if a string is a valid ticker or a quitter string (A quitter
   * string is entered by the user when he does not want to proceed).
   *
   * @param s       the ticker to check
   * @param quitter the quitter string
   */
  public static int checkValidStock(String s, String quitter) {
    try {
      stockTicker.valueOf(s);
    } catch (Exception e) {
      if (s.equalsIgnoreCase(quitter)) {
        return 2;
      } else {
        return 0;
      }
    }
    return 1;
  }

  /**
   * This function checks if a portfolio file exists in the user portfolio folder.
   *
   * @param portfolioName the file to check
   */
  public static boolean checkFileExists(String portfolioName, boolean flexible) {
    String absolutePath = System.getProperty("user.dir");
    String osSeperator = System.getProperty("file.separator");
    String path = absolutePath + osSeperator + "allUserPortfolios" + osSeperator;
    if (flexible) {
      path = path + "flexiblePortfolios/" + portfolioName + ".xml";
    } else {
      path = path + "inflexiblePortfolios/" + portfolioName + ".xml";
    }
    File filePath = new File(path);
    return filePath.exists();
  }


  /**
   * This function checks if a date is in the correct format.
   *
   * @param date the date to check
   */
  public static boolean checkDateFormat(String date) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    format.setLenient(false);
    try {
      format.parse(date);
      return true;
    } catch (ParseException e) {
      return false;
    }
  }

  /**
   * This function checks if a date is in the future.
   *
   * @param date the date to check
   */
  public static boolean checkDateGreaterThanToday(String date) {
    LocalDate inputDate = LocalDate.parse(date);
    LocalDate today = LocalDate.now();
    return inputDate.compareTo(today) > 0;
  }

  /**
   * This function checks if the date provided is a valid date and falls in our list of dates
   * that are allowed.
   *
   * @param date the date to check
   */
  public static boolean checkDateValidity(String date) throws IOException {
    String absolutePath = System.getProperty("user.dir");
    String osSeperator = System.getProperty("file.separator");
    String finalPath = absolutePath + osSeperator + "dates" + osSeperator + "dates.csv";
    if (checkDateFormat(date)) {
      LocalDate dateCur = LocalDate.parse(date);
      int compareDateToToday = dateCur.compareTo(dateToday);
      int compareDateToLast = dateCur.compareTo(lastHistoricDate);

      if (compareDateToLast > 0 && compareDateToToday <= 0) {
        ReadCSVs r = new ReadCSVs(finalPath);
        String[] data = r.getDataByDate(date);

        return data.length > 0;
      }
    }
    return false;
  }

  /**
   * This function checks if the String number is an integer that falls
   * inside the lower and higher limit (used in cases where the user enters
   * the serial number of the option he wants to choose).
   *
   * @param num         the number string to check
   * @param lowerLimit  the lower limit to check from
   * @param higherLimit the higher limit to check from
   */
  public static boolean checkValidNumberOption(String num, int lowerLimit, int higherLimit) {
    int n;
    try {
      n = Integer.parseInt(num);
    } catch (Exception e) {
      return false;
    }
    return n >= lowerLimit && n <= higherLimit;
  }

  /**
   * This function checks if the date entered is later than the date of the last transaction
   * to ensure a chronological order is maintained.
   *
   * @param date          the date to check
   * @param portfolioName the portfolio to check on
   * @param stockChoice   the particular stock data to compare dates of.
   */
  public static boolean checkDateChronology(String stockChoice, String date,
                                            String portfolioName) throws IOException {
    if (checkDateFormat(date)) {
      String absolutePath = System.getProperty("user.dir");
      String osSeperator = System.getProperty("file.separator");
      String p = absolutePath + osSeperator + "allUserPortfolios" + osSeperator;
      String path = p + "flexiblePortfolios/" + portfolioName + ".xml";
      WorkWithFileTypes w = new WorkWithXML(path, portfolioName);
      List<HashMap<String, String>> existingStocks = w.read();
      LocalDate inputDate = LocalDate.parse(date);
      LocalDate today = LocalDate.now();
      if (inputDate.compareTo(today) > 0) {
        return false;
      }
      for (HashMap<String, String> stock : existingStocks) {
        LocalDate stockDate = LocalDate.parse(stock.get("Date of transaction"));
        if (stockChoice.equals(stock.get("Stock ticker")) &&
                stockDate.compareTo(inputDate) > 0) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  /**
   * This function checks if a date comes before the creation date of a portfolio.
   *
   * @param date          the date to check
   * @param portfolioName the file to check on
   */
  public static boolean checkDateBeforeCreatedDate(String portfolioName, String date) {
    String absolutePath = System.getProperty("user.dir");
    String osSeperator = System.getProperty("file.separator");
    String p = absolutePath + osSeperator + "allUserPortfolios" + osSeperator;
    String path = p + "flexiblePortfolios/" + portfolioName + ".xml";
    WorkWithFileTypes xml = new WorkWithXML(path, portfolioName);
    LocalDate xmlCreatedDate = LocalDate.parse(xml.getFileCreationDate());
    LocalDate inputDate = LocalDate.parse(date);
    return inputDate.compareTo(xmlCreatedDate) < 0;
  }

  /**
   * This function checks if a date comes in the future.
   *
   * @param date          the date to check
   * @param portfolioName the file to check on
   */
  public static boolean checkDateAfterToday(String portfolioName, String date) {
    String absolutePath = System.getProperty("user.dir");
    String osSeperator = System.getProperty("file.separator");
    String p = absolutePath + osSeperator + "allUserPortfolios" + osSeperator;
    String path = p + "flexiblePortfolios/" + portfolioName + ".xml";
    LocalDate inputDate = LocalDate.parse(date);
    LocalDate today = LocalDate.now();
    return inputDate.compareTo(today) > 0;
  }

  /**
   * This function checks number is an integer.
   *
   * @param num the number to check
   */
  public static boolean checkIntegerValue(String num) {
    try {
      Integer.parseInt(num);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * This function checks if a sell operation can be carried out, according to previous
   * transactions.
   *
   * @param shares        the number of shares to sell
   * @param inputSymbol   the stock ticker
   * @param portfolioName the file to check on
   */
  public static boolean checkSellIsValid(String portfolioName, String shares, String inputSymbol) {
    String absolutePath = System.getProperty("user.dir");
    String osSeperator = System.getProperty("file.separator");
    String finalPath = absolutePath + osSeperator + "allUserPortfolios" + osSeperator
            + "flexiblePortfolios" + osSeperator;
    String path = finalPath + portfolioName + ".xml";
    WorkWithFileTypes xml = new WorkWithXML(path, portfolioName);
    double totalShares = 0;
    List<HashMap<String, String>> existingStocks = xml.read();
    for (HashMap<String, String> stock : existingStocks) {
      if (stock.get("Stock ticker").equals(inputSymbol)) {
        totalShares += Double.parseDouble(stock.get("Number of shares"));
      }
    }
    return totalShares >= Double.parseDouble(shares);
  }

  /**
   * This function checks if the dates are in valid interval.
   *
   * @param startingDate the start date of the time frame to check
   * @param endingDate   the end date of the time frame to check
   * @param interval     the required interval
   */
  public static boolean checkIntervalBetweenDates(String startingDate, String endingDate,
                                                  int interval) {
    LocalDate firstDate = LocalDate.parse(startingDate);
    LocalDate lastDate = LocalDate.parse(endingDate);
    long daysBetween = DAYS.between(firstDate, lastDate);
    return daysBetween >= interval;
  }

  public static boolean checkDateFormatWithQuitter(String date, String quitter){
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    format.setLenient(false);
    try {
      format.parse(date);
      return true;
    } catch (ParseException e) {
      return date.equalsIgnoreCase(quitter);
    }
  }
}
