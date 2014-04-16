package pt.tecnico.aasma.wireflag.environment.landscape;

import pt.tecnico.aasma.wireflag.environment.weather.ThunderStorm;

public class Water extends Landscape {

	public Water(int xCoord, int yCoord) {
		super(VREDUCEDSPD, yCoord, yCoord);
	}

	@Override
	public void setExtremeWeather(int duration) {
		weather = new ThunderStorm(duration, xCoord, yCoord);
	}
}
