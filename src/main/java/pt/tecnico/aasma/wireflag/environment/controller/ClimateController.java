package pt.tecnico.aasma.wireflag.environment.controller;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.WireFlagGame;

public class ClimateController implements GameElement {

	private Random random;
	private int pressure;
	private int xCoord;
	private int yCoord;

	public ClimateController() {
		random = new Random();
	}

	@Override
	public void init() throws SlickException {
	}

	public void update(int delta) throws SlickException {
		pressure += delta;
		if (pressure > 10000) {
			if (random.nextInt(delta) > 5) {

				int width = MapController.getMap().getMapWidth()
						/ MapController.getMap().getTileWidth();
				int heigth = MapController.getMap().getMapHeight()
						/ MapController.getMap().getTileHeight();

				xCoord = random.nextInt(width);
				yCoord = random.nextInt(heigth);

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
				pressure = 0;
			}
		}
	}

	@Override
	public void render(Graphics g) {

		/*
		 * if (climate.equals("rain")) {
		 * 
		 * for (int i = xCoord; i < xCoord + 100; i++) for (int j = yCoord; j <
		 * yCoord + 100; j++) if (i % 30 == 0 && j % 30 == 0) rain.draw(i *
		 * 1.0f, j * 1.0f);
		 * 
		 * }
		 */

		/*
		 * if (climate.equals("rain")) { for (int i = 0; i <
		 * Map.getMap().getMapHeight(); i++) for (int j = 0; j <
		 * Map.getMap().getMapWidth(); j++) { if (i % 50 == 0 && j % 50 == 0)
		 * rain.draw(j * 1.0f, i * 1.0f); } }
		 */
	}

}
