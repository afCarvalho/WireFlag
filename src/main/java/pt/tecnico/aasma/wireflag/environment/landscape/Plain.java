package pt.tecnico.aasma.wireflag.environment.landscape;

import pt.tecnico.aasma.wireflag.environment.weather.Rainy;

public class Plain extends Landscape {

	public Plain() {
		super(NORMALSPD);
	}

	@Override
	public void setExtremeWeather() {
		weather = new Rainy();
	}

}
