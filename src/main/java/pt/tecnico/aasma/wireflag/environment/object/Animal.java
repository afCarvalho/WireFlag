package pt.tecnico.aasma.wireflag.environment.object;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Animal implements GameElement {

	private int protein;
	private Random random;
	private Animation goat;
	private MapPosition animalPos;

	public Animal(MapPosition pos) {
		random = new Random();
		protein = random.nextInt(100);
		animalPos = pos;
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
	public void init() throws SlickException {
		goat = new Animation(new Image[] { new Image(System.getProperty("data")
				+ "goat.png") }, new int[] { 300 }, false);
	}

	@Override
	public void render(Graphics g) {
		int tileWidth = MapController.getMap().getTileWidth();
		int tileHeight = MapController.getMap().getTileHeight();

		goat.draw(animalPos.getX() * tileWidth, animalPos.getY() * tileHeight);
	}

	@Override
	public void update(int delta) throws SlickException {
		if (random.nextInt(100) > 95) {
			protein--;
		}

	}
}
