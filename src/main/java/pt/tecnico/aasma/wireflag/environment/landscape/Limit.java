package pt.tecnico.aasma.wireflag.environment.landscape;

import pt.tecnico.aasma.wireflag.environment.weather.Rainy;

public class Limit extends Landscape {

	public Limit(int xCoord, int yCoord) {
		super(0, yCoord, yCoord);
	}

	@Override
	public void setExtremeWeather(int duration) {
		weather = new Rainy(duration, xCoord, yCoord);
	}
}
