package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 * Class represents the creation of the frame for balancing a portfolio.
 */
public class BalancePortfolioFrame {

  JFrame bpf;
  JPanel panel;
  JLabel bplabel;

  /**
   * Constructor that sets JFrame of this class.
   */
  public BalancePortfolioFrame() {
    bpf = new JFrame();
    initComponents();
  }

  private void initComponents() {
    panel = new JPanel();
    bplabel = new JLabel();

    bpf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    bplabel.setText("Balance Portfolio");

  }

  /**
   * Method is a top level container which displays the window on the screen.
   *
   * @return the instance variable
   */
  public JFrame getFrame() {
    return bpf;
  }


}
