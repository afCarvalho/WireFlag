package pt.tecnico.aasma.wireflag.environment.landscape;

import pt.tecnico.aasma.wireflag.environment.weather.SandStorm;

public class Desert extends Landscape {

	public Desert() {
		super(REDUCEDSPD);
	}

	@Override
	public void setExtremeWeather() {
		weather = new SandStorm();
	}
}
