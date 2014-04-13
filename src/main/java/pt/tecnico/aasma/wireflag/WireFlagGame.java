package pt.tecnico.aasma.wireflag;

import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.environment.Flag;
import pt.tecnico.aasma.wireflag.environment.controller.ClimateController;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.environment.controller.TimeController;

public class WireFlagGame extends BasicGame {

	private static final int MAP = 0;
	private static final int FLAG = 1;
	private static final int AGENT = 2;
	private static final int CLIMATE = 3;
	private static final int TIME = 4;

	private GameElement[] elements;

	public WireFlagGame() {
		super("Wired-Flag");
	}

	public static void main(String[] arguments) {
		try {
			int maxFPS = 100;
			AppGameContainer app = new AppGameContainer(new WireFlagGame());
			app.setDisplayMode(1400, 800, false);
			app.setTargetFrameRate(maxFPS);
			// app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		elements = new GameElement[] { MapController.getMap(), new Flag(), new Agent(),
				new ClimateController(), new TimeController() };

		for (GameElement e : elements) {
			e.init();
		}
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {

		for (GameElement e : elements) {
			e.update(delta);
		}
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {

		for (GameElement e : elements) {
			e.render(g);
		}
	}
}