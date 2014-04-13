package pt.tecnico.aasma.wireflag.environment;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.GameElement;

public class Flag implements GameElement {

	private Animation flag;
	private Animation fire;
	private int xCoord;
	private int yCoord;

	public Flag() {
	}

	public void update(int delta) {
	}

	@Override
	public void init() throws SlickException {

		Random random = new Random();

		flag = new Animation(new Image[] { new Image("data/SmallFlag.png") },
				new int[] { 300 }, false);
		fire = new Animation(new Image[] { new Image("data/fire.png") },
				new int[] { 300 }, false);

		xCoord = random.nextInt(Map.getMap().getMapWidth());
		yCoord = random.nextInt(Map.getMap().getMapHeight());

		while (Map.getMap().isBlocked(xCoord, yCoord)) {
			xCoord = random.nextInt(Map.getMap().getMapWidth());
			yCoord = random.nextInt(Map.getMap().getMapHeight());

		}
	}

	@Override
	public void render(Graphics g) {
		flag.draw(xCoord * 1.0f, yCoord * 1.0f);
		fire.draw(xCoord * 1.0f, yCoord * 1.0f);
	}
}
