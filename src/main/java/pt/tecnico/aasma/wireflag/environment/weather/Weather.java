package pt.tecnico.aasma.wireflag.environment.weather;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import pt.tecnico.aasma.wireflag.IGameElement;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

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

	/***************
	 *** GETTERS ***
	 ***************/

	public abstract int getLifeDamage();

	/************************
	 *** STATE PREDICATES ***
	 ************************/

	public boolean isExtremeWeather() {
		return duration > 0;
	}

	/********************
	 *** GAME RELATED ***
	 ********************/

	@Override
	public void update(int delta) {
		if (duration > 0)
			duration--;
	}

	@Override
	public void render(Graphics g) {

		int width = MapController.getMap().getTileWidth() * weatherPos.getX();
		int height = MapController.getMap().getTileHeight() * weatherPos.getY();
		draw(width, height);
	}

	public void draw(int x, int y) {
		extremeWeather.draw(x, y);
	}
}
