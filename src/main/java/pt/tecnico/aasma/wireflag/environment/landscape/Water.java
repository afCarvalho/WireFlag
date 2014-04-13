package pt.tecnico.aasma.wireflag.environment.landscape;

import pt.tecnico.aasma.wireflag.environment.weather.ThunderStorm;

public class Water extends Landscape {

	public Water() {
		super(VREDUCEDSPD);
	}

	@Override
	public void setExtremeWeather() {
		weather = new ThunderStorm();
	}

}
