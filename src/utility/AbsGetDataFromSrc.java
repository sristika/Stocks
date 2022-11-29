package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;

abstract class AbsGetDataFromSrc implements GetDataFromSource {

  String[] absGetDataByDate(BufferedReader reader, String dateString) throws IOException {
    String line = "";
    LocalDate dateCur = LocalDate.parse(dateString);

    while ((line = reader.readLine()) != null) {
      String[] lineArray = line.split(",");
      if (UtilityClass.checkDateFormat(lineArray[0])) {
        LocalDate dateFromSrc = LocalDate.parse(lineArray[0]);
        if (dateFromSrc.compareTo(dateCur) <= 0) {
          return lineArray;
        }
      }
    }
    return new String[0];
  }
}
