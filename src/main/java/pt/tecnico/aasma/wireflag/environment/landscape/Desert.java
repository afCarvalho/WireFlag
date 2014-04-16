package pt.tecnico.aasma.wireflag.environment.landscape;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.weather.SandStorm;

public class Desert extends Landscape {

	public Desert(int xCoord, int yCoord) {
		super(REDUCEDSPD, xCoord, yCoord);
	}

	@Override
	public void setExtremeWeather(int duration) throws SlickException {
		weather = new SandStorm(duration, xCoord, yCoord);
		weather.init();
	}
}
