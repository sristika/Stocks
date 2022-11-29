package utility;

import java.io.IOException;

/**
 * This is an Utility interface whose functions can be used from anywhere in the program to read
 * any data source in the path provided. The methods are kept public to allow access from
 * all classes in the project.
 */
public interface GetDataFromSource {

  /**
   * The getDataByDate function accepts a date String and checks if there is any data
   * against that particular date, and returns a string array of the data or empty array
   * if the data was not found.
   *
   * @param date the date to get data from
   */
  String[] getDataByDate(String date) throws IOException;
}
