package pt.tecnico.aasma.wireflag.environment.landscape;

import pt.tecnico.aasma.wireflag.environment.weather.Rainy;

public class Forest extends Landscape {

	public Forest() {
		super(REDUCEDSPD);
	}

	@Override
	public void setExtremeWeather() {
		weather = new Rainy();
	}

}
