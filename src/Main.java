import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import controller.Controller;
import controller.ControllerFlexible;
import controller.ControllerImpl;
import model.PortfolioFlexible;
import model.PortfolioImpl;
import utility.UtilityClass;

/**
 * This is the driver program that delegates control to the controller.
 */
public class Main {
  /**
   * Main function of the program that transfer the control
   * to the controller.
   *
   * @param args default parameter
   */
  public static void main(String[] args) throws IOException {
    Readable in = new InputStreamReader(System.in);
    Appendable out = System.out;
    Scanner scan = new Scanner(in);
    boolean run = true;
    while (run) {
      out.append("\nWhat kind of portfolio do you want to work with? " +
              "Enter integer number choice:\n\n");
      out.append("1. Inflexible Portfolio\n");
      out.append("2. Flexible Portfolio\n");
      out.append("3. Exit from program\n");
      out.append("\nEnter a whole number choice from the options provided above:\n");
      String stringChoice = scan.next();
      while (!UtilityClass.checkIntegerValue(stringChoice)) {
        out.append("Enter a valid integer whole number.\n");
        stringChoice = scan.next();
      }

      int choice = Integer.parseInt(stringChoice);

      switch (choice) {
        case 1: {
          Controller controller = new ControllerImpl(in, out);
          try {
            controller.start(new PortfolioImpl());
          } catch (IOException e) {
            System.out.append(e.getMessage());
          }
          break;
        }

        case 2: {
          ControllerFlexible flexibleController = new ControllerFlexible(in, out);
          try {
            flexibleController.start(new PortfolioFlexible());
          } catch (IOException e) {
            System.out.append(e.getMessage());
          }
          break;
        }

        case 3: {
          run = false;
          break;
        }

        default: {
          out.append("Invalid choice number entered.");
        }

      }
    }
  }
}
