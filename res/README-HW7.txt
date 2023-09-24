--Swing library is used to produce Graphical User Interface.


Steps to run the Jar file:

1.  Please copy and paste the data folder from res folder to "c:/users/<username>" folder in your PC.
    And place the jar file in the same location as the data folder.
	  For example, in my PC, the folder is "c:/users/vinoothna"

2. Once, the data folder and the jar file is available in above mentioned folder,
 Please open command prompt.

3. In command prompt, type the command java -jar <location of Jar file> and press enter to run the Jar file.

	OR

   To run the code directly from IntelliJ, click on 'MainClass' and hit RUN.

4. Once you run successfully, user will see a menu with 2 operations.
   Choose either a CLI or a GUI for further operations.

5. If you choose option 2, A GUI frame will be displayed with following menu options:

       1. Create Portfolio
       2. Buy Shares on a Specific Date
       3. Sell Shares on a Specific Date
       4. View Composition of a Portfolio
       5. Value of a Current Portfolio
       6. Value of a Portfolio on a Specific Date
       7. Cost Basis of a Portfolio
       8. Get a Copy of Current Portfolio
       9. Invest Fixed Amount of Existing Portfolio
       10. Dollar-Cost Averaging
	 11. Balance Portfolio on a specific date
       12. Quit

Below are the steps of how input and output looks like to be able to successfully perform the above
operations:


To Create Portfolio

    1. Click on Create Portfolio Button.
    2. Enter portfolio name (Only alphabets) in the input box and press OK.
    3. Enter the
        i.)  date in YYYY-MM-DD format
        ii.) tickers in the format AA,IBM,MSFT (Comma separated and no spaces. Enter valid ticker values)
        iii.)Shares in the corresponding order of the tickers entered above. 30,20,50.
        iv.) Commission Fee (integer value greater than zero.)
    4. Click save button.

To Rebalance a Portfolio:
	1. Create a new portfolio first or make sure there is an existing portfolio in /data folder.
	2. Choose Rebalance portfolio on a specific date.
	3. Enter the name of the portfolio you want to balance.
	4. Enter the date on which you want to rebalance the portfolio.
	(At this point we fetch the current composition of the portfolio. The code we received also fetches 
	the price of the stock from API while doing this. Due to which there might be a slight wait time, 
	depending on the number of stocks in the portfolio.)
	5. You will be shown the ticker of the stocks present in the portfolio.
	6. Enter the respective weightage for all the stocks, according to which they should be balanced.
	7. After all the weightages have been entered, the model will then perform rebalancing. During this 
	time there might not be a window open on the screen. Please be patient till the main menu window opens again.
	8. Click on view composition and enter the portfolio name and a date on or after the rebalance date.
	9. You should now be able to see the rebalanced composition of the portfolio.



As part of Assignment 7 we were able to complete the feature of rebalancing the portfolio through the GUI and the CLI
as well.Rebalancing uses the existing team's buy and sell functions to perfrom it's operation. 

The existing buy and sell operations suffer from a limitation that if these are performed on a weekend, and if the API 
doesn't contain data for a date after that weekend, then these operations return a NaN value. Since rebalancing utilized 
existing buy and sell functionality, it suffers from this design limitation as well.

NOTE: Rebalancing will still work as expected for past weeekend dates or any weekdays, as long as the API is able to
fetch data for that date(or a later date).


Changes made to implement rebalancing of a portfolio:

Changes made in model package:
	1. FlexiblePortfolioModal.java (Interface) - Added a void function "balancePortfolioModel" that is utilized for 
		performing the operations on balancing a portfolio.
	2. FlexiblePortfolioModalImpl.java - Added implementation of the method "balancePortfolioModel" written in the 
		interface. This method performs all the operations in the model related to rebalancing a portfolio.

Changes in controller package:
	3. Adapter.java - Added a new command that creates a BalancePortfolio type object to perform rebalancing
		operations.
	4. BalancePortfolio.java - Added a new class that performs all the controller operations of rebalancing 
		a portfolio.
	5. Features.java (Interface) - Added a new method "balancePortfolio" that works as a feature for any GUI.
	6. GraphicalControllerImpl.java - Added implementation of "balancePortfolio" method overriden from the 
		Features interface. This method is called by controller of a GUI to rebalance a portfolio.

Changes in view package:
	1. BalancePortfolioFrame.java - A new class that represents a frame of the rebalance portfolio GUI screen.
	2. FlexiblePortfolioView.java (Interface)- Added methods "displayBalanceTicker" and "displayTotalStocksInComposition".
	3. FlexiblePortfolioViewImpl.java - Implemented the additional methods defined in FlexiblePortfolioView interface.
	4. GraphicalView.java - Added action listener for balance portfolio.
	5. MainMenuFrame.java - Added a button to the main menu which calls the action listener for balancing portfolio.

None of the existing code provided to us by the team needed to be changes. We have only utilized exisiting functions or 
added new ones where necessary.