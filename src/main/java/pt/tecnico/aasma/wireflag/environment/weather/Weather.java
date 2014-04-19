package pt.tecnico.aasma.wireflag.environment.weather;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import pt.tecnico.aasma.wireflag.IGameElement;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public abstract class Weather implements IGameElement {

	protected static final int MAXLIFEDMG = 100;
	protected static final int HIGHLIFEDMG = 40;
	protected static final int MODERATELIFEDMG = 20;
	protected static final int LOWLIFEDMG = 5;
	protected static final int NOLIFEDMG = 0;

	private int duration;
	private MapPosition weatherPos;
	private Animation extremeWeather;

	public Weather(int duration, MapPosition weatherPos,
			Animation extremeWeather) {
		this.duration = duration;
		this.weatherPos = weatherPos;
		this.extremeWeather = extremeWeather;
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

	public void draw(int x, int y) {
		extremeWeather.draw(x, y);
	}

	public abstract int getLifeDamage();
}
