package model;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

/**
 * This is the class that contains all the functions related to producing the performance chart
 * of a portfolio.
 */
public class PortfolioPerformanceChart {

  private final String startString;
  private final String endString;
  private final String pName;

  /**
   * This constructor initialises the start date, end date and portfolio name of the portfolio
   * that we need to find performance of.
   */
  public PortfolioPerformanceChart(String startString, String endString, String portfolioName) {
    this.startString = startString;
    this.endString = endString;
    this.pName = portfolioName;
  }

  private List<Double> getTotalValues(List<LocalDate> dateArray) throws IOException {
    List<Double> totalValues = new ArrayList<>();
    PortfolioFlexibleInterface p = new PortfolioFlexible();
    p.setPortfolioName(pName);
    for (LocalDate d : dateArray) {
      totalValues.add(p.getTotalValue(pName, String.valueOf(d)));
    }
    return totalValues;
  }

  /**
   * This function handles the creation flow of the barchart and calls all the private
   * functions that handle the different parts of the creation process.
   */
  public StringBuilder createBarchart()
          throws IOException {
    StringBuilder res = new StringBuilder();
    LocalDate start = LocalDate.parse(startString);
    LocalDate end = LocalDate.parse(endString);

    long daysDiff = DAYS.between(start, end);
    List<LocalDate> dateArray;

    if (daysDiff > 5 * 365) {
      dateArray = yearly(start, end);
    } else if (daysDiff > 30 && daysDiff <= 2 * 30) {
      dateArray = byWeeklyList(start, end);
    } else if (daysDiff > 2 * 30 && daysDiff <= 6 * 30) {
      dateArray = weeklyList(start, end);
    } else if (daysDiff > 6 * 30 && daysDiff <= 365 * 2) {
      dateArray = monthlyList(start, end);
    } else if (daysDiff > 365 * 2 && daysDiff <= 5 * 365) {
      dateArray = quarterlyList(start, end);
    } else {
      dateArray = dailyList(start, end);
    }
    List<Double> totalValues = getTotalValues(dateArray);
    Double max = (double) 0;
    Double min = (double) Integer.MAX_VALUE;
    for (Double i : totalValues) {
      if (i > max) {
        max = i;
      }
      if (i < min) {
        min = i;
      }
    }
    int scale = ((int) (max - min)) / 50;
    if (max - min == 0) {
      scale = (int) (max / 50);
    }
    if (scale == 0) {
      scale = 1;
    }


    List<Integer> stars = getStars(scale, totalValues, min, max);
    List<String> timestamp = getTimestamp(dateArray);


    if (stars.size() > 0) {
      res.append("Performance of portfolio ").append(pName).append(" from ").append(start).
              append(" to ").append(end).append("\n\n");
      int i = 0;
      for (String date : timestamp) {
        res.append(date).append(" : ");
        for (int j = 0; j < stars.get(i); j++) {
          res.append("*");
        }
        res.append("\n");
        i++;
      }
      res.append("\n");
      res.append("Scale: * = $").append(scale);
      if (min - scale > 100) {
        res.append("  Base: $").append(Math.round(min - scale));
      }
    }
    return res;
  }

  private List<Integer> getStars(int scale, List<Double> totalValues, Double min, Double max) {
    Double base = min - scale;
    List<Integer> stars = new ArrayList<>();
    if (base > 100) {
      for (Double totalValue : totalValues) {
        stars.add((int) Math.ceil((totalValue - base) / scale));
      }
    } else {
      for (Double totalValue : totalValues) {
        stars.add((int) Math.ceil((totalValue) / scale));
      }
    }
    return stars;
  }

  private List<LocalDate> dailyList(LocalDate start, LocalDate end) {
    List<LocalDate> list = new ArrayList<>();
    while (!start.isAfter(end)) {
      list.add(start);
      start = start.plusDays(1);
    }

    return list;
  }

  private List<LocalDate> byWeeklyList(LocalDate start, LocalDate end) {
    List<LocalDate> list = new ArrayList<>();
    LocalDate day = start;
    while (day.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY)).compareTo(end) < 0 &&
            day.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).compareTo(end) < 0) {
      day = day.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
      list.add(day);
      day = day.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
      list.add(day);
    }
    return list;
  }

  private List<LocalDate> weeklyList(LocalDate start, LocalDate end) {
    LocalDate weekEnd = start;
    List<LocalDate> list = new ArrayList<>();
    while (weekEnd.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).compareTo(end) < 0) {
      weekEnd = weekEnd.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
      list.add(weekEnd);
    }
    return list;
  }

  private List<LocalDate> monthlyList(LocalDate start, LocalDate end) {
    LocalDate monthEnd = start;
    List<LocalDate> list = new ArrayList<>();

    while (monthEnd.withDayOfMonth(
            monthEnd.getMonth().length(monthEnd.isLeapYear())).compareTo(end) < 0) {
      monthEnd = monthEnd.withDayOfMonth(
              monthEnd.getMonth().length(monthEnd.isLeapYear()));
      list.add(monthEnd);
      monthEnd = monthEnd.plusDays(10);
    }
    return list;
  }

  private List<LocalDate> quarterlyList(LocalDate start, LocalDate end) {
    LocalDate quarterEnd = start.with(TemporalAdjusters.lastDayOfMonth());
    List<LocalDate> list = new ArrayList<>();
    list.add(quarterEnd);
    while (quarterEnd
            .plusMonths(3)
            .with(TemporalAdjusters.lastDayOfMonth()).compareTo(end) < 0) {
      quarterEnd = quarterEnd
              .plusMonths(3)
              .with(TemporalAdjusters.lastDayOfMonth());
      list.add(quarterEnd);
    }
    return list;
  }

  private List<LocalDate> yearly(LocalDate start, LocalDate end) {
    LocalDate yearEnd = start.with(lastDayOfYear());
    List<LocalDate> list = new ArrayList<>();
    list.add(yearEnd);
    while (yearEnd.plusDays(5).with(lastDayOfYear()).compareTo(end) < 0) {
      yearEnd = yearEnd.plusDays(5).with(lastDayOfYear());
      list.add(yearEnd);
    }
    return list;
  }


  private List<String> getTimestamp(List<LocalDate> dateArray) {
    List<String> timestamps = new ArrayList<>();

    for (LocalDate d : dateArray) {
      DateTimeFormatter dateFormatter
              = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
      timestamps.add(d.format(dateFormatter));
    }
    return timestamps;
  }
}
