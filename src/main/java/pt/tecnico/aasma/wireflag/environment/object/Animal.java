package pt.tecnico.aasma.wireflag.environment.object;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.IGameElement;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Animal implements IGameElement {

	private int protein;
	private Random random;
	private Animation goat;
	private Animation pig;
	private MapPosition animalPos;
	private int type;
	private final int PIG = 0;
	private final int GOAT = 1;

	public Animal(MapPosition pos) {
		random = new Random();
		protein = random.nextInt(100);
		animalPos = pos;
		type = random.nextInt(2);
		pig = AnimationLoader.getLoader().getPig();
		goat = AnimationLoader.getLoader().getGoat();
	}

	public boolean isAlive() {
		return protein > 0;
	}

	public int kill() {
		int result = protein;
		protein = 0;
		return result;
	}

	@Override
	public void render(Graphics g) {
		int tileWidth = MapController.getMap().getTileWidth();
		int tileHeight = MapController.getMap().getTileHeight();

		if (type == PIG) {
			pig.draw(animalPos.getX() * tileWidth, animalPos.getY()
					* tileHeight);
		} else {
			goat.draw(animalPos.getX() * tileWidth, animalPos.getY()
					* tileHeight);
		}

	}

	@Override
	public void update(int delta) throws SlickException {
		if (random.nextInt(100) > 95) {
			protein--;
		}

	}
}
