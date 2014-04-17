package pt.tecnico.aasma.wireflag.environment;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Flag implements GameElement {

	private Animation flag;
	private MapPosition flagPosition;

	public Flag() {
	}

	public void init() throws SlickException {

		flag = new Animation(new Image[] { new Image(System.getProperty("data")
				+ "SmallFlag.png") }, new int[] { 300 }, false);

		flagPosition = MapController.getMap().getRandomPosition();

		while (MapController.getMap().isBlocked(flagPosition)) {
			flagPosition = MapController.getMap().getRandomPosition();
		}

		MapController.getMap().getLandscape(flagPosition).setFlag(this);
	}

	public void render(Graphics g) {
		int tileWidth = MapController.getMap().getTileWidth();
		int tileHeight = MapController.getMap().getTileHeight();

		flag.draw(flagPosition.getX() * tileWidth, flagPosition.getY()
				* tileHeight);
	}

	@Override
	public void update(int delta) {
	}
}
