package pt.tecnico.aasma.wireflag.environment.weather;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;

public abstract class Weather implements GameElement {

	private int duration;
	private int xCoord;
	private int yCoord;

	public Weather(int duration, int xCoord, int yCoord) {
		this.duration = duration;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}

	public boolean isExtremeWeather() {
		return duration > 0;
	}

	@Override
	public abstract void init() throws SlickException;

	@Override
	public void update(int delta) {
		duration--;
	}

	@Override
	public void render(Graphics g) {

		for (int x = xCoord * MapController.getMap().getTileWidth(); x < xCoord
				* MapController.getMap().getTileWidth()
				+ MapController.getMap().getTileWidth(); x++) {
			for (int y = yCoord * MapController.getMap().getTileHeight(); y < yCoord
					* MapController.getMap().getTileHeight()
					+ MapController.getMap().getTileHeight(); y++) {
				if (x % 25 == 0 && y % 25 == 0)
					draw(x, y);
			}
		}
	}

	public abstract void draw(int x, int y);
}
