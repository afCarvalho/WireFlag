package pt.tecnico.aasma.wireflag.environment.landscape;

import pt.tecnico.aasma.wireflag.environment.weather.SnowStorm;

public class Mountain extends Landscape {

	public Mountain() {
		super(VREDUCEDSPD);
	}

	@Override
	public void setExtremeWeather() {
		weather = new SnowStorm();
	}
}
