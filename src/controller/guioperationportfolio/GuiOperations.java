package controller.guioperationportfolio;

import modal.InvestmentStrategyImpl;

/**
 * Interface represents the GUI operation for the portfolio.
 */
public interface GuiOperations {

  /**
   * Method to execute the operation delegated to that particular implementation.
   *
   * @param fileName name of portfolio.
   * @param model    model object to be used.
   * @return return the result of operation as a string.
   */
  String executeOperation(String fileName, InvestmentStrategyImpl model);
}
