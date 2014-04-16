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

	public void update(int delta) {
		pressure += delta;
		if (pressure > 1000) {
			if (random.nextInt(delta) > 5) {

				xCoord = random.nextInt(MapController.getMap().getMapWidth());
				yCoord = random.nextInt(MapController.getMap().getMapHeight());
				int NTiles = MapController.getMap().getNTiles();

				while (xCoord > NTiles - 5 || yCoord > NTiles - 5) {
					xCoord = random.nextInt(NTiles);
					yCoord = random.nextInt(NTiles);
				}

				int duration = random.nextInt(100000);

				for (int x = 0; x < xCoord + 5; x++) {
					for (int y = 0; y < yCoord + 5; y++) {
						MapController.getMap().getLandscape(x, y)
								.setExtremeWeather(duration);
					}
				}

			}
			pressure = 0;
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
