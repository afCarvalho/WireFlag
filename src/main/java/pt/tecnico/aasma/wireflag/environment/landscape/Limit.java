package pt.tecnico.aasma.wireflag.environment.landscape;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.weather.Rainy;
import pt.tecnico.aasma.wireflag.environment.weather.Sunny;

public class Limit extends Landscape {

	public Limit(int xCoord, int yCoord) {
		super(0, xCoord, yCoord);
	}

	@Override
	public void setExtremeWeather(int duration) throws SlickException {
		weather = new Sunny(0, xCoord, yCoord);
		weather.init();
	}

	@Override
	public boolean isInflammable() {
		return false;
	}
}
