package pt.tecnico.aasma.wireflag.environment.landscape;

import pt.tecnico.aasma.wireflag.environment.weather.Rainy;

public class Plain extends Landscape {

	public Plain(int xCoord, int yCoord) {
		super(NORMALSPD, yCoord, yCoord);
	}

	@Override
	public void setExtremeWeather(int duration) {
		weather = new Rainy(duration, xCoord, yCoord);
	}

}
