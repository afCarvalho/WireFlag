package pt.tecnico.aasma.wireflag.environment.controller;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.GameElement;

public class ClimateController implements GameElement {

	private String climate = "";
	private Animation rain;
	private Random random;
	private int pressure;
	private int xCoord;
	private int yCoord;

	public ClimateController() {
		random = new Random();
	}

	@Override
	public void init() throws SlickException {

		rain = new Animation(new Image[] { new Image(System.getProperty("data") + "rain.png") },
				new int[] { 300 }, false);

	}

	public void update(int delta) {
		pressure += delta;
		if (pressure > 1000) {
			if (random.nextInt(delta) > 5) {
				if (climate.equals("rain"))
					climate = "sun";
				else {
					climate = "rain";

					xCoord = random.nextInt(MapController.getMap()
							.getMapWidth());
					yCoord = random.nextInt(MapController.getMap()
							.getMapHeight());

					while (xCoord > MapController.getMap().getMapWidth() - 100
							|| yCoord > MapController.getMap().getMapHeight() - 100) {
						xCoord = random.nextInt(MapController.getMap()
								.getMapWidth());
						yCoord = random.nextInt(MapController.getMap()
								.getMapHeight());
					}

				}

			}
			pressure = 0;
		}
	}

	@Override
	public void render(Graphics g) {

		if (climate.equals("rain")) {

			for (int i = xCoord; i < xCoord + 100; i++)
				for (int j = yCoord; j < yCoord + 100; j++)
					if (i % 30 == 0 && j % 30 == 0)
						rain.draw(i * 1.0f, j * 1.0f);

		}

		/*
		 * if (climate.equals("rain")) { for (int i = 0; i <
		 * Map.getMap().getMapHeight(); i++) for (int j = 0; j <
		 * Map.getMap().getMapWidth(); j++) { if (i % 50 == 0 && j % 50 == 0)
		 * rain.draw(j * 1.0f, i * 1.0f); } }
		 */
	}

}
