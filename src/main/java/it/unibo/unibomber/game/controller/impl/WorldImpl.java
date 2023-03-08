package it.unibo.unibomber.game.controller.impl;

import java.awt.Graphics;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.api.World;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.utilities.Constants;

public class WorldImpl implements World,Runnable, GameLoop{
    
	private WorldPanelImpl unibomberPanel;
	private Menu menu;
	private Thread g_Thread;

    public WorldImpl(){
		initClasses();
       	unibomberPanel = new WorldPanelImpl(this);
		new WorldWindow(unibomberPanel);
		unibomberPanel.requestFocus();
		startGameLoop();
    }

	private void initClasses() {
		menu = new Menu(this);
	}

	private void startGameLoop() {
		g_Thread = new Thread(this);
		g_Thread.start();
	}
	
	@Override
	public void update() {
		switch (Gamestate.state) {
		case MENU:
			menu.update();
			break;
		case PLAY:
			break;
		case OPTIONS:
		case QUIT:
		default:
			System.exit(0);
			break;
		}
	}

	@Override
	public void draw(Graphics g){
		switch (Gamestate.state) {
			case MENU:
				menu.draw(g);
				break;
			case PLAY:
				break;
			default:
				break;
		}
	}

	@Override
	public void run() {double timePerFrame = 1000000000.0 / Constants.UI.GameLoop.FPS_SET;

		long previousTime = System.nanoTime();

		int frames = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaF = 0;

		while (true) {
			long currentTime = System.nanoTime();

			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			if (deltaF >= 1) {
				update();
				unibomberPanel.repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
	}

	@Override
	public Menu getMenu() {
		return menu;
	}
	
}