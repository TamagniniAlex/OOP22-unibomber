package it.unibo.unibomber.game.view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Word Window class.
 */
public class WorldWindow {
  private final JFrame jframe;

  /**
   * Set default window settings.
   * 
   * @param unibomberPanel
   */
  public WorldWindow(final WorldPanelImpl unibomberPanel) {
    jframe = new JFrame();
    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jframe.add(unibomberPanel);
    ImageIcon icon = new ImageIcon("./src/main/res/icon.png");
    jframe.setTitle("Unibomber");
    jframe.setIconImage(icon.getImage());
    jframe.setLocationRelativeTo(null);
    jframe.setResizable(false);
    jframe.pack();
    jframe.setVisible(true);
    jframe.setLocationRelativeTo(null);
  }
}
