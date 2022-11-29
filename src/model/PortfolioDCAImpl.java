package model;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import enums.stockTicker;
import utility.WorkWithFileTypes;
import utility.WorkWithXML;


public class PortfolioDCAImpl extends PortfolioFlexible{

  private String strategyName;

  public void setStrategyName(String name){
    this.strategyName = name;
  }



  public void createDCAStrategy(int total, HashMap<String, Integer> proportions, String start,
                                String end, int days){
    System.out.println(super.portfolioName);
    WorkWithFileTypes w = new WorkWithXML(super.finalPath + super.portfolioName + "_DCA_"
            +strategyName + ".xml",
            super.portfolioName + "_DCA_" +strategyName +".xml");

    w.createDCAFile(total, proportions, start, end, days);
  }



  public void updateDCAStrategyToPortfolio() throws IOException {
    File dir = new File(super.finalPath);
//    System.out.println();
    List<String> fileNames = new ArrayList<>();

    LocalDate dateObj = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String date = dateObj.format(formatter);

    for(File file : Objects.requireNonNull(dir.listFiles())) {
      if(file.getName().startsWith(super.portfolioName + "_DCA_")){
        fileNames.add(file.getName());
      }
    }
    if(fileNames.size() > 0){
      for(String fileName : fileNames){
        WorkWithFileTypes w = new WorkWithXML(super.finalPath + fileName,
                fileName);
        HashMap<String, String> h = w.readDCA();
        String startDate = w.readDCAStart();
        String endDate = w.readDCAEnd();
        String lastUpdated = w.readLastUpdated();
        String freq = w.readDCAFreq();
        String finished = w.readFinished();

        if(finished.equals("false")){
          LocalDate start = lastUpdated.equals("NA")? LocalDate.parse(startDate):
                  LocalDate.parse(lastUpdated);
          LocalDate end = LocalDate.parse(date).isBefore(LocalDate.parse(endDate))?
                  LocalDate.parse(date): LocalDate.parse(endDate);

          List<LocalDate> dates = new ArrayList<>();
          if(start.isEqual(end)){
            dates.add(start);
          }else{
            dates = getDateList(start, end, Integer.parseInt(freq));
          }
          List<HashMap<String, String>> allPurchases = new ArrayList<>();
          for(LocalDate d: dates){
            for(Map.Entry<String, String> i: h.entrySet()){
              HashMap<String, String> stockData = new HashMap<>();
              stockData.put("Date", String.valueOf(d));
              stockData.put("Stock-ticker", i.getKey());
              Stocks stock = new Stocks(stockTicker.valueOf(i.getKey()), 0);
              stock.fillStockData(String.valueOf(d), true);
              stockData.put("Number-of-shares", String.valueOf(Double.parseDouble(i.getValue())/
                      Double.parseDouble(stock.getValueOfShare())));
              allPurchases.add(stockData);
            }
          }
          super.purchaseStocks(allPurchases, super.portfolioName);
          w.setDCALastUpdated(date);
          if(LocalDate.parse(date).isAfter(LocalDate.parse(endDate))){
            w.setFinished(true);
          }
        }
      }
    }
  }

  private List<LocalDate> getDateList(LocalDate start, LocalDate end, int freq){
    LocalDate temp = start;
    List<LocalDate> list = new ArrayList<>();
    while (temp.isBefore(end)){
      list.add(temp);
      temp = temp.plusDays(freq);
    }
    return list;
  }
}
