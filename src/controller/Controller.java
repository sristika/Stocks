package controller;

import java.io.IOException;

import model.Portfolio;

/**
 * An interface that contains the functions of a controller.
 * The controller bridges the gap between a model and view.
 */
public interface Controller {

  /**
   * Method that starts the operation of the Stocks program from the controller.
   * It asks the view to show the Main menu which
   * contains all the functionalities that are supported by this program and
   * then handles whatever the user replies according to different scenarios.
   */
  void start(Portfolio portfolio) throws IOException;
}
