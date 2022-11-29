Please note that the jar file Stocks.jar needs to be in the same directory as the below folders
1. allUserPortfolios
2. dates
3. stocksData_csv
4. Commission.txt


Instruction on how to run the program to create a flexible portfolio, purchase 3 stocks on different dates, examine the total value of the portfolio and cost basis of the portfolio:

1. Open terminal on the folder which contains the jar file.
2. Type →      java -jar Stocks.jar
3. The console will show a menu. Choose option 2 to work with a flexible portfolio.
4. Console will provide you with different options supported by this flexible portfolio.
5. Enter the name of the Portfolio (Must be a name that does not exist already). 
	Enter Port1
6. Enter the date you want to create this portfolio on in YYYY-MM-DD format. 
	Enter 2022-06-10
7. You will be shown a list of all stocks with their Ticker symbols. You can also enter Quit if you do not want to proceed. 
	Enter any stock Ticker correctly(eg: GOOGL).
8. Enter the number of shares you want to buy. 
	Enter 10.
9. Enter quit.

10. You will be taken to the flexible portfolio menu. Choose option to purchase shares. 
	Enter 2.
11. Enter the name of portfolio you want to purchase stocks in. 
	Enter Port1.
12. You will be shown a list of all stocks with their Ticker symbols. 
	Enter MSFT.
13. Enter the number of shares you want to buy. 
	Enter 5.
14. Enter the date you want to buy this share on. 
	Enter 2022-06-14.
15. You will be shown a list of all stocks with their Ticker symbols. You can also enter Quit if you do not want to proceed. 
	Enter AAPL.
16. Enter the number of shares to buy. 
	Enter 12.
17. Enter the date to buy on. 
	Enter 2022-06-16
18. You will be shown a list of all stocks with their Ticker symbols. You can also enter Quit if you do not want to proceed. 
	Enter NFLX.
19. Enter the number of shares you want to buy. 
	Enter 20.
20. Enter the date you want to buy this share on. 
	Enter 2022-07-07
21. Enter Quit.

22. We choose the option to get the total value of the portfolio on a specified date.
23. 	Enter 5.
24. Enter the name of portfolio. 
	Enter Port1
25. Enter date to compute value on in YYYY-MM-DD format. 
	Enter 2022-07-13
26. Total value of the portfolio will be displayed.

23. We choose the option to calculate the cost basis of a portfolio on a specified date.
24. 	Enter 6.
25. Enter the name of portfolio. 
	Enter Port1
26. Enter a date to calculate cost basis on. 
	Enter 2022-07-21
27. Cost basis of the portfolio will be displayed.

28. Enter 9 to exit to the flexible portfolio menu.
29. Enter 3 to exit from the program.

Below is the list of stocks that the program supports:


1. Microsoft( Ticker: MSFT) 
2. Google( Ticker: GOOGL) 
3. Apple( Ticker: AAPL) 
4. IBM( Ticker: IBM) 
5. Morgan Stanley( Ticker: MS) 
6. Amazon.com Inc( Ticker: AMZN) 
7. Bank Of America Corp( Ticker: BAC) 
8. DoorDash Inc - Class A( Ticker: DASH) 
9. Etsy Inc( Ticker: ETSY) 
10. JPMorgan Chase & Company( Ticker: JPM) 
11. MongoDB Inc - Class A( Ticker: MDB) 
12. Netflix Inc( Ticker: NFLX) 
13. U.S. Gold Corp( Ticker: USAU) 
14. Vodafone Group plc( Ticker: VOD) 
15. Warner Bros. Discovery Inc - Class A( Ticker: WBD) 


Restrictions: 
Dates supported : All days from the past 20 years are accepted. However, transactions can be made in chronological order only.

Please note that performance chart can be created only for spans more than or equal to 5 days.

There is no caching implemented as of now, so functionalities involving fetching data from API causes slight delay in outputting results.


----------------


Instructions on how to run the program for an inflexible portfolio. 
To create a portfolio with 3 different stocks, a second portfolio with 2 different stocks and query their value on a specific date – 


1. Open terminal on the folder which contains the jar file.
2. Type →      java -jar Stocks.jar
3. The console will show a menu. Choose option 1 to work with an inflexible portfolio.

3. The console will show a menu
  What are you looking for today? 
1. Create an inflexible portfolio. 
2. View composition of a portfolio. 
3. Get total value of an inflexible portfolio on a specified date. 
4. Exit the program 


Choose an option number: 
4. Enter 1.
5. Enter the name of the Portfolio (Must be a name that does not exist already). Enter Port1
6. You will be shown a list of all stocks with their Ticker symbols. You can also enter Quit if you do not want to proceed. Enter any stock Ticker correctly.


   1. Type the stock TICKER
   2. Microsoft( Ticker: MSFT) 
   3. Google( Ticker: GOOGL) 
   4. Apple( Ticker: AAPL) 
   5. IBM( Ticker: IBM) 
   6. Morgan Stanley( Ticker: MS) 
   7. Amazon.com Inc( Ticker: AMZN) 
   8. Bank Of America Corp( Ticker: BAC) 
   9. DoorDash Inc - Class A( Ticker: DASH) 
   10. Etsy Inc( Ticker: ETSY) 
   11. JPMorgan Chase & Company( Ticker: JPM) 
   12. MongoDB Inc - Class A( Ticker: MDB) 
   13. Netflix Inc( Ticker: NFLX) 
   14. U.S. Gold Corp( Ticker: USAU) 
   15. Vodafone Group plc( Ticker: VOD) 
   16. Warner Bros. Discovery Inc - Class A( Ticker: WBD) 
   17. Finished adding stocks, create portfolio now. (Type Quit)


        (Enter MSFT)


7. Enter the number of shares of that stock. 
(Enter 10)
8. You will be shown the same list of tickers again. Keep entering stock Tickers and number of shares until you are done. Enter QUIT when done.


9.  You will see a message saying “Successfully created portfolio.”.
10.  The program will show you the initial menu again. Carry out the same steps to create a new portfolio will 2 different stocks.
11.  Enter quit to create portfolio.
12.  You will be shown the initial menu again, enter ‘3’ to get the value of a portfolio by date.
13. Enter a name of an existing portfolio of which you want to get the value of.
14. Enter a valid date in YYYY-MM-DD format between 2022-06-13 and 2022-11-02 (Must be a business day).
15. You will be shown the initial menu of inflexible portfolio again. Enter 4 to quit from this.
16. Enter 3 to exit from the program.


Below is the list of stocks that the program supports:


1. Microsoft( Ticker: MSFT) 
2. Google( Ticker: GOOGL) 
3. Apple( Ticker: AAPL) 
4. IBM( Ticker: IBM) 
5. Morgan Stanley( Ticker: MS) 
6. Amazon.com Inc( Ticker: AMZN) 
7. Bank Of America Corp( Ticker: BAC) 
8. DoorDash Inc - Class A( Ticker: DASH) 
9. Etsy Inc( Ticker: ETSY) 
10. JPMorgan Chase & Company( Ticker: JPM) 
11. MongoDB Inc - Class A( Ticker: MDB) 
12. Netflix Inc( Ticker: NFLX) 
13. U.S. Gold Corp( Ticker: USAU) 
14. Vodafone Group plc( Ticker: VOD) 
15. Warner Bros. Discovery Inc - Class A( Ticker: WBD) 



Dates supported : All business days  between 2022-06-13 and 2022-11-02 are supported for an inflexible portfolio.
Please note that the program assumes that today’s date is 2022-11-02 for an inflexible portfolio (Due to limited data stored in the project data files.)


The HTML Documentation is present inside the docs zip file.
