package model;

import java.io.IOException;

import enums.stockTicker;
import utility.ReadCSVs;
import utility.ReadFromAlphaVantage;

/**
 * This class represents a stock at a particular date.
 */
public class Stocks {
  stockTicker stockSymbol;
  String date;
  double numberOfShares;
  String valueOfShare;

  /**
   * Public constructor that takes in two parameters to create
   * a new Stocks object.
   *
   * @param ticker         the ticker value of this stock or the symbol of the stock.
   * @param numberOfShares number of shares of this stock.
   */
  public Stocks(stockTicker ticker, double numberOfShares) {
    this.stockSymbol = ticker;
    this.numberOfShares = numberOfShares;
  }

  /**
   * Method that fetches the data of a stock from a file and returns true if
   * data was found and false otherwise.
   */
  public boolean fillStockData(String date, boolean flexible) throws IOException {
    String[] data;
    if (!flexible) {
      String absolutePath = System.getProperty("user.dir");
      String osSeperator = System.getProperty("file.separator");
      String finalPath = absolutePath + osSeperator + "stocksData_csv" + osSeperator + "daily_"
              + this.stockSymbol + ".csv";

      ReadCSVs read = new ReadCSVs(finalPath);
      data = read.getDataByDate(date);
    } else {
      ReadFromAlphaVantage read =
              new ReadFromAlphaVantage(this.getTicker(), "TIME_SERIES_DAILY");
      data = read.getDataByDate(date);
    }

    if (data.length == 6) {
      this.setDate(date);
      this.setValueOfShare(data[4]);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Method that fetches the stocksymbol of the object.
   */
  String getTicker() {
    return this.stockSymbol.toString();
  }

  /**
   * Method that fetches the number of shares of the stock object.
   */
  double getNumberOfShares() {
    return this.numberOfShares;
  }

  /**
   * Method that fetches the value of the current share.
   */
  String getValueOfShare() {
    return this.valueOfShare;
  }

  private void setValueOfShare(String value) {
    this.valueOfShare = value;
  }

  /**
   * Method that fetches the current date as on which the data
   * of the stock is available.
   */
  String getDate() {
    return this.date;
  }

  private void setDate(String date) {
    this.date = date;
  }

}
