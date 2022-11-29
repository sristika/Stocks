package utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This interface represents the input/output operations that the program carries
 * out on different file types.
 */
public interface WorkWithFileTypes {

  /**
   * This function is used to create a any portfolio.
   */
  void create(ArrayList<HashMap<String, String>> stockData, String creationDate);

  /**
   * This function is used to read data from a portfolio.
   */
  List<HashMap<String, String>> read();

  /**
   * This function is used to read the creation date of the file.
   */
  String getFileCreationDate();

  /**
   * This function is used to update data on a portfolio file.
   */
  boolean update(List<HashMap<String, String>> stocks);

  HashMap<String, String> readDCA();

  String readDCAStart();

  String readDCAEnd();

  String readLastUpdated();

  String readDCAFreq();

  String readFinished();

  void setDCALastUpdated(String date);

  void setFinished(boolean finished);

  void createDCAFile(int total, HashMap<String, Integer> proportions, String start, String end, int days);
}
