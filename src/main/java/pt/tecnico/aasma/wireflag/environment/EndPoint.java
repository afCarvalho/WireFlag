package pt.tecnico.aasma.wireflag.environment;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;

public class EndPoint implements GameElement {

	private Animation endPoint;
	private int xCoord;
	private int yCoord;

	public EndPoint() {
	}

	public void init() throws SlickException {

		Random random = new Random();

		endPoint = new Animation(
				new Image[] { new Image("data/palace.png") },
				new int[] { 300 }, false);

		xCoord = random.nextInt(MapController.getMap().getMapWidth());
		yCoord = random.nextInt(MapController.getMap().getMapHeight());

		while (MapController.getMap().isBlocked(xCoord, yCoord)) {
			xCoord = random.nextInt(MapController.getMap().getMapWidth());
			yCoord = random.nextInt(MapController.getMap().getMapHeight());

		}

		MapController.getMap().getLandscape(xCoord, yCoord, true)
				.setEndPoint(this);
	}

	public void render(Graphics g) {
		endPoint.draw(xCoord * 1.0f, yCoord * 1.0f);
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub

	}
}
