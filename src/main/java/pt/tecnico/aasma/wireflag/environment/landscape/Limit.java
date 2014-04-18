package pt.tecnico.aasma.wireflag.environment.landscape;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.weather.Rainy;
import pt.tecnico.aasma.wireflag.environment.weather.Sunny;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Limit extends Landscape {

	public Limit(MapPosition position) {
		super(0, position, REDUCEDVSB, VHIGHFATIGUE);
	}

	@Override
	public void setExtremeWeather(int duration) throws SlickException {
		weather = new Sunny(0, landscapePos);
	}

	@Override
	public boolean isInflammable() {
		return false;
	}
}
