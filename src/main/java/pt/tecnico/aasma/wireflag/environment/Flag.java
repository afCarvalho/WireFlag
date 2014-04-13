package pt.tecnico.aasma.wireflag.environment;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;

public class Flag implements GameElement {

	private Animation flag;
	private Animation fire;
	private int xCoord;
	private int yCoord;

	public Flag() {
	}

	public void init() throws SlickException {

		Random random = new Random();

		flag = new Animation(new Image[] { new Image("data/SmallFlag.png") },
				new int[] { 300 }, false);
		fire = new Animation(new Image[] { new Image("data/fire.png") },
				new int[] { 300 }, false);

		xCoord = random.nextInt(MapController.getMap().getMapWidth());
		yCoord = random.nextInt(MapController.getMap().getMapHeight());

		while (MapController.getMap().isBlocked(xCoord, yCoord)) {
			xCoord = random.nextInt(MapController.getMap().getMapWidth());
			yCoord = random.nextInt(MapController.getMap().getMapHeight());

		}

		MapController.getMap().getLandscape(xCoord, yCoord).setFlag(this);
	}

	public void render(Graphics g) {
		flag.draw(xCoord * 1.0f, yCoord * 1.0f);
		fire.draw(xCoord * 1.0f, yCoord * 1.0f);
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub

	}
}
