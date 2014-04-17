package pt.tecnico.aasma.wireflag.environment.object;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class EndPoint implements GameElement {

	private Animation endPoint;
	private MapPosition flagPos;

	public EndPoint() {
	}

	public void init() throws SlickException {

		endPoint = new Animation(new Image[] { new Image("data/palace.png") },
				new int[] { 300 }, false);

		flagPos = MapController.getMap().getRandomPosition();

		while (MapController.getMap().isBlocked(flagPos)) {
			flagPos = MapController.getMap().getRandomPosition();
		}

		MapController.getMap().getLandscape(flagPos).setEndPoint(this);
	}

	public void render(Graphics g) {
		int tileWidth = MapController.getMap().getTileWidth();
		int tileHeight = MapController.getMap().getTileHeight();

		endPoint.draw(flagPos.getX() * tileWidth, flagPos.getY() * tileHeight);
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub

	}
}
