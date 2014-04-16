package pt.tecnico.aasma.wireflag.environment.landscape;

import pt.tecnico.aasma.wireflag.environment.weather.Rainy;

public class Forest extends Landscape {

	public Forest(int xCoord, int yCoord) {
		super(REDUCEDSPD, yCoord, yCoord);
	}

	@Override
	public void setExtremeWeather(int duration) {
		weather = new Rainy(duration, xCoord, yCoord);
	}

}
