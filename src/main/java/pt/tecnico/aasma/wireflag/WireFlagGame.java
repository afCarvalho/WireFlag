package pt.tecnico.aasma.wireflag;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.controller.AgentController;
import pt.tecnico.aasma.wireflag.environment.controller.ClimateController;
import pt.tecnico.aasma.wireflag.environment.controller.EndGameController;
import pt.tecnico.aasma.wireflag.environment.controller.IController;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.environment.controller.ObjectController;
import pt.tecnico.aasma.wireflag.environment.controller.TimeController;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;

public class WireFlagGame extends BasicGame {

	private static IController[] elements;

	public WireFlagGame() {
		super("WireFlag");
	}

	public static void win(int teamId) {
		elements = new IController[] { EndGameController.getEnd(),
				ObjectController.getObjects() };
		EndGameController.getEnd().endGame(teamId);
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
		elements = new IController[] { MapController.getMap(),
				TimeController.getTime(), new ClimateController(),
				AgentController.getAgents(), ObjectController.getObjects() };

		AnimationLoader.getLoader().loadImages();

		for (IController e : elements) {
			e.init();
		}
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {

		for (IGameElement e : elements) {
			e.update(delta);
		}
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {

		for (IGameElement e : elements) {
			e.render(g);
		}
	}
}