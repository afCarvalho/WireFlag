package pt.tecnico.aasma.wireflag.environment.weather;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.IGameElement;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public abstract class Weather implements IGameElement {

	private int duration;
	private MapPosition weatherPos;

	public Weather(int duration, MapPosition weatherPos) {
		this.duration = duration;
		this.weatherPos = weatherPos;
	}

	public boolean isExtremeWeather() {
		return duration > 0;
	}

	@Override
	public void update(int delta) {
		if (duration > 0)
			duration--;
	}

	@Override
	public void render(Graphics g) {

		int tileWidth = MapController.getMap().getTileWidth();
		int tileHeight = MapController.getMap().getTileHeight();

		int tileXPosition = weatherPos.getX() * tileWidth;
		int tileYPosition = weatherPos.getY() * tileHeight;

		for (int x = tileXPosition; x < tileXPosition + tileWidth; x++) {
			for (int y = tileYPosition; y < tileYPosition + tileHeight; y++) {
				if (x % 25 == 0 && y % 25 == 0)
					draw(x, y);
			}
		}
	}

	public abstract void draw(int x, int y);
}
