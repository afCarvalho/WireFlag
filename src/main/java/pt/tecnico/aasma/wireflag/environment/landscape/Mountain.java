package pt.tecnico.aasma.wireflag.environment.landscape;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.weather.SnowStorm;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Mountain extends Landscape {

	public Mountain(MapPosition position) {
		super(VREDUCEDSPD, position);
	}

	@Override
	public void setExtremeWeather(int duration) throws SlickException {
		weather = new SnowStorm(duration, landscapePos);
	}

	@Override
	public boolean isInflammable() {
		return false;
	}
}
