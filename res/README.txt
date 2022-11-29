We have implemented a flexible and an inflexible portfolio in this assignment.

Following features are working in the respective portfolios.

Flexible Portfolio:
1) Allow a user to create one or more portfolios on a particular date(which is not in the future) with shares of one or more stocks.
2) Purchase stocks into a portfolio on a particular date from the list of supported stocks.
3) Sell stocks from a portfolio on a particular date from the list of supported stocks.
4) Examine the composition of a portfolio.
5) Find the total value of a portfolio on a particular date, if a date before the portfolio creation is entered, it returns 0.
6) Find the cost basis of a portfolio on a particular date.
7) Creates a performance chart of the value of a portfolio over the time period mentioned by the user. To accommodate both small and large ranges of data, the program chooses a base value, timestamps and scale dynamically.
8) Allows user to change the commission that is being charged on every purchase/sale of a stock. Commission can be set from the flexible portfolio menu so that the user does not have to manually enter the commission everytime they buy/sell a stock.
9) Save and retrieve files in XML format.
10) User can also enter input an xml file directly in the flexiblePortfolios/inflexiblePortfolios directory present in res folder and then work on those files in the program.
11) The application will be able to support new APIs that might be required in the future.

Inflexible Portfolio:
1) Allow a user to create one or more portfolio with shares of one or more stock. User cannot add or remove shares from portfolio after creating it.
2) Examine the compositions of a portfolio.
3) Determine the total value of a portfolio on certain date, provided it is in the date range supported by this program. 
4) Save to and retrieve portfolios from a file.
5) The program handles invalid data by asking the user to enter again or enter quit to not move ahead.
6) The program handles the case where user enters a holiday to get total value by asking them to enter a business day instead.
7) The program handles cases where the user enters a ticker and number of shares once and again enters the same ticker with number of shares later, by adding the number of shares entered each time and storing it in a single xml element of the ticker. 
8) The system supports both entering portfolio data manually in the terminal and entering portfolio as an XML file. 

