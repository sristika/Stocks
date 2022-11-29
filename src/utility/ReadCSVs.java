package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The ReadCSVs is an Utility class that can be used from anywhere in the program to read any CSV
 * in the path provided. The methods are kept public to allow access from all classes in the
 * project.
 */
public class ReadCSVs extends AbsGetDataFromSrc {
  private final String path;

  /**
   * The ReadCSVs constructor initializes the path on which it needs to read the file.
   *
   * @param path the path to read the file from
   */
  public ReadCSVs(String path) {
    this.path = path;
  }

  @Override
  public String[] getDataByDate(String date) throws IOException {
    BufferedReader reader = null;
    reader = new BufferedReader(new FileReader(path));
    return absGetDataByDate(reader, date);
  }
}
