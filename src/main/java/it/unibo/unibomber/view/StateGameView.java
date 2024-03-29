package it.unibo.unibomber.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import it.unibo.unibomber.controller.api.GameLoop;
import it.unibo.unibomber.controller.impl.StateGame;
import it.unibo.unibomber.game.model.impl.StateGameButtonImpl;
import it.unibo.unibomber.utilities.Constants;

/**
 * Draw game pause view statement.
 */
public final class StateGameView implements GameLoop {
    private final StateGame controller;

    /**
     * @param controller State Game controller.
     */
    public StateGameView(final StateGame controller) {
        this.controller = controller;
    }

    @Override
    public void update() {
        for (final StateGameButtonImpl mb : controller.getButtons()) {
            mb.update();
        }
    }

    @Override
    public void draw(final Graphics g) {
        final Graphics2D g2;
        if (g instanceof Graphics2D) {
            g2 = (Graphics2D) g;
            g2.setColor(new Color(0, 0, 0, Constants.UI.Screen.getOpacity()));
            g2.fillRect(0, 0, Constants.UI.Screen.getgWidth(), Constants.UI.Screen.getgHeight());
            for (final StateGameButtonImpl mb : controller.getButtons()) {
                mb.draw(g);
            }
        }
    }

}
