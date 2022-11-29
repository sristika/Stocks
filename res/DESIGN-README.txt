

Design changes made in Stocks(Part 2):

1. Added an abstract portfolio class that is extended by both flexible and inflexible classes.

	Justification: To minimise code duplication and to capture the common functionalities between both the types of portfolios.

2. Made minor modifications in getTotalValue function of inflexible portfolios.

	Justifications: To remove the coupling between the examinePortfolio and getTotalValue methods.

3. Shifted logic for accessing XML files to utility package.

	Justifications: This was done to isolate the method logic from IO logic, and to support multiple file types in future with minimal changes to the model.


We have implemented this assignment using the MVC architecture. 

Creating a portfolio, reading/writing portfolio to a file, examining the composition of a portfolio, get the total value of a portfolio are functions handled by the model of our design.

Main function delegates the control of the program to the controller.

The controller is responsible for reading inputs like the portfolio name, the stock ticker etc, validating them and deciding which part of the architecture the control flow goes to next.

The view is responsible for printing out the menu options, the portfolio compositions or any other warnings it gets from the controller.

The model is responsible for carrying out the work for all functionalities that are required for this assignment.



Current design -- 

We have two controllers, and based on the chosen portfolio type, the control is delegated from the main to the respective controller. 

The controllers call the appropriate methods in the model after validating all inputs(using the checks in the utility class from utility package).

The model has two interfaces, one for flexible and the other for inflexible portfolios. The common logic from both the portfolio types are abstracted into an abstract portfolio class. The functions in these classes use the Workwithfilestype interface to read and write to different kinds of data files (portfolio files). We have a separate class with all functions related to the Barchart, for computing portfolio performance chart. To accommodate both small and large ranges of data, the program chooses a base value, timestamps and scale dynamically.
All stocks are represented as stock objects of the stocks class. This class is also used to get the value of stocks at any date. It uses the GetDataFromSrc interface  from the utility package to get data from either local files, or fetch data from an API.

After doing everything behind-the-scenes, the model passes the result to the controller. The controller is then responsible for passing it ahead to the view to be displayed to the user.


Previous Design ---

The stocks that are available to a user are stored in the enum stockTicker.

We have utilized a Portfolio interface in the model package that defines the basic functionalities mentioned above that should be performed on a Portfolio. 
We make use of a class Stocks to store the detail of a particular stock like the stock ticker, date of creation and number of shares.
Portfolio takes in a list of Stocks objects. The value of these objects are stored in an XML file once the portfolio has been successfully created.
We chose to store it in an XML so that stocks in a portfolio can be stored in a proper hierarchy.
Once a file has been created, no edit operations are supported by the model on this file.

A Controller Interface in the controller package defines a start method from where the control of the program shifts entirely to the Controller. 
A controller takes in a Readable object and Appendable object which it utilized to fetch and pass on outputs to the view.
An implementation of this controller then communicates with the view and the model to intimate a user of the operations that are available to them. 
The controller also performs any validations on the data entered by the user.
When a new portfolio is being created, we are utilizing a HashSet so that a single portfolio does not contain repetitive of the same stock. Rather the number of shares entered gets added to the first instance of that stock.

A View Interface in the view package defines the methods that this program should display to the user. An implementation of this interface is responsible for notifying the user of the options available to them at any point.
Currently we are utilizing a text based user interface by which a user is intimated of operations they can perform. Output from a view is appended to the Appendable object.
The view also informs user of the type of data needed for each input, in case an incorrect input is passed, an error message is displayed to the user post which they have the option to enter the value again.

All functions and classes that need to be accessible from across the project (like checks and file readers) are present inside the utility package.

We are maintaining a CSV file with all dates that are valid from the project to make date checks.
