package pt.tecnico.aasma.wireflag.environment.landscape;

import pt.tecnico.aasma.wireflag.environment.weather.Rainy;

public class Limit extends Landscape {

	public Limit() {
		super(0);
	}

	@Override
	public void setExtremeWeather() {
		weather = new Rainy();
	}
}
