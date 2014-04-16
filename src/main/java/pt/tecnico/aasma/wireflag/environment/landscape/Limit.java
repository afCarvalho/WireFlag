package pt.tecnico.aasma.wireflag.environment.landscape;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.weather.Rainy;

public class Limit extends Landscape {

	public Limit(int xCoord, int yCoord) {
		super(0, xCoord, yCoord);
	}

	@Override
	public void setExtremeWeather(int duration) throws SlickException {
		weather = new Rainy(duration, xCoord, yCoord);
		weather.init();
	}
}
