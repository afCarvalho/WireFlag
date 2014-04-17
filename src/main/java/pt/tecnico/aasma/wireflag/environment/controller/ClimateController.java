package pt.tecnico.aasma.wireflag.environment.controller;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.GameElement;

public class ClimateController implements GameElement {

	private Random random;
	private int pressure;
	private int dryness;
	private int xFireCoord;
	private int yFireCoord;
	private boolean activeFire;

	public ClimateController() {
		random = new Random();
	}

	@Override
	public void init() throws SlickException {
	}

	public void update(int delta) throws SlickException {

		if (random.nextInt(10) < 8) {
			pressure += delta;
		} else {
			pressure -= delta;
		}

		if (random.nextInt(10) < 8) {
			dryness += delta;
		} else {
			dryness -= delta;
		}

		if (pressure > 10000) {
			createClimateEvent();
			pressure = 0;
		}

		if (dryness > 10000) {
			createFireEvent();
			dryness = 0;
		}
	}

	public void createClimateEvent() throws SlickException {
		int width = MapController.getMap().getMapWidth()
				/ MapController.getMap().getTileWidth();
		int heigth = MapController.getMap().getMapHeight()
				/ MapController.getMap().getTileHeight();

		int xCoord = random.nextInt(width);
		int yCoord = random.nextInt(heigth);

		while (xCoord > width - 4 || yCoord > heigth - 4) {
			xCoord = random.nextInt(width);
			yCoord = random.nextInt(heigth);
		}

		int duration = random.nextInt(10000);

		for (int x = xCoord; x < xCoord + 4; x++) {
			for (int y = yCoord; y < yCoord + 4; y++) {
				MapController.getMap().getLandscape(x, y, false)
						.setExtremeWeather(duration);
			}
		}
	}

	public void createFireEvent() {

	}

	@Override
	public void render(Graphics g) {
	}

}
