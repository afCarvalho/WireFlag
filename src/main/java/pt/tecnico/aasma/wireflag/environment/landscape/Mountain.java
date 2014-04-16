package pt.tecnico.aasma.wireflag.environment.landscape;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.weather.SnowStorm;

public class Mountain extends Landscape {

	public Mountain(int xCoord, int yCoord) {
		super(VREDUCEDSPD, xCoord, yCoord);
	}

	@Override
	public void setExtremeWeather(int duration) throws SlickException {
		weather = new SnowStorm(duration, xCoord, yCoord);
		weather.init();
	}
}
