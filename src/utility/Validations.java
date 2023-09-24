package utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to perform validations on input data.
 */
public final class Validations {

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
   * This function checks all the percentages add up to 100.
   *
   * @param stockslist details of stocks.
   */
  public static boolean checkValidTotalPercent(HashMap<String, Integer> stockslist) {
    int sum = 0;
    for (Map.Entry<String, Integer> entry : stockslist.entrySet()) {
      sum = sum + entry.getValue();
    }
    return sum == 100;
  }
}
