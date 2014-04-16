package pt.tecnico.aasma.wireflag.environment.landscape;

import pt.tecnico.aasma.wireflag.environment.weather.SnowStorm;

public class Mountain extends Landscape {

	public Mountain(int xCoord, int yCoord) {
		super(VREDUCEDSPD, yCoord, yCoord);
	}

	@Override
	public void setExtremeWeather(int duration) {
		weather = new SnowStorm(duration, xCoord, yCoord);
	}
}
