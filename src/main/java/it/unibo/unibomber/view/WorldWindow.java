package it.unibo.unibomber.view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import it.unibo.unibomber.utilities.UploadRes;

/**
 * Word Window class.
 */
public class WorldWindow {
  /**
   * Set default window settings.
   * 
   * @param unibomberPanel Panel of world
   */
  public WorldWindow(final WorldPanelImpl unibomberPanel) {
    final JFrame jframe = new JFrame();
    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jframe.add(unibomberPanel);
    final ImageIcon icon = new ImageIcon(UploadRes.getSpriteAtlas("menu/icon.png"));
    jframe.setTitle("Unibomber");
    jframe.setIconImage(icon.getImage());
    jframe.setLocationRelativeTo(null);
    jframe.setResizable(false);
    jframe.pack();
    jframe.setVisible(true);
    jframe.setFocusable(true);
    jframe.requestFocus();
    jframe.setLocationRelativeTo(null);
  }
}
